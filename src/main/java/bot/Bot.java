package bot;

import callback.CallbackContainer;
import command.CommandContainer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotServiceImp;

public class Bot extends TelegramLongPollingBot {
    private static final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;

    public Bot(String botToken) {
        super(botToken);
        this.commandContainer = new CommandContainer(new SendBotServiceImp(this));
        this.callbackContainer = new CallbackContainer(new SendBotServiceImp(this));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().getText() != null) {
            String text = update.getMessage().getText();
            if (text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = text.split(" ")[0];
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }
        }
        if (update.getCallbackQuery() != null) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackIdentifier = callbackQuery.getData();
            callbackContainer.retrieveCallback(callbackIdentifier).execute(callbackQuery);
        }
    }

    @Override
    public String getBotUsername() {
        return "@igrovnybot";
    }
}
