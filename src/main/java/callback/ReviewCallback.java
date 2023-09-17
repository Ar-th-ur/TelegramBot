package callback;

import com.pengrad.telegrambot.model.CallbackQuery;
import service.SendBotService;

public class ReviewCallback implements Callback {
    public final static String NAME = "review";
    private final SendBotService service;

    public ReviewCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {

    }
}
