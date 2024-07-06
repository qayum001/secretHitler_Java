package pujak.boardgames.secretHitler.core.Services;

import java.util.ArrayList;
import java.util.Dictionary;

import com.google.common.primitives.UnsignedLong;

public interface MessageSender {
    void sendMessage(UnsignedLong recieverId, String message);
    
    void sendMessageToMany(ArrayList<UnsignedLong> reciecerIds, String message);

    String getChoose(UnsignedLong reciecerId, String message, Dictionary<String, String> variants);

    ArrayList<String> getChooseFromMany(ArrayList<UnsignedLong> reciecerIds, String message,
            Dictionary<String, String> variants);
}