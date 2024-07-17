package pujak.boardgames.secretHitler.telegramBot;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfiguration {
    public TelegramBotsApi telegramBotsApi(SecretHitlerBot secretHitlerBot) throws TelegramApiException{
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(secretHitlerBot);
        return api;
    }
}
