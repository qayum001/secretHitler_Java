package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;

import java.util.ArrayList;

public class LoyaltyInvestigationEvent implements GameEvent {
    private Table table;
    private boolean isFirstExecuted;
    private boolean isSecondExecuted;

    private final ElectionManager electionManager;
    private final MessageSender messageSender;


    public LoyaltyInvestigationEvent(ElectionManager electionManager, MessageSender messageSender){
        this.electionManager = electionManager;
        this.messageSender = messageSender;
    }

    @Override
    public void Execute(Delegate delegate) {
        System.out.println("From Loyalty Investigation event");

         if (isFirstExecuted)
             isSecondExecuted = true;
         else
             isFirstExecuted = true;

        var currentPresident = table.getPresident();
        var playersInvestigationPull = table.getGame().getActivePlayers(table.getGame().getPlayers());
        playersInvestigationPull.remove(currentPresident);

        var investigationData = table.getGame().getElectionPull((ArrayList<Player>) playersInvestigationPull);
        var playerToInvestigateId = electionManager.getChosenVariant(currentPresident.getTelegramId(), investigationData);
        var playerToInvestigate = playersInvestigationPull.stream().filter(e -> e.getTelegramId() == playerToInvestigateId)
                .findFirst().orElseThrow();

        var message = "Player: " + playerToInvestigate.getTelegramId() + " Party Membership: " + playerToInvestigate.getRole().getParty();
        System.out.println();
        messageSender.sendMessage(currentPresident.getTelegramId(), message);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        if (isFirstExecuted && isSecondExecuted || table.getGame().isGameOver())
            return false;

        this.table = table;
        var fascistsArticlesCount = table.getFascistActiveArticles().size();
        var gameType = table.getGame().getGameType();

        if (gameType == GameType.Small)
            return false;
        if (gameType == GameType.Usual)
            return  fascistsArticlesCount == 2 && !isFirstExecuted;
        if (gameType == GameType.Big){
           if (fascistsArticlesCount == 1 && !isFirstExecuted)
               return true;
            return fascistsArticlesCount == 2 && !isSecondExecuted;
        }

        return false;
    }
}
