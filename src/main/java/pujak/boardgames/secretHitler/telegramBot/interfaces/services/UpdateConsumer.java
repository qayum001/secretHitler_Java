package pujak.boardgames.secretHitler.telegramBot.interfaces.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateConsumer {
    void consumeMessage(Update update);
    void consumeCallbackQuery(Update update);
}
