package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import org.telegram.telegrambots.meta.api.objects.Message;
import pujak.boardgames.secretHitler.telegramBot.dto.CallbackData;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperImpl implements BotHelper {
    @Override
    public String gatCommandFromMessage(Message message) {
        return null;
    }

    @Override
    public CommandContent getCommandFromText(String text) {
        Pattern pattern = Pattern.compile("^/(\\w+)(?:\\s+(.+))?");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return  new CommandContent(matcher.group(1), matcher.group(2));
        }
        return new CommandContent("", "");
    }

    @Override
    public String createCallbackData(long roomId, long playerId, String data) {
        return String.valueOf(roomId) + " " + playerId + " " + data;
    }

    @Override
    public CallbackData getDataFromCallback(String callback) {
        var parts = callback.split(" ");

        var room = Long.parseLong(parts[0]);
        var player = Long.parseLong(parts[1]);

        return new CallbackData(room, player, parts[2]);
    }
}
