package callback.store.fortnite;

import callback.BackToMainCallback;
import callback.Callback;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import service.SendBotService;

public class VbacksCallback implements Callback {
    public final static String NAME = "vbacks";
    private final SendBotService service;

    public VbacksCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.message().chat().id();
        Integer messageId = callbackQuery.message().messageId();
        InputMediaPhoto media = new InputMediaPhoto("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\vbacks.png");
        media.caption("Выбери товар");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("1000 В-баксов").callbackData("1"),
                        new InlineKeyboardButton("2800 В-баксов").callbackData("2")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("5000 В-баксов").callbackData("3"),
                        new InlineKeyboardButton("13500 В-баксов").callbackData("4")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Главное меню").callbackData(BackToMainCallback.NAME),
                        new InlineKeyboardButton("Назад").callbackData(FortnitStoreCallBack.NAME)
                }
        );
        service.sendEditMessage(id, messageId, media, markup);
    }
}
