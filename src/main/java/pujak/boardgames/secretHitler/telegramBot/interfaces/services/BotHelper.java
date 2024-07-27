package pujak.boardgames.secretHitler.telegramBot.interfaces.services;

import org.telegram.telegrambots.meta.api.objects.Message;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;

public interface BotHelper {
    String gatCommandFromMessage(Message message);
    CommandContent getCommandFromText(String text);
    String createCallbackData(long roomId, long playerId, String data);
    CallbackData getDataFromCallback(String callback);
}
