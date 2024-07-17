package pujak.boardgames.secretHitler.telegramBot.interfaces.services;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageConsumer {
    void consumeUpdate(Update update);
}
