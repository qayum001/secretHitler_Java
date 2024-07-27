package pujak.boardgames.secretHitler.telegramBot.callbackManagement;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.concurrent.*;

public class CallbackWaiter<T, C extends Callback<T>> { // T is callback returning type, C is Callback type

    @Autowired
    private CallbackStorage<C> callbackStorage;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public CompletableFuture<ArrayList<T>> getCallbacks(ArrayList<C> callbacks){
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

    public CompletableFuture<T> getCallback(ArrayList<C> callbacks){
        CompletableFuture<T> result = new CompletableFuture<>();

        scheduler.execute(() -> {
            while (!callbacks.isEmpty() && !result.isDone()) {
                var removingCallbacks = new ArrayList<C>();
                for (var callback : callbacks) {
                    var awaitingCallback = callbackStorage.getCallback(callback.callbackData());
                    if (awaitingCallback == null) {
                        continue;
                    }
                    result.complete(callback.execute(null));
                    removingCallbacks.add(callback);
                    break; // Exit the loop once a callback is found and executed
                }

                callbackStorage.removeCallbacks(removingCallbacks);

                if (!result.isDone()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100); // Delay for 500 milliseconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        result.completeExceptionally(e);
                    }
                }
            }
        });

        return result;
    }
}
