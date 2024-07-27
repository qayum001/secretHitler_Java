package pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks;

import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.Callback;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;

public record CandidateChoosingCallback(CallbackData callbackData) implements Callback<String> {

    @Override
    public String execute(Game game) {
        return "";
    }
}
