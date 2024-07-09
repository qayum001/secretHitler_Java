package pujak.boardgames.secretHitler.console;

import java.util.ArrayList;
import java.util.Dictionary;

import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.MessageSender;

public class ConsoleMessageSender implements MessageSender {

    @Override
    public void sendMessage(UnsignedLong recieverId, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
    }

    @Override
    public void sendMessageToMany(ArrayList<UnsignedLong> reciecerIds, String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessageToMany'");
    }

    @Override
    public String getChoose(UnsignedLong reciecerId, String message, Dictionary<String, String> variants) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChoose'");
    }

    @Override
    public ArrayList<String> getChooseFromMany(ArrayList<UnsignedLong> reciecerIds, String message,
            Dictionary<String, String> variants) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getChooseFromMany'");
    }
    
}
