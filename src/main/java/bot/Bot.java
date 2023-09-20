package bot;

import callback.CallbackContainer;
import command.CommandContainer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.SendBotServiceImp;

public class Bot extends TelegramLongPollingBot {
    private static final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;

    public Bot(String botToken) {
        super(botToken);
        this.commandContainer = new CommandContainer(new SendBotServiceImp(this));
        this.callbackContainer = new CallbackContainer(new SendBotServiceImp(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = text.split(" ")[0];
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
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
                execute(answerPreCheckoutQuery);
            } catch (TelegramApiException e) {

            }
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId("1066061901");
//            sendMessage.setText("""
//                    Заказ оплачен: @%s
//                    Цена:
//                    Товар:
//                    %s
//                    """.formatted(
//                    preCheckoutQuery.getFrom().getUserName(),
//
//                    ));
        }
    }

    @Override
    public String getBotUsername() {
        return "@igrovnybot";
    }
}
