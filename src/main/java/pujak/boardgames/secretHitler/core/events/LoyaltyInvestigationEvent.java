package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
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
    public void Execute(Delegatable delegatable) {
        System.out.println("From Loyalty Investigation event");

         if (isFirstExecuted)
             isSecondExecuted = true;
         else
             isFirstExecuted = true;

        var currentPresident = table.getPresident();
        var playersInvestigationPull = table.getGame().getActivePlayers(table.getGame().getPlayers());
        playersInvestigationPull.remove(currentPresident);

        var investigationData = table.getGame().getElectionPull((ArrayList<Player>) playersInvestigationPull);
        var playerToInvestigateId = electionManager.getChosenVariant(currentPresident.getId(), investigationData);
        var playerToInvestigate = playersInvestigationPull.stream().filter(e -> e.getId().equals(playerToInvestigateId))
                .findFirst().get();

        var message = "Player: " + playerToInvestigate.getId() + " Party Membership: " + playerToInvestigate.getRole().getParty();
        System.out.println();
        messageSender.sendMessage(currentPresident.getId(), message);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        if (isFirstExecuted && isSecondExecuted)
            return false;

        this.table = table;
        var fascistsArticlesCount = table.getFascistActiveArticles().size();
        var gameType = table.getGame().getGameType();

        if (gameType == GameType.Small)
            return fascistsArticlesCount == 1 && !isFirstExecuted;;
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
