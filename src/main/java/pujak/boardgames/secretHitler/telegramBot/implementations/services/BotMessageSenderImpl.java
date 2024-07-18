package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pujak.boardgames.secretHitler.telegramBot.SecretHitlerBot;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotMessageSender;

import java.util.List;

public class BotMessageSenderImpl implements BotMessageSender {

    private final SecretHitlerBot secretHitlerBot;

    public BotMessageSenderImpl(SecretHitlerBot secretHitlerBot){
        this.secretHitlerBot = secretHitlerBot;
    }

    @Override
    public void sendToOne(String id, String text) {
        var message = SendMessage.builder().chatId(id).text(text).build();

        try {
            secretHitlerBot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendToMany(List<String> ids, String message) {

    }
}
