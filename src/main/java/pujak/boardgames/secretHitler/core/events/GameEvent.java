package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.models.*;

public interface GameEvent {
    void Execute(Delegate delegatable);

    //check for
    boolean isConditionsMatched(Table table);
}