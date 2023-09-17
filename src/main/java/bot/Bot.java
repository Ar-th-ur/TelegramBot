package bot;

import callback.CallbackContainer;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import command.CommandContainer;
import service.SendBotServiceImp;

public class Bot extends TelegramBot {
    private static final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    private final CallbackContainer callbackContainer;

    public Bot(String botToken) {
        super(botToken);
        this.commandContainer = new CommandContainer(new SendBotServiceImp(this));
        this.callbackContainer = new CallbackContainer(new SendBotServiceImp(this));
    }

    public void init() {
        this.setUpdatesListener(list -> {
            list.forEach(Bot.this::processUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


    public void processUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            String text = update.message().text();
            if (text.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = text.split(" ")[0];
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }
        }
        if (update.callbackQuery() != null) {
            CallbackQuery callbackQuery = update.callbackQuery();
            String callbackIdentifier = callbackQuery.data();
            callbackContainer.retrieveCallback(callbackIdentifier).execute(callbackQuery);
        }
    }
}
