package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackHandler;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotHelper;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.UpdateConsumer;

@Component
public class UpdateConsumerImpl implements UpdateConsumer {

    private final BotHelper botHelper;
    private final CommandFactory commandFactory;
    private final CallbackHandler callbackHandler;

    public UpdateConsumerImpl(BotHelper botHelper, CommandFactory commandFactory, CallbackHandler callbackHandler) {
        this.botHelper = botHelper;
        this.commandFactory = commandFactory;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void consumeMessage(Update update) {
        var text = update.getMessage().getText();
        var commandContent = botHelper.getCommandFromText(text);

        var command = commandFactory.getCommand(commandContent.command());
        command.Execute(update, commandContent);
    }

    @Override
    public void consumeCallbackQuery(Update update) {
        var cbData = botHelper.getDataFromCallback(update.getCallbackQuery().getData());

        callbackHandler.handleCallback(cbData);
    }
}