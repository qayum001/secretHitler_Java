package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;

public class Room {
    private ArrayList<Player> players;
    private Game game;
    private GameRules gameRules;

    public Room(GameRules gameRules) {
        this.gameRules = gameRules;
        players = new ArrayList<>();
        game = new Game(players);        
    }

    public void start() {
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
