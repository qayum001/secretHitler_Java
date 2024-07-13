package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.Article;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.MessageSender;

import java.util.ArrayList;
import java.util.HashMap;

public class ArticlePeekEvent implements GameEvent{
    private final MessageSender messageSender;
    private Table table;
    private boolean isExecuted;

    public ArticlePeekEvent(MessageSender messageSender){
        this.messageSender = messageSender;
    }
    @Override
    public void Execute(Delegatable delegatable) {
        System.out.println("From Article Peek event");

        isExecuted = true;
        var message = new StringBuilder();

        message.append(" Here you can see three card from top of draw pile: \n");
        for (var i = 0; i < 3; i++) {
            var article = table.getDrawPile().get(i);
            message.append("Article #").append(i).append(" is ").append(article.getType()).append("\n");
        }
        message.append("end. \n");

        var presidentId = table.getPresident().getId();
        messageSender.sendMessage(presidentId, message.toString());

        isExecuted = true;
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        this.table = table;
        if (isExecuted)
            return false;

        var gameType = table.getGame().getGameType();

        if (gameType == GameType.Small){
            return table.getFascistActiveArticles().size() == 1;
        }
        return false;
    }
}
