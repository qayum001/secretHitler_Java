package pujak.boardgames.secretHitler.telegramBot.callbackManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.*;

@Component
public class CallbackWaiter<T, C extends Callback<T>> { // T is callback returning type, C is Callback type
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public CompletableFuture<ArrayList<T>> getCallbacks(ArrayList<C> callbacks, CallbackStorage<C> callbackStorage){
        var res = new ArrayList<T>();

        while (!callbacks.isEmpty()){
            var removingCallbacks = new ArrayList<C>();
            for (var callback: callbacks){
                var awaitingCallback = callbackStorage.getCallback(callback.callbackData());
                if (awaitingCallback == null){
                    continue;
                }
                removingCallbacks.add(callback);
                res.add(callback.execute(null));
            }
            callbackStorage.removeCallbacks(removingCallbacks);
        }
        return CompletableFuture.completedFuture(res);
    }

    public CompletableFuture<T> getCallback(ArrayList<C> callbacks, CallbackStorage<C> callbackStorage){
        CompletableFuture<T> result = new CompletableFuture<>();

        scheduler.execute(() -> {

            System.out.println("Start to await callback");

            while (!callbacks.isEmpty() && !result.isDone()) {
                System.out.println("In loop");
                System.out.println(callbacks.size());
                var removingCallbacks = new ArrayList<C>();
                for (var callback : callbacks) {
                    var awaitingCallback = callbackStorage.getCallback(callback.callbackData());
                    if (awaitingCallback == null) {
                        continue;
                    }
                    result.complete(callback.execute(null));
                    removingCallbacks.add(callback);
                    break;
                }

                callbackStorage.removeCallbacks(removingCallbacks);

                if (!result.isDone()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        result.completeExceptionally(e);
                    }
                }
                System.out.println(callbacks.size());
            }
        });

        return result;
    }
}
