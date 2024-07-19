package pujak.boardgames.secretHitler.telegramBot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pujak.boardgames.secretHitler.telegramBot.implementations.Command.StartCommand;
import pujak.boardgames.secretHitler.telegramBot.interfaces.Command;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;
import pujak.boardgames.secretHitler.telegramBot.implementations.Command.EchoCommand;
import pujak.boardgames.secretHitler.telegramBot.implementations.CommandFactoryImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.BotMessageSenderImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.HelperImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.MessageConsumerImpl;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotHelper;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotMessageSender;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.MessageConsumer;

@Configuration
public class BotConfiguration {

    //<editor-fold desc="Services">
    @Bean
    public CommandFactory commandFactory(){
        return new CommandFactoryImpl();
    }

    @Bean
    public BotHelper botHelper(){
        return new HelperImpl();
    }

    @Bean
    public MessageConsumer messageConsumer(BotHelper helper, CommandFactory commandFactory){
        return new MessageConsumerImpl(helper, commandFactory);
    }

    @Bean
    public BotMessageSender messageSender(SecretHitlerBot secretHitlerBot){
        return new BotMessageSenderImpl(secretHitlerBot);
    }
    //</editor-fold>

    //<editor-fold desc="Commands">
    @Bean
    public Command addStartCommand(CommandFactory commandFactory){
        var command = new StartCommand();
        commandFactory.registerCommand(command);
        return command;
    }

    @Bean
    public Command addEchoCommand(CommandFactory commandFactory, BotMessageSender messageSender){
        var command = new EchoCommand(messageSender);
        commandFactory.registerCommand(command);

        return  command;
    }
    //</editor-fold>

    // <editor-fold desc="Bot">
    @Bean
    public SecretHitlerBot secretHitlerBot(MessageConsumer messageConsumer){
        return new SecretHitlerBot(messageConsumer);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(SecretHitlerBot secretHitlerBot) throws TelegramApiException{
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(secretHitlerBot);
        return api;
    }
    //</editor-fold>
}
