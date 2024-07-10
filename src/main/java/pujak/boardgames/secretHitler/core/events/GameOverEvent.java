package pujak.boardgames.secretHitler.core.events;

import java.util.ArrayList;
import java.util.UUID;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.models.GameResult;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.models.Enums.Party;
import pujak.boardgames.secretHitler.core.models.Enums.ResponsibilityType;

public class GameOverEvent implements GameEvent {

    private Table table;
    private Party winnerParty;

    @Override
    public void Execute(Delegatable delegatable) {
        var winners = new ArrayList<Player>();

        for (Player player : table.getGame().getPlayers()) {
            if (player.getRole().getParty() == winnerParty) {
                winners.add(player);
            }
        }

        delegatable.Execute(new GameResult(winners, table.getGame().getPlayers(), winnerParty, UUID.randomUUID()));
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        this.table = table;
        
        if (table.getFascistActiveArticles().size() >= 3
                && table.getChancellor().getRole().getResponsibilityType() == ResponsibilityType.Fascist) {
            this.winnerParty = Party.Fascist;
            return true;
        }
        var gameRules = table.getGameRules();

        if (table.getFascistActiveArticles().size() == gameRules.fascistWinArticlesCount()) {
            this.winnerParty = Party.Fascist;
            return true;
        }

        if (table.getLiberalActiveArticles().size() == gameRules.liberalWinArticlesCount()) {
            this.winnerParty = Party.Liberal;
            return true;
        }

        return false;
    }
}