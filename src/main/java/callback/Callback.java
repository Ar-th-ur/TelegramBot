package callback;

import com.pengrad.telegrambot.model.CallbackQuery;

public interface Callback {
    void execute(CallbackQuery callbackQuery);
}
