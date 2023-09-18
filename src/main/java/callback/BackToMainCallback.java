package callback;

import callback.store.StoreCallBack;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class BackToMainCallback implements Callback {
    public final static String NAME = "backToMain";
    private final SendBotService service;

    public BackToMainCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        InputMediaPhoto media = new InputMediaPhoto();
        try {
            media.setMedia(new FileInputStream("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\menu.png"), "menu.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }media.setCaption("Главное меню");
        Integer messageId = callbackQuery.getMessage().getMessageId();
        Long chatId = callbackQuery.getMessage().getChatId();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(
                                InlineKeyboardButton.builder().text("Магазин \uD83C\uDFAE").callbackData(StoreCallBack.NAME).build(),
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
        service.sendEditPhoto(chatId, messageId, media, markup);
    }
}
