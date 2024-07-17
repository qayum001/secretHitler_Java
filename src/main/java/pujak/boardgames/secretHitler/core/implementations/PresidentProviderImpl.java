package pujak.boardgames.secretHitler.core.implementations;

import pujak.boardgames.secretHitler.core.Interfaces.PresidentProvider;
import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.core.models.Player;

import java.util.List;

public class PresidentProviderImpl implements PresidentProvider {

    private final Game game;

    public PresidentProviderImpl(Game game){
        this.game = game;
    }

    @Override
    public Player getNext() {
        return null;
    }

    @Override
    public Player getChosen(List<Player> candidates, Player choosingPlayer) {
        return null;
    }
}
