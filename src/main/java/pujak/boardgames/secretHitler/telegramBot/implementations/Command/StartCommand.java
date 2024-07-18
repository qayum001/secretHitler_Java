package pujak.boardgames.secretHitler.telegramBot.implementations.Command;

import org.telegram.telegrambots.meta.api.objects.Update;
import pujak.boardgames.secretHitler.telegramBot.dto.CommandContent;
import pujak.boardgames.secretHitler.telegramBot.interfaces.Command;

public class StartCommand implements Command {
    @Override
    public String getCommand() {
        return "start";
    }

    @Override
    public void Execute(Update update, CommandContent commandContent) {

    }
}
