package callback;

import com.pengrad.telegrambot.model.CallbackQuery;
import service.SendBotService;

public class GuaranteeCallback implements Callback {
    public final static String NAME = "guarantee";
    private final SendBotService service;

    public GuaranteeCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {

    }
}
