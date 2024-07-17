package pujak.boardgames.secretHitler.core.Interfaces;

import pujak.boardgames.secretHitler.core.models.Player;

import java.util.List;

public interface PresidentProvider {
    Player getNext();
    Player getChosen(List<Player> candidates, Player choosingPlayer);
}
