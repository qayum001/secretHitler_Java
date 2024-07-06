package pujak.boardgames.secretHitler.core.Services;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.models.Player;

public interface ElectionManager {
    boolean isElectionSucceed(UnsignedLong recieverId, Map<UnsignedLong, String> data);

    ArrayList<String> getVotes(ArrayList<Player> voters, ArrayList<String> variants, String message);

    UnsignedLong getChosenCandidate(UnsignedLong recieverId, Map<UnsignedLong, String> data);
}