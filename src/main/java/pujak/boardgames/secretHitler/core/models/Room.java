package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;

import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.core.events.EventFactory;

public class Room {
    private ArrayList<Player> players;
    private Game game;
    private GameRules gameRules;

    public Room(GameRules gameRules,
            ArticlesProvider articlesProvider,
            ElectionManager electionManager,
            MessageSender messageSender,
            EventFactory eventFactory) {
        this.gameRules = gameRules;
        players = new ArrayList<>();
        
        game = new Game(players,
            messageSender,
            electionManager,
            articlesProvider,
            eventFactory);        
    }

    public void start() {
        System.out.println("room started");
        game.Start(gameRules);
    }

    public void addPlayer(Player player) {
        if (player != null) 
            players.add(player);
    }
    
    public void kickPlayer(Player player) {
        if (player != null && players.contains(player))
            players.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public Game getGame() {
        return game;
    }
}
