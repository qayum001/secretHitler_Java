package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.MessageSender;

import java.util.stream.Collectors;

public class StageEndEvent implements GameEvent {
    private final MessageSender messageSender;
    private Table table;

    public StageEndEvent(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    @Override
    public void Execute(Delegate delegate) {

        var game = table.getGame();
        System.out.println("This message from event");

        table.setPreviousChancellor(table.getChancellor());
        table.setPreviousPresident(table.getPresident());

        var message = table.getTableInfo();
        messageSender.sendMessageToMany(game.getPlayers().stream().map(Player::getId).collect(Collectors.toList()), message);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        this.table = table;
        return !table.getGame().isGameOver();
    }
}
