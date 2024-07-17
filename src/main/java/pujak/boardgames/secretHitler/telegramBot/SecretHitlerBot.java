package pujak.boardgames.secretHitler.telegramBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.MessageConsumer;

@Component
public class SecretHitlerBot extends TelegramLongPollingBot{

    private final MessageConsumer messageConsumer;

    public SecretHitlerBot(MessageConsumer messageConsumer){
        this.messageConsumer = messageConsumer;
    }

    @Override
    public String getBotUsername() {
        return "https://t.me/pujak_secret_hitler_bot";
    }

    @Override
    public String getBotToken() {
        return "7204853550:AAHnHJ9F6ZIu4N5wmuShxwEKseHeG0T8olg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        messageConsumer.consumeUpdate(update);
    }
}
