package pujak.boardgames.secretHitler.telegramBot.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;

public interface Command {
    String getCommand();
    void Execute(Update update, CommandContent commandContent);
}
