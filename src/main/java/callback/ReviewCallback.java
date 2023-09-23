package callback;

import command.Command;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.SendBotService;

import java.io.File;
import java.util.List;

public class ReviewCallback implements Callback {
    public final static String NAME = "review";
    private final SendBotService service;

    public ReviewCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        InputMediaPhoto media = new InputMediaPhoto();
        media.setMedia(new File("C:\\Users\\user\\IdeaProjects\\comunity_edition\\RobertTelegramBot\\src\\main\\resources\\review.png"), "review.png");
        media.setParseMode(ParseMode.MARKDOWN);
        media.setCaption("Отдельный чат с отзывами, писать в чат могут те, кто оформил заказ.\n[Чатик с отзывами](https://t.me/FortPointReviews)");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(
                        List.of(InlineKeyboardButton.builder().text("Назад").callbackData(BackToMainCallback.NAME).build())
                )
        );
        service.sendEditPhoto(id, messageId, media, markup);
    }
}
