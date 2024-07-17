package pujak.boardgames.secretHitler.telegramBot.interfaces.services;

import java.util.List;

public interface BotMessageSender {
    void sendToOne(String id, String message);
    void sendToMany(List<String> ids, String message);
}
