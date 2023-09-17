package callback.store.fortnite;

import callback.BackToMainCallback;
import callback.Callback;
import callback.store.StoreCallBack;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import service.SendBotService;

import java.io.File;

public class FortnitStoreCallBack implements Callback {
    public static final String NAME = "fortnite";
    private final SendBotService service;

    public FortnitStoreCallBack(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.message().chat().id();
        Integer messageId = callbackQuery.message().messageId();
        InputMediaPhoto media = new InputMediaPhoto(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\fortnite.png"));
        media.caption("Выбери категорию");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Вбаксы").callbackData(VbacksCallback.NAME),
                        new InlineKeyboardButton("Наборы").callbackData(StoreSetsCallback.NAME)
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Подписка").callbackData("3")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Назад").callbackData(StoreCallBack.NAME),
                        new InlineKeyboardButton("Главное меню").callbackData(BackToMainCallback.NAME)
                }
        );
        service.sendEditMessage(id, messageId, media, markup);
    }
}
