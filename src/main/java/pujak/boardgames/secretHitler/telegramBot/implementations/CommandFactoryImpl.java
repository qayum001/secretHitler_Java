package pujak.boardgames.secretHitler.telegramBot.implementations;

import org.springframework.stereotype.Component;
import pujak.boardgames.secretHitler.telegramBot.interfaces.BotCommand;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;

import java.util.ArrayList;

@Component
public class CommandFactoryImpl implements CommandFactory {

    private final ArrayList<BotCommand> botCommands;

    public CommandFactoryImpl(){
        botCommands = new ArrayList<>();
    }

    @Override
    public BotCommand getCommand(String command) {
        System.out.println(command);
        for (var item: botCommands){
            if (command.equals(item.getCommand())){
                return item;
            }
        }
        throw new RuntimeException("CommandNotFound");
    }

    @Override
    public void registerCommand(BotCommand botCommand) {
        botCommands.add(botCommand);
    }
}
