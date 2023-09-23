package bot;

import callback.CallbackContainer;
import callback.StartDialogCallback;
import command.CommandContainer;
import dialog.Dialog;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import review_sender.ReviewSender;
import service.SendBotServiceImp;
import sets.Product;
import sets.ProductsContainer;
import users.Purchases;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static utils.ButtonUtils.createButtonData;

public class Bot extends TelegramLongPollingBot {
    private static final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;
    private final Dialog dialog;
    private final ReviewSender reviewSender;

    public static final Queue<SendMessage> paidOrders = new ArrayDeque<>();
    public static List<Long> questions = new ArrayList<>();
    public static List<Long> reviews = new ArrayList<>();
    public static boolean isDialog = false;
    public static boolean isAllowedToSend = true;

    public Bot(String botToken) {
        super(botToken);
        this.commandContainer = new CommandContainer(new SendBotServiceImp(this));
        this.callbackContainer = new CallbackContainer(new SendBotServiceImp(this));
        this.dialog = new Dialog(new SendBotServiceImp(this));
        this.reviewSender = new ReviewSender(new SendBotServiceImp(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = text.split(" ")[0];
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }
            if (isDialog) {
                dialog.sendMessage(update);
            }
            Long chatId = update.getMessage().getChatId();
            if (questions.contains(chatId)) {
                parseQuestion(update, text);
            }
            if (reviews.contains(chatId)) {
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(chatId);
                deleteMessage.setMessageId(update.getMessage().getMessageId() - 1);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Благодарим за отзыв");
                reviews.remove(chatId);
                reviewSender.sendReview(text, update.getMessage().getFrom());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackIdentifier = callbackQuery.getData();
            callbackContainer.retrieveCallback(callbackIdentifier).execute(callbackQuery);
        }
        if (update.hasPreCheckoutQuery()) {
            PreCheckoutQuery preCheckoutQuery = update.getPreCheckoutQuery();
            AnswerPreCheckoutQuery answerPreCheckoutQuery = new AnswerPreCheckoutQuery();
            answerPreCheckoutQuery.setPreCheckoutQueryId(preCheckoutQuery.getId());
            answerPreCheckoutQuery.setOk(true);
            try {
                if (execute(answerPreCheckoutQuery)) {
                    sendRemindUser(preCheckoutQuery);
                    sendCompletedPayment(preCheckoutQuery);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseQuestion(Update update, String text) {
        SendMessage question = new SendMessage();
        question.setChatId(Dialog.mainId);
        question.setText("""
                💬 Вопрос от @%s:
                
                %s
                """.formatted(update.getMessage().getFrom().getUserName(), text));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("Начать диалог", StartDialogCallback.NAME + " " + update.getMessage().getChatId())
                        )
                )
        );
        question.setReplyMarkup(markup);
        sendOperator(question);
        SendMessage sendMessage = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setText("\uD83D\uDCAC Ваш вопрос был отправлен в поддержку, оператор свяжется с вами, как только освободится");
        questions.remove(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendRemindUser(PreCheckoutQuery preCheckoutQuery) {
        Long userId = preCheckoutQuery.getFrom().getId();
        String productName = ProductsContainer.getCommonMap().get(preCheckoutQuery.getInvoicePayload()).getName();
        Purchases.addPurchase(userId, productName);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText("""
                ✅ Заказ успешно оплачен
                                            
                Для получения товара с вами свяжется оператор прямо в этом чате. Вам придет уведомление, как оператор освободится.
                """);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendCompletedPayment(PreCheckoutQuery preCheckoutQuery) {
        Product product = ProductsContainer.getCommonMap().get(preCheckoutQuery.getInvoicePayload());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Dialog.mainId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setText("""
                ✅ *Заказ оплачен*
                ID профиля: `%s`
                Никнейм: @%s
                Товар: %s
                Цена: %d
                """.formatted(
                preCheckoutQuery.getFrom().getId(),
                preCheckoutQuery.getFrom().getUserName(),
                product.getName(),
                product.getPrice() / 100)
        );
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("Начать диалог", StartDialogCallback.NAME + " " + preCheckoutQuery.getFrom().getId())
                        )
                )
        );
        sendMessage.setReplyMarkup(markup);
        sendOperator(sendMessage);
    }

    private void sendOperator(SendMessage sendMessage) {
        try {
            if (isAllowedToSend) {
                execute(sendMessage);
                isAllowedToSend = false;
            } else {
                paidOrders.add(sendMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "@igrovnybot";
    }
}
