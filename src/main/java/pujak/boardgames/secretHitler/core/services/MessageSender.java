package pujak.boardgames.secretHitler.core.services;

import java.util.*;

import com.google.common.primitives.UnsignedInteger;
import pujak.boardgames.secretHitler.core.models.Player;

public interface MessageSender {
    void sendMessage(long receiverId, String message);
    
    void sendMessageToMany(List<Player> receiverIds, String message);

}