package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameStorage;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;

public class JoinRoomCommand implements BotCommand {

    @Autowired
    private GameStorage gameStorage;
    @Autowired
    private MessageSender messageSender;

    @Override
    public String getCommand() {
        return "join";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var user = update.getMessage().getFrom();

        if (gameStorage.isPlayerInRoom(user.getId())){
            messageSender.sendMessage(user.getId(), "You are in room already");
            return;
        }

        var roomId = (long) 0;
        try {
            roomId = Long.getLong(commandContent.content());
        }
        catch (Exception exception) {
            messageSender.sendMessage(user.getId(), "Room not found or Id is incorrect");
            return;
        }

        if (!gameStorage.isRoomExists(roomId)){
            messageSender.sendMessage(user.getId(), "Room not found or Id is incorrect");
        }

        var player = new Player(user.getId(), user.getFirstName());

        gameStorage.addPlayerToRoom(roomId, player);

        messageSender.sendMessage(player.getTelegramId(), "You are in room now");
    }
}