package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameStorage;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.RoomFactory;

public class CreateRoomBotCommand implements BotCommand {

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private RoomFactory roomFactory;
    @Autowired
    private GameStorage gameStorage;

    @Override
    public String getCommand() {
        return "create_room";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var user = update.getMessage().getFrom();
        var room = roomFactory.createRoom();

        if (gameStorage.isPlayerInRoom(user.getId())){
            messageSender.sendMessage(user.getId(), userInRoom());
            return;
        }

        var hostPlayer = new Player(user.getId(), user.getFirstName());

        gameStorage.addPlayerToRoom(room, hostPlayer);

        gameStorage.AddRoom(room.getHostId(), room);

        messageSender.sendMessage(user.getId(), getRoomCreateString(room.getHostId()));
    }

    private String getRoomCreateString(long id){
        return "Room created \n" +
               "Your room id <code> %d <code>".formatted(id) +
               "\nOther player can join to you room via this id";
    }

    private String userInRoom(){
        return "You are in room already";
    }
}
