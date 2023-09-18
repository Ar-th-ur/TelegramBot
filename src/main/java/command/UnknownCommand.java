package command;

import org.telegram.telegrambots.meta.api.objects.Update;
import service.SendBotService;

public class UnknownCommand implements Command {
    private final static String UNKNOWN_MESSAGE = "Я вас не понимаю, напишите /help, чтобы узнать, что я понимаю";
    private final SendBotService service;

    public UnknownCommand(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(Update update) {
        service.sendMessage(update.getMessage().getChatId(), UNKNOWN_MESSAGE);
    }
}
