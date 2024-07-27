package pujak.boardgames.secretHitler.telegramBot.callbackManagement;

import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;

public interface Callback <T> {
    CallbackData callbackData();

    T execute(Game game);
}
