package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameStorage;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;

public class StartGameCommand implements BotCommand {

    @Autowired
    private GameStorage gameStorage;
    @Autowired
    private MessageSender messageSender;

    @Override
    public String getCommand() {
        return "start";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var hostUser = update.getMessage().getFrom();

        var hostsRoom = gameStorage.getRoom(hostUser.getId());

        if (!hostsRoom.isStartAvailable()){
            messageSender.sendMessage(hostUser.getId(), "Not correct player count");
        }
    }
}
