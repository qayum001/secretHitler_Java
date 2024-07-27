package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;

@Component
public class EchoBotCommand implements BotCommand {

    @Autowired
    private MessageSender messageSender;

    @Override
    public String getCommand() {
        return "echo";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var senderId = update.getMessage().getFrom().getId();

        messageSender.sendMessage(senderId, commandContent.content());
    }
}
