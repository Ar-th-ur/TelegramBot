package callback;

import service.SendBotServiceImp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CallbackContainer {
    private final Map<String, Callback> callbackMap;

    public CallbackContainer(SendBotServiceImp service) {
        callbackMap = Collections.unmodifiableMap(new HashMap<>() {{
            put(AccountCallback.NAME, new AccountCallback(service));
            put(GuaranteeCallback.NAME, new GuaranteeCallback(service));
            put(ReviewCallback.NAME, new ReviewCallback(service));
            put(PurchasesHistoryCallback.NAME, new PurchasesHistoryCallback(service));
            put(SubscribesCallback.NAME, new SubscribesCallback(service));
            put(SupportCallback.NAME, new SupportCallback(service));
            put(BackToMainCallback.NAME, new BackToMainCallback(service));
            put(FortnitStoreCallBack.NAME, new FortnitStoreCallBack(service));
            put(FAQCallback.NAME, new FAQCallback(service));
            put(StoreSetsCallback.NAME, new StoreSetsCallback(service));
            put(VbacksCallback.NAME, new VbacksCallback(service));
            put(StartDialogCallback.NAME, new StartDialogCallback(service));
        }});
    }

    public Callback retrieveCallback(String commandIdentifier) {
        for (Map.Entry<String, Callback> entry : callbackMap.entrySet()) {
            if (commandIdentifier.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return callbackQuery -> System.out.println("error");
    }
}
