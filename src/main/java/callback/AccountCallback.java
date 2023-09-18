package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class AccountCallback implements Callback {
    public final static String NAME = "account";
    private final static String ACCOUNT_MESSAGE = "Ваш кабинет";
    private final SendBotService service;

    public AccountCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        try {
            media.setMedia(new FileInputStream("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\account.png"), "account.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }media.setCaption(ACCOUNT_MESSAGE);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                InlineKeyboardButton.builder().text("Назад").callbackData(BackToMainCallback.NAME).build()
                        )
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
