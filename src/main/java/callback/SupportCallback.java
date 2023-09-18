package callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import service.SendBotService;

public class SupportCallback implements Callback {
    public final static String NAME = "support";
    private final SendBotService service;

    public SupportCallback(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

    }
}
