package pujak.boardgames.secretHitler.telegramBot.callbackManagement;

import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.CandidateChoosingCallback;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.ElectionCallback;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.RoomStorage;

import java.util.ArrayList;

@Component
public class CallbackHandler {

    @Autowired
    @Qualifier("electionCallbackStorage")
    private CallbackStorage<ElectionCallback> electionCallbackStorage;

    @Autowired
    @Qualifier("choosingCallbackStorage")
    private CallbackStorage<CandidateChoosingCallback> choosingCallbackStorage;

    @Autowired
    private RoomStorage gameStorage;

    public void handleCallback(CallbackData callbackData){
        var room = gameStorage.getRoom(callbackData.roomId());

        var handlers = List.of(
            electionCallbackStorage,
            choosingCallbackStorage);

        for (var item: handlers){
            if (item.hasCallback(callbackData)){
                item.getCallback(callbackData).execute(room.getGame());
            }
        }
    }
}
