package pujak.boardgames.secretHitler.console;

import java.util.*;

import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.services.MessageSender;

public class ConsoleMessageSender implements MessageSender {

    @Override
    public void sendMessage(long receiverId, String message) {
        System.out.println(receiverId + message);
    }

    @Override
    public void sendMessageToMany(List<Player> receiverIds, String message) {
        System.out.println("Message to many");
        System.out.println("This message: \n " + message);
    }
}
