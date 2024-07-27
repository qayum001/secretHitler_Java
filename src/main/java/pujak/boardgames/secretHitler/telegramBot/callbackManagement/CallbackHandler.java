package pujak.boardgames.secretHitler.telegramBot.callbackManagement;

import org.springframework.beans.factory.annotation.Autowired;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.ElectionCallback;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameStorage;

public class CallbackHandler {

    @Autowired
    private CallbackStorage<ElectionCallback> electionCallbackStorage;

    @Autowired
    private GameStorage gameStorage;

    public void handleCallback(CallbackData callbackData){
        var room = gameStorage.getRoom(callbackData.roomId());

        electionCallbackStorage.getCallback(callbackData).execute(room.getGame());
    }
}
