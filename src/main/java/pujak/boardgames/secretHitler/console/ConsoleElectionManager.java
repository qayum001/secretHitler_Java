package pujak.boardgames.secretHitler.console;

import java.util.*;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.models.Player;

import static java.lang.System.*;

public class ConsoleElectionManager implements ElectionManager {


    @Override
    public boolean isElectionSucceed(int receiverId, Map<UUID, String> data) {
        return false;
    }

    @Override
    public ArrayList<String> getVotes(List<Player> voters, ArrayList<String> variants, String message) {
        var res = new ArrayList<String>();

        var in = new Scanner(System.in);
        for (var voter: voters){
            System.out.println(voter.getId() + " " + voter.getName());
            while(true){
                for (var variant: variants){
                    System.out.print(variant + " ");
                }
                System.out.println();
                var des = in.nextLine();
                if (variants.contains(des)){
                    res.add(des);
                    break;
                }
            }
        }
        return  res;
    }

    @Override
    public UUID getChosenCandidate(UUID receiverId, Map<UUID, String> data) {
        var res = UUID.randomUUID();

        var in = new Scanner(System.in);
        var keys = data.keySet();
        while(true){
            for (var key: keys){
                System.out.println(key + " " + data.get(key));
            }

            var input = in.nextLine();
            if (data.containsKey(UUID.fromString(input))){
                res = UUID.fromString(input);
                break;
            }
        }
        return res;
    }
}