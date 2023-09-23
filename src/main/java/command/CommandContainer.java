package command;

import callback.AccountCallback;
import callback.FAQCallback;
import callback.FortnitStoreCallBack;
import callback.GuaranteeCallback;
import service.SendBotServiceImp;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final UnknownCommand unknownCommand;

    public CommandContainer(SendBotServiceImp service) {
        commandMap = Collections.unmodifiableMap(new ConcurrentHashMap<>() {{
            put(StartCommand.NAME, new StartCommand(service));
            put("/menu", new StartCommand(service));
            put(StopDialogCommand.NAME, new StopDialogCommand(service));
        }});
        unknownCommand = new UnknownCommand(service);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
