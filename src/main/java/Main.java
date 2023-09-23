import bot.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        //6685448383:AAHd9LBx55XoegVsirLY62_roOScs44lCsQ
        Bot bot = new Bot("6607346311:AAE_X_v9vZRe9tNKRtEdlAoDNemwLLltfZ8");
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
