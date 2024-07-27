package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.MessageSender;

public class ArticlePeekEvent implements GameEvent{
    private final MessageSender messageSender;
    private Table table;
    private boolean isExecuted;

    public ArticlePeekEvent(MessageSender messageSender){
        this.messageSender = messageSender;
    }
    @Override
    public void Execute(Delegate delegatable) {
        System.out.println("From Article Peek event");

        isExecuted = true;
        var message = new StringBuilder();

        message.append(" Here you can see three card from top of draw pile: \n");
        for (var i = 0; i < 3; i++) {
            var article = table.getDrawPile().get(i);
            message.append("Article #").append(i).append(" is ").append(article.getType()).append("\n");
        }
        message.append("end. \n");

        var presidentId = table.getPresident().getTelegramId();
        messageSender.sendMessage(presidentId, message.toString());

        isExecuted = true;
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        if (isExecuted || table.getGame().isGameOver())
            return false;

        this.table = table;
        var gameType = table.getGame().getGameType();

        if (gameType == GameType.Small){
            return table.getFascistActiveArticles().size() == 3;
        }
        return false;
    }
}
