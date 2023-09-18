package callback.store.fortnite;

import callback.BackToMainCallback;
import callback.Callback;
import callback.VbacksCallback;
import callback.store.StoreCallBack;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class FortnitStoreCallBack implements Callback {
    public static final String NAME = "fortnite";
    private final SendBotService service;

    public FortnitStoreCallBack(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        try {
            media.setMedia(new FileInputStream("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\fortnite.png"), "fortnite.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }media.setCaption("Выбери категорию");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                InlineKeyboardButton.builder().text("Вбаксы").callbackData(VbacksCallback.NAME).build(),
                                InlineKeyboardButton.builder().text("Наборы").callbackData("1").build()
                        ),
                        List.of(
                                InlineKeyboardButton.builder().text("Подписка").callbackData("12").build()
                        ),
                        List.of(
                                InlineKeyboardButton.builder().text("Назад").callbackData(StoreCallBack.NAME).build(),
                                InlineKeyboardButton.builder().text("Гглавное меню").callbackData(BackToMainCallback.NAME).build()
                        )
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
