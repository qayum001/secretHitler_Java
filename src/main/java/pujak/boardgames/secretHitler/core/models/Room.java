package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;

import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.services.MessageSender;

public class Room {
    private final ArrayList<Player> players;
    private final Game game;
    private final GameRules gameRules;

    public Room(GameRules gameRules,
            ArticlesProvider articlesProvider,
            MessageSender messageSender,
            ElectionManager electionManager,
            EventFactory eventFactory) {
        this.gameRules = gameRules;
        players = new ArrayList<>();
        
        game = new Game(players,
            electionManager,
            messageSender,
            articlesProvider,
            eventFactory);        
    }

    public GameResult start() {
        System.out.println("room started");
        return game.Start(gameRules);
    }

    public void addPlayer(Player player) {
        if (player != null) 
            players.add(player);
    }
}
