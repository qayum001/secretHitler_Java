package pujak.boardgames.secretHitler.telegramBot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.implementations.Command.StartBotCommand;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackHandler;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameStorage;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.core.BotArticleProvider;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.core.BotElectionManager;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.core.BotMessageSender;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;
import pujak.boardgames.secretHitler.telegramBot.implementations.Command.EchoBotCommand;
import pujak.boardgames.secretHitler.telegramBot.implementations.CommandFactoryImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.HelperImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.UpdateConsumerImpl;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotHelper;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.UpdateConsumer;

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
    public UpdateConsumer messageConsumer(BotHelper helper, CommandFactory commandFactory, CallbackHandler callbackHandler){
        return new UpdateConsumerImpl(helper, commandFactory, callbackHandler);
    }

    @Bean
    public MessageSender messageSender(){
        return new BotMessageSender();
    }

    @Bean
    public ArticlesProvider articlesProvider(){
        return new BotArticleProvider();
    }

    @Bean
    public ElectionManager electionManager(){
        return new BotElectionManager();
    }

    @Bean
    public GameStorage gameStorage() { return new GameStorage(); }
    //</editor-fold>

    //<editor-fold desc="Commands">
    @Bean
    public BotCommand addStartCommand(CommandFactory commandFactory){
        var command = new StartBotCommand();
        commandFactory.registerCommand(command);
        return command;
    }

    @Bean
    public BotCommand addEchoCommand(CommandFactory commandFactory){
        var command = new EchoBotCommand();
        commandFactory.registerCommand(command);

        return  command;
    }
    //</editor-fold>

    // <editor-fold desc="Bot">
    @Bean
    public SecretHitlerBot secretHitlerBot(UpdateConsumer messageConsumer){
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
