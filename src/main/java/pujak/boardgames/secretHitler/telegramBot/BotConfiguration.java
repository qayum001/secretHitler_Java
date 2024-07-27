package pujak.boardgames.secretHitler.telegramBot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackStorage;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackWaiter;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.CandidateChoosingCallback;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.callbacks.ElectionCallback;
import pujak.boardgames.secretHitler.telegramBot.implementations.Command.*;
import pujak.boardgames.secretHitler.telegramBot.callbackManagement.CallbackHandler;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.GameRunner;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.RoomStorage;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.core.BotArticleProvider;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.core.BotElectionManager;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.core.BotMessageSender;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;
import pujak.boardgames.secretHitler.telegramBot.implementations.CommandFactoryImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.HelperImpl;
import pujak.boardgames.secretHitler.telegramBot.implementations.services.UpdateConsumerImpl;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.BotHelper;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.UpdateConsumer;

@Configuration
public class BotConfiguration {


    //<editor-fold desc="Callback">

    @Bean
    public CallbackStorage<ElectionCallback> electionCallbackStorage(){
        return new CallbackStorage<>();
    }

    @Bean
    public CallbackStorage<CandidateChoosingCallback> choosingCallbackStorage(){
        return new CallbackStorage<>();
    }

    @Bean
    public CallbackWaiter<String, ElectionCallback> electionCallbackWaiter(CallbackStorage<ElectionCallback> callbackStorage){
        return new CallbackWaiter<>();
    }

    @Bean
    public CallbackWaiter<String, CandidateChoosingCallback> candidateChoosingCallbackWaiter(CallbackStorage<CandidateChoosingCallback> callbackStorage){
        return new CallbackWaiter<>();
    }

    @Bean
    public CallbackHandler callbackHandler(){
        return new CallbackHandler();
    }

    //</editor-fold>

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
    public RoomStorage gameStorage() { return new RoomStorage(); }

    @Bean
    public GameRunner gameRunner(){
        return new GameRunner();
    }
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

    @Bean
    public CreateRoomBotCommand createRoomBotCommand(CommandFactory commandFactory){
        var command = new CreateRoomBotCommand();
        commandFactory.registerCommand(command);

        return command;
    }

    @Bean
    public JoinRoomCommand joinRoomCommand(CommandFactory commandFactory){
        var command = new JoinRoomCommand();
        commandFactory.registerCommand(command);

        return command;
    }

    @Bean
    public LeaveRoomCommand leaveRoomCommand(CommandFactory commandFactory){
        var command = new LeaveRoomCommand();
        commandFactory.registerCommand(command);

        return command;
    }

    @Bean
    public StartGameCommand startGameCommand(CommandFactory commandFactory){
        var command = new StartGameCommand();
        commandFactory.registerCommand(command);

        return command;
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
