package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.*;

public interface GameEvent {
    void Execute(Delegatable delegatable);

    //check for
    boolean isConditionsMatched(Table table);
}