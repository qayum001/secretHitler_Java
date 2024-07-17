package pujak.boardgames.secretHitler.telegramBot.interfaces.services;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotHelper {
    String gatCommandFromMessage(Message message);
    String getCommandFormText(String text);
}
