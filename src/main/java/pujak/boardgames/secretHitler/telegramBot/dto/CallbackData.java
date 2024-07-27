package pujak.boardgames.secretHitler.telegramBot.dto;

import java.util.Objects;

public record CallbackData (long roomId, long playerId, String message){
    @Override
    public String toString(){
        return roomId + " " + playerId + " " + message;
    }

    public boolean equals(CallbackData callbackData){
        var room = roomId == callbackData.roomId;
        var player = playerId == callbackData.playerId;
        var message = Objects.equals(message(), callbackData.message());

        return room && player && message;
    }
}
