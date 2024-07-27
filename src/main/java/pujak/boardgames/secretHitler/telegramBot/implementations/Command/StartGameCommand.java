package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameRunner;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.RoomStorage;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;

@Component
public class StartGameCommand implements BotCommand {

    @Autowired
    private RoomStorage gameStorage;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private GameRunner gameRunner;

    @Override
    public String getCommand() {
        return "start_game";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var hostUser = update.getMessage().getFrom();

        var hostsRoom = gameStorage.getRoomByUserId(hostUser.getId());

        if (!hostsRoom.isStartAvailable()){
            messageSender.sendMessage(hostUser.getId(), "Not correct player count");
            return;
        }

        gameRunner.startGame(hostsRoom);
    }
}
