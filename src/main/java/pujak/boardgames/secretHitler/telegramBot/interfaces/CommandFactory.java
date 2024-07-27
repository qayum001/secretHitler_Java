package pujak.boardgames.secretHitler.telegramBot.interfaces;

public interface CommandFactory {
    BotCommand getCommand(String command);
    void registerCommand(BotCommand botCommand);

}
