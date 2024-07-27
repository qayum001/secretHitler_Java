package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.core.models.GameResult;
import pujak.boardgames.secretHitler.core.models.GameRules;
import pujak.boardgames.secretHitler.core.models.Room;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GameRunner {

    private final ExecutorService executorService;
    private final Map<Long, Game> activeGames = new ConcurrentHashMap<>();

    public GameRunner(){
        executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    public void startGame(Room room) {
        CompletableFuture.runAsync(() -> {
            var game = room.getGame();
            activeGames.put(room.getHostId(), room.getGame());
            try {
                var gameResult = room.start();
                saveGameResults(gameResult, game);
            } finally {
                activeGames.remove(room.getHostId());
            }
        }, executorService);
    }

    public Map<Long, Game> getActiveGames(){
        return activeGames;
    }

    private void saveGameResults(GameResult gameResult, Game game){

    }
}
