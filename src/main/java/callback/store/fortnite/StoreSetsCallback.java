package callback.store.fortnite;

import callback.Callback;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import service.SendBotService;

public class StoreSetsCallback implements Callback {
    public final static String NAME = "storeSets";
    private final SendBotService service;

    public StoreSetsCallback(SendBotService service) {
        this.service = service;
    }


    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long id = callbackQuery.message().chat().id();
        Integer messageId = callbackQuery.message().messageId();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                new InlineKeyboardButton("Набор 1").callbackData("set 1")
        );
        service.sendEditMessage(id, messageId, "Выбери набор", markup);
    }
}
