package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import service.SendBotService;

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
        media.setCaption("""
                Ваш ID профиля: `%d`
                Количество пополнений: 0
                Количество заказов: 0
                                
                Баланс: 0₽
                """.formatted(callbackQuery.getFrom().getId()));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                createButtonData("Пополнить баланс \uD83D\uDCB0", "12")
                        ),
                        List.of(
                                createButtonData("Использовать промокод \uD83D\uDECD", "12")
                        ),
                        List.of(
                                createButtonData("История покупок \uD83D\uDD0E", "12")
                        ),
                        List.of(
                                createButtonData("Назад", BackToMainCallback.NAME)
                        )
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
