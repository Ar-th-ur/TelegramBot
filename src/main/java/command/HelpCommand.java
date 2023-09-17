package command;

import com.pengrad.telegrambot.model.Update;
import service.SendBotService;

public class HelpCommand implements Command {
    public final static String NAME = "/help";
    private final static String HELP_MESSAGE = """
            Доступных команд пока нет
            """;
    private final SendBotService service;

    public HelpCommand(SendBotService service) {
        this.service = service;
    }

    @Override
    public void execute(Update update) {
        service.sendMessage(update.message().chat().id(), HELP_MESSAGE);
    }
}
