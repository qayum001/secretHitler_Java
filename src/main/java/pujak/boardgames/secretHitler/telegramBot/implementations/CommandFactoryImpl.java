package pujak.boardgames.secretHitler.telegramBot.implementations;

import org.springframework.stereotype.Component;
import pujak.boardgames.secretHitler.telegramBot.interfaces.Command;
import pujak.boardgames.secretHitler.telegramBot.interfaces.CommandFactory;

import java.util.ArrayList;

@Component
public class CommandFactoryImpl implements CommandFactory {

    private final ArrayList<Command> commands;

    public CommandFactoryImpl(){
        commands = new ArrayList<>();
    }

    @Override
    public Command getCommand(String command) {
        System.out.println(command);
        for (var item: commands){
            if (command.equals(item.getCommand())){
                return item;
            }
        }
        throw new RuntimeException("CommandNotFound");
    }

    @Override
    public void registerCommand(Command command) {
        commands.add(command);
    }
}
