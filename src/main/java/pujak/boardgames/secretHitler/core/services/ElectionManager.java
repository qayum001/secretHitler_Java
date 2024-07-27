package pujak.boardgames.secretHitler.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pujak.boardgames.secretHitler.core.models.Player;

public interface ElectionManager {
    ArrayList<String> getVotes(List<Player> voters, ArrayList<String> variants, String message);

    Long  getChosenVariant(long receiverId, Map<Long, String> data);
}