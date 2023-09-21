package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;
import sets.Product;
import sets.ProductsContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.ButtonUtils.createButtonData;
import static utils.ButtonUtils.createButtonUrl;

public class StoreSetsCallback implements Callback {
    public final static String NAME = "storeSets";
    private final SendBotService service;
    private final static InputMediaPhoto media;

    static {
        media = new InputMediaPhoto();
        media.setMedia(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\sets.png"), "sets.png");
        media.setCaption("Выбери набор");
    }

    public StoreSetsCallback(SendBotService service) {
        this.service = service;
    }


    @Override
    public void execute(CallbackQuery callbackQuery) {
        if (callbackQuery.getData().equals(NAME)) {
            sendTitle(callbackQuery);
        } else {
            sendProduct(callbackQuery);
        }
    }

    private void sendProduct(CallbackQuery callbackQuery) {
        String name = callbackQuery.getData().split(" ", 2)[1];
        Product set = ProductsContainer.getSet(name);
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        String paymentLink = service.getInvoiceLink(chatId, set.getName(), set.getName(), set.getPrice());
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonUrl("Купить", paymentLink)
                        ),
                        List.of(
                                createButtonData("Назад", NAME)
                        )
                )
        );
        service.sendEditPhoto(chatId, messageId, set.getPhoto(), markup);
    }

    private void sendTitle(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        int counter = 0;
        for (Product product : ProductsContainer.getSets().values()) {
            row.add(createButtonData(product.getName(), NAME + " " + product.getName()));
            counter++;
            if (counter == 2) {
                keyboard.add(List.copyOf(row));
                row.clear();
                counter = 0;
            }
        }
        keyboard.add(List.of(createButtonData("Назад", FortnitStoreCallBack.NAME)));
        markup.setKeyboard(keyboard);
        service.sendEditPhoto(chatId, messageId, media, markup);
    }
}
