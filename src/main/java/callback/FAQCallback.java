package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import service.SendBotService;

public class FAQCallback implements Callback {
    public final static String NAME = "faq";
    private final SendBotService service;

    public FAQCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {

    }
}
