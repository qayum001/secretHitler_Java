package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.models.Enums.ResponsibilityType;

public class GameOverEvent implements GameEvent {

    private Table table;

    @Override
    public void Execute(Delegatable delegatable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Execute'");
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        this.table = table;
        
        if (table.getFascistActiveArticles().size() >= 3
                && table.getChancellor().getRole().getResponsibilityType() == ResponsibilityType.Fascist) {
            return true;
        }
        var gameRules = table.getGameRules();
        if ()

        return false;
    }
}
