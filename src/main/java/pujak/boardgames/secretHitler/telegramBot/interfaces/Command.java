package pujak.boardgames.secretHitler.telegramBot.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String getCommand();
    void Execute(Update update);
}
