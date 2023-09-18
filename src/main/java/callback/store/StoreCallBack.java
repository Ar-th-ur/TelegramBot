package callback.store;

import callback.BackToMainCallback;
import callback.Callback;
import callback.store.fortnite.FortnitStoreCallBack;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class StoreCallBack implements Callback {
    public final static String NAME = "store";
    private final static String STORE_MESSAGE = "Здесь ваш магазин";
    private final SendBotService service;

    public StoreCallBack(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        try {
            media.setMedia(new FileInputStream("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\store.png"), "store.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        media.setCaption(STORE_MESSAGE);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                InlineKeyboardButton.builder().text("Fortnite").callbackData(FortnitStoreCallBack.NAME).build()
                        ),
                        List.of(
                                InlineKeyboardButton.builder().text("Назад").callbackData(BackToMainCallback.NAME).build()
                        )
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
