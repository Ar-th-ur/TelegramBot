package callback.store.fortnite;

import callback.Callback;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import service.SendBotService;

public class StoreSetsCallback implements Callback {
    public final static String NAME = "storeSets";
    private final SendBotService service;

    public StoreSetsCallback(SendBotService service) {
        this.service = service;
    }


    @Override
    public void execute(CallbackQuery callbackQuery) {

    }
}
