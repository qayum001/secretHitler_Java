package pujak.boardgames.secretHitler.console;

import java.util.*;

import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.MessageSender;

public class ConsoleMessageSender implements MessageSender {

    @Override
    public void sendMessage(UUID receiverId, String message) {
        System.out.println(receiverId.toString() + message);
    }

    @Override
    public void sendMessageToMany(List<UUID> receiverIds, String message) {
//        System.out.println("To:");
//        for (var item: receiverIds){
//            System.out.println(item.toString());
//        }
        System.out.println("Message to many");
        System.out.println("This message: \n " + message);
    }

    @Override
    public String getChoose(UUID receiverId, String message, Map<String, String> variants) {
        while(true){
            for (var key: variants.keySet()){
                System.out.println(variants.get(key));
            }
            var in = new Scanner(System.in);

            var input = in.nextLine();
            if (variants.containsValue(input)){
                return input;
            }
        }
    }

    @Override
    public ArrayList<String> getChooseFromMany(ArrayList<UUID> receiverId, String message, Map<String, String> variants) {
        return null;
    }
}
