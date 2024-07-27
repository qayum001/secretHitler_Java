package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.persistence.dbModels.PlayerModel;
import pujak.boardgames.secretHitler.persistence.respositories.PlayerRepository;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;

@Component
public class StartBotCommand implements BotCommand {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private MessageSender messageSender;

    @Override
    public String getCommand() {
        return "start";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var user = update.getMessage().getFrom();

        var player = new PlayerModel(user.getId(), user.getFirstName());

        if (playerRepository.findById(player.getId()).isPresent())
            return;

        playerRepository.save(player);
    }
}