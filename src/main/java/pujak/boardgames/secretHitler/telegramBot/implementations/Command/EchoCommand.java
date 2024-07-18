package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.interfaces.Command;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotMessageSender;

@Component
public class EchoCommand implements Command {

    private final BotMessageSender messageSender;

    public EchoCommand(BotMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public String getCommand() {
        return "echo";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {
        var messageText = update.getMessage().getText();
        var senderId = update.getMessage().getFrom().getId();

        messageSender.sendToOne(senderId.toString(), commandContent.content());
    }
}
