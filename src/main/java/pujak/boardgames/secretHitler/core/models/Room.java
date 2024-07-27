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
    private final long hostId;

    public Room(GameRules gameRules,
            ArticlesProvider articlesProvider,
            MessageSender messageSender,
            ElectionManager electionManager,
            EventFactory eventFactory,
            long hostId) {

        this.gameRules = gameRules;
        this.hostId = hostId;
        players = new ArrayList<>();
        
        game = new Game(players,
            electionManager,
            messageSender,
            articlesProvider,
            eventFactory);
    }

    public boolean isStartAvailable(){
        var count = players.size();
        return count >= gameRules.minPlayersToStart() && count <= gameRules.maxPlayersToStart();
    }

    public GameResult start() {
        return game.Start(gameRules);
    }

    public Game getGame(){
        return this.game;
    }

    public boolean isPlayerInThisRoom(Player player){
        return players.contains(player);
    }

    public boolean isPlayerInThisRoom(Long id){
        return players.stream().anyMatch(e -> e.getTelegramId() == id);
    }

    public void addPlayer(Player player) {
        if (player != null) {
            player.setInRoom(true);
            players.add(player);
        }
    }

    public long getHostId() {
        return hostId;
    }
}
