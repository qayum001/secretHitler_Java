package pujak.boardgames.secretHitler.telegramBot.callbackManagement;

import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CallbackStorage<C extends Callback<?>> {
    private final ConcurrentHashMap<CallbackData, C> callbacks;

    {
        callbacks = new ConcurrentHashMap<>();
    }

    public void registerCallbacks(ArrayList<C> callbacks){
        for (var item: callbacks){
            this.callbacks.put(item.callbackData(), item);
        }
    }

    public void registerCallback(C callBack){
        callbacks.put(callBack.callbackData(), callBack);
    }

    public void removeCallbacks(ArrayList<C> callbacks){
        for (var item: callbacks){
            this.callbacks.remove(item.callbackData());
        }
    }

    public void removeCallback(C callback){
        this.callbacks.remove(callback.callbackData());
    }

    public Callback<?> getCallback(CallbackData callback){
        return callbacks.get(callback);
    }
}