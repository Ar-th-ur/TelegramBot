package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import service.SendBotService;
import users.Purchases;

import java.io.File;
import java.util.List;

import static utils.ButtonUtils.createButtonData;

public class AccountCallback implements Callback {
    public final static String NAME = "account";
    private final SendBotService service;

    public AccountCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        media.setMedia(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\account.png"), "account.png");
        Long userId = callbackQuery.getFrom().getId();
        media.setCaption("""
                Ваш ID профиля: `%d`
                Количество заказов: %d
                """.formatted(userId, Purchases.getCount(id)));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("История покупок \uD83D\uDD0E", PurchasesHistoryCallback.NAME)
                        ),
                        List.of(
                                createButtonData("Назад", BackToMainCallback.NAME)
                        )
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
