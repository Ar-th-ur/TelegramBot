package callback;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import service.SendBotService;

import java.io.File;

public class AccountCallback implements Callback {
    public final static String NAME = "account";
    private final static String ACCOUNT_MESSAGE = "Ваш кабинет";
    private final SendBotService service;

    public AccountCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.message().chat().id();
        Integer messageId = callbackQuery.message().messageId();
        InputMediaPhoto media = new InputMediaPhoto(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\account.png"));
        media.caption(ACCOUNT_MESSAGE);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Назад").callbackData(BackToMainCallback.NAME)
                }
        );

        service.sendEditMessage(id, messageId, media, markup);
    }
}
