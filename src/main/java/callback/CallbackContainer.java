package callback;

import callback.store.StoreCallBack;
import callback.store.fortnite.FortnitStoreCallBack;
import callback.store.fortnite.StoreSetsCallback;
import callback.store.fortnite.VbacksCallback;
import service.SendBotServiceImp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CallbackContainer {
    private final Map<String, Callback> callbackMap;

    public CallbackContainer(SendBotServiceImp service) {
        callbackMap = Collections.unmodifiableMap(new HashMap<>(){{
            put(StoreCallBack.NAME, new StoreCallBack(service));
            put(AccountCallback.NAME, new AccountCallback(service));
            put(FAQCallback.NAME, new FAQCallback(service));
            put(VbacksCallback.NAME, new VbacksCallback(service));
            put(GuaranteeCallback.NAME, new GuaranteeCallback(service));
            put(ReviewCallback.NAME, new ReviewCallback(service));
            put(SupportCallback.NAME, new SupportCallback(service));
            put(BackToMainCallback.NAME, new BackToMainCallback(service));
            put(FortnitStoreCallBack.NAME, new FortnitStoreCallBack(service));
            put(StoreSetsCallback.NAME, new StoreSetsCallback(service));
        }});
    }

    public Callback retrieveCallback(String commandIdentifier) {
        return callbackMap.getOrDefault(commandIdentifier, callbackQuery -> {
            System.out.println("error");
        });
    }
}
