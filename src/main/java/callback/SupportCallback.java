package callback;

import com.pengrad.telegrambot.model.CallbackQuery;
import service.SendBotService;

public class SupportCallback implements Callback {
    public final static String NAME = "support";
    private final SendBotService service;

    public SupportCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {

    }
}
