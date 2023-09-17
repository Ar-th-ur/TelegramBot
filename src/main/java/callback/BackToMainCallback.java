package callback;

import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import service.SendBotService;

import java.io.File;

public class BackToMainCallback implements Callback {
    public final static String NAME = "backToMain";
    public static InlineKeyboardMarkup markup;
    private final SendBotService service;

    static {
        markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Магазин \uD83C\uDFAE").callbackData("store"),
                        new InlineKeyboardButton("Кабинет \uD83E\uDEAA").callbackData("account")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("FAQ ⁉\uFE0F").callbackData("faq"),
                        new InlineKeyboardButton("Гарантии ☑\uFE0F").callbackData("guarantee")
                },
                new InlineKeyboardButton[]{
                        new InlineKeyboardButton("Отзывы \uD83D\uDDE3").callbackData("reviews"),
                        new InlineKeyboardButton("Поддержка \uD83D\uDC68\u200D\uD83D\uDCBB").callbackData("support")
                }
        );
    }

    public BackToMainCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.message().chat().id();
        Integer messageId = callbackQuery.message().messageId();
        InputMediaPhoto media = new InputMediaPhoto(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\menu.png"));
        media.caption("Главное меню");
        service.sendEditMessage(id, messageId, media, markup);
    }
}
