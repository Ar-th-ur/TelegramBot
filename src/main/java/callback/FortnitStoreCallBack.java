package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;
import utils.ButtonUtils;

import java.io.File;
import java.util.List;

import static utils.ButtonUtils.createButtonData;

public class FortnitStoreCallBack implements Callback {
    public static final String NAME = "fortnite";
    public static final InputMediaPhoto media;
    private final SendBotService service;

    static {
        media = new InputMediaPhoto();
        media.setMedia(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\fortnite.png"), "fortnite.png");
        media.setCaption("Выбери категорию");
    }

    public FortnitStoreCallBack(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("Наборы", StoreSetsCallback.NAME),
                                createButtonData("Вбаксы", VbacksCallback.NAME)
                        ),
                        List.of(
                                createButtonData("Подписки", "12")
                        ),
                        List.of(
                                createButtonData("Назад", BackToMainCallback.NAME)
                        )
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
