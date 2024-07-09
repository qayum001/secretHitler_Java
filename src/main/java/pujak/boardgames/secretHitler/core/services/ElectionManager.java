package pujak.boardgames.secretHitler.core.services;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.models.Player;

public interface ElectionManager {
    boolean isElectionSucceed(UnsignedInteger recieverId, Map<UnsignedLong, String> data);

    ArrayList<String> getVotes(ArrayList<Player> voters, ArrayList<String> variants, String message);

    UnsignedInteger getChosenCandidate(UnsignedInteger recieverId, Map<UnsignedInteger, String> data);
}