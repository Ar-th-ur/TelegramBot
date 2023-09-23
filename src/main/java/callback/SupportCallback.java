package callback;

import command.Command;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import service.SendBotService;

import java.io.File;
import java.util.List;

import static utils.ButtonUtils.createButtonData;

public class SupportCallback implements Callback {
    public final static String NAME = "support";
    private final SendBotService service;

    public SupportCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        media.setMedia(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\support.png"), "support.png");
        media.setParseMode(ParseMode.MARKDOWN);
        media.setCaption("Тут ты можешь задать свой вопрос в поддержку, но перед этим ознакомься с нашим [FAQ](https://telegra.ph/FAQ-CHasto-zadavaemye-voprosy-09-21-2)\n\nОтвечаем в порядке очереди");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("Задать вопрос", AskQuestionCallback.NAME)
                        ),
                        List.of(
                                createButtonData("В главное меню", BackToMainCallback.NAME)
                        )
                )
        );
        service.sendEditPhoto(chatId, messageId, media, markup);
    }
}
