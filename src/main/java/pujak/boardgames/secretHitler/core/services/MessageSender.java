package pujak.boardgames.secretHitler.core.services;

import java.util.*;

import com.google.common.primitives.UnsignedInteger;

public interface MessageSender {
    void sendMessage(UUID receiverId, String message);
    
    void sendMessageToMany(List<UUID> receiverIds, String message);

    String getChoose(UUID receiverId, String message, Map<String, String> variants);

    ArrayList<String> getChooseFromMany(ArrayList<UUID> receiverId, String message,
            Map<String, String> variants);
}