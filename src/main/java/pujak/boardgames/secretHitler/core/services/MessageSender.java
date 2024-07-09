package pujak.boardgames.secretHitler.core.services;

import java.util.ArrayList;
import java.util.Dictionary;

import com.google.common.primitives.UnsignedInteger;

public interface MessageSender {
    void sendMessage(UnsignedInteger recieverId, String message);
    
    void sendMessageToMany(ArrayList<UnsignedInteger> reciecerIds, String message);

    String getChoose(UnsignedInteger reciecerId, String message, Dictionary<String, String> variants);

    ArrayList<String> getChooseFromMany(ArrayList<UnsignedInteger> reciecerIds, String message,
            Dictionary<String, String> variants);
}