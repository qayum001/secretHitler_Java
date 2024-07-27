package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.RoomStorage;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;

@Component
public class LeaveRoomCommand implements BotCommand {

    @Autowired
    private RoomStorage gameStorage;

    @Autowired
    private MessageSender messageSender;

    @Override
    public String getCommand() {
        return "leave";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var userId = update.getMessage().getFrom().getId();

        if (!gameStorage.isPlayerInRoom(userId)){
            messageSender.sendMessage(userId, "You are in room already");
            return;
        }

        var playerRoom = gameStorage.getRoomByUserId(userId);

        gameStorage.kickPlayerFromRoom(playerRoom.getHostId(), userId);

        messageSender.sendMessage(userId, "You left the room");
    }
}
