package pujak.boardgames.secretHitler.telegramBot.interfaces;

public interface CommandFactory {
    Command getCommand(String command);
    void registerCommand(Command command);

}
