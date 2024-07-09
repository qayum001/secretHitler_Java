package pujak.boardgames.secretHitler.console;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.models.Player;

public class ConsoleElectionManager implements ElectionManager {


    @Override
    public boolean isElectionSucceed(UnsignedInteger recieverId, Map<UnsignedLong, String> data) {
        return false;
    }

    @Override
    public ArrayList<String> getVotes(ArrayList<Player> voters, ArrayList<String> variants, String message) {
        return null;
    }

    @Override
    public UnsignedInteger getChosenCandidate(UnsignedInteger recieverId, Map<UnsignedInteger, String> data) {
        return null;
    }
}
