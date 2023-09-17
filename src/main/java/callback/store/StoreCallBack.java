package callback.store;

import callback.BackToMainCallback;
import callback.Callback;
import callback.store.fortnite.FortnitStoreCallBack;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import service.SendBotService;

import java.io.File;

public class StoreCallBack implements Callback {
    public final static String NAME = "store";
    private final static String STORE_MESSAGE = "Здесь ваш магазин";
    private final SendBotService service;

    public StoreCallBack(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.message().chat().id();
        Integer messageId = callbackQuery.message().messageId();
        InputMediaPhoto media = new InputMediaPhoto(new File("C:\\Users\\user\\IdeaProjects\\Solution\\src\\main\\resources\\store.png"));
        media.caption(STORE_MESSAGE);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Fortnite").callbackData(FortnitStoreCallBack.NAME),
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Назад").callbackData(BackToMainCallback.NAME)
                }
        );
        service.sendEditMessage(id, messageId, media, markup);
    }
}
