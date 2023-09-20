package command;

import callback.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;

import java.io.File;
import java.util.List;

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
        InputFile inputFile = new InputFile(file);
        Long chatId = update.getMessage().getChatId();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                InlineKeyboardButton.builder().text("Магазин \uD83C\uDFAE").callbackData(FortnitStoreCallBack.NAME).build(),
                                InlineKeyboardButton.builder().text("Кабинет \uD83E\uDEAA").callbackData(AccountCallback.NAME).build()
                        ),
                        List.of(
                                InlineKeyboardButton.builder().text("FAQ ⁉\uFE0F").callbackData(FAQCallback.NAME).build(),
                                InlineKeyboardButton.builder().text("Гарантии ☑\uFE0F").callbackData(GuaranteeCallback.NAME).build()
                        ),
                        List.of(
                                InlineKeyboardButton.builder().text("Отзывы \uD83D\uDDE3").callbackData(ReviewCallback.NAME).build(),
                                InlineKeyboardButton.builder().text("Поддержка \uD83D\uDC68\u200D\uD83D\uDCBB").callbackData(SupportCallback.NAME).build()
                        )
                )
        );
        service.sendPhoto(chatId, inputFile, markup, START_MESSAGE);
    }
}
