package pujak.boardgames.secretHitler.console;

import java.util.*;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.models.Player;

import static java.lang.System.*;

public class ConsoleElectionManager implements ElectionManager {

    @Override
    public ArrayList<String> getVotes(List<Player> voters, ArrayList<String> variants, String message) {
        var res = new ArrayList<String>();

//        var in = new Scanner(System.in);
//        for (var voter: voters){
//            System.out.println(voter.getId() + " " + voter.getName() + " idDead: " + voter.isDead());
//            while(true){
//                for (var variant: variants){
//                    System.out.print(variant + " ");
//                }
//                System.out.println();
//                var des = in.nextLine();
//                if (variants.contains(des)){
//                    res.add(des);
//                    break;
//                }
//            }
//        }

        for (var i = 0; i < 5; i++) {
            res.add("Ja");
        }

        return  res;
    }

    @Override
    public Long getChosenVariant(long receiverId, Map<Long, String> data) {
        var in = new Scanner(System.in);
        var keys = data.keySet();
        while(true){
            for (var key: keys){
                System.out.println(key + " " + data.get(key));
            }

            var input = in.nextLong();
            if (data.containsKey(input))
                return input;
        }
    }
}