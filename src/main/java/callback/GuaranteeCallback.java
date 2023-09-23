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

public class GuaranteeCallback implements Callback {
    public final static String NAME = "guarantee";
    private final SendBotService service;

    public GuaranteeCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        media.setMedia(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\faq.png"), "faq.png");
        media.setParseMode(ParseMode.MARKDOWN);
        media.setCaption("Максимально надёжно, без банов и скама\n[Ознакомиться с гарантиями](https://telegra.ph/Garantii-10-21");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("Назад", BackToMainCallback.NAME)
                        )
                )
        );
        service.sendEditPhoto(chatId, messageId, media, markup);
    }
}
