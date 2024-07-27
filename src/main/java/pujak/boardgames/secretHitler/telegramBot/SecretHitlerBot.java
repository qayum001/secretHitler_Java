package pujak.boardgames.secretHitler.telegramBot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.UpdateConsumer;

@Component
public class SecretHitlerBot extends TelegramLongPollingBot{

    private final UpdateConsumer updateConsumer;

    public SecretHitlerBot(UpdateConsumer messageConsumer){
        this.updateConsumer = messageConsumer;
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
        if (update.hasCallbackQuery()){
            updateConsumer.consumeCallbackQuery(update);
            return;
        }
        updateConsumer.consumeMessage(update);
    }
}
