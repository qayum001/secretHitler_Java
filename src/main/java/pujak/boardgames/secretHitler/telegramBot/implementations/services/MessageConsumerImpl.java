package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotHelper;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.MessageConsumer;

@Component
public class MessageConsumerImpl implements MessageConsumer {

    private final BotHelper botHelper;
    private final CommandFactory commandFactory;

    public MessageConsumerImpl(BotHelper botHelper, CommandFactory commandFactory) {
        this.botHelper = botHelper;
        this.commandFactory = commandFactory;
    }

    @Override
    public void consumeUpdate(Update update) {
        var text = update.getMessage().getText();
        var commandContent = botHelper.getCommandFromText(text);

        var command = commandFactory.getCommand(commandContent.command());
        command.Execute(update, commandContent);
    }
}