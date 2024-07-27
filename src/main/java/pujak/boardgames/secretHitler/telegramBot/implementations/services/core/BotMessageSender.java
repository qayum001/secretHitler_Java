package pujak.boardgames.secretHitler.telegramBot.implementations.services.core;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.SecretHitlerBot;

import java.util.List;

@Service
public class BotMessageSender implements MessageSender {

    @Autowired
    private SecretHitlerBot secretHitlerBot;

    @Override
    public void sendMessage(long receiverId, String message) {
        var sendMessage = SendMessage.builder().chatId(String.valueOf(receiverId)).text(message).parseMode("html").build();

        try {
            secretHitlerBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessageToMany(List<Player> receiverIds, String message) {
        var ids = receiverIds.stream().map(Player::getTelegramId).toList();

        for (var id: ids){
            var sendMessage = SendMessage.builder().text(message).parseMode("html").chatId(String.valueOf(id)).build();
            try {
                secretHitlerBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
