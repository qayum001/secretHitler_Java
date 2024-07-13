package pujak.boardgames.secretHitler.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pujak.boardgames.secretHitler.core.models.Player;

public interface ElectionManager {
    boolean isElectionSucceed(int receiverId, Map<UUID, String> data);

    ArrayList<String> getVotes(List<Player> voters, ArrayList<String> variants, String message);

    UUID getChosenVariant(UUID receiverId, Map<UUID, String> data);
}