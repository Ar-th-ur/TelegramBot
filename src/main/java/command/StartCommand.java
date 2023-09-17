package command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import service.SendBotService;

import java.io.File;

public class StartCommand implements Command {
    public final static String NAME = "/start";
    private final static String START_MESSAGE = "Главное меню";
    private final SendBotService service;

    public StartCommand(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(Update update) {
        File file = new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\menu.png");
        long id = update.message().chat().id();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
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
        service.sendPhoto(id, file, markup, START_MESSAGE);
    }
}
