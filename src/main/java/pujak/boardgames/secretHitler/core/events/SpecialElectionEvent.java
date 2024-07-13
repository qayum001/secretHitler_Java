package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SpecialElectionEvent implements GameEvent {
    private Table table;

    private final ElectionManager electionManager;
    private final ArticlesProvider articlesProvider;

    private GameType gameType;
    private boolean isExecuted;

    public SpecialElectionEvent(ElectionManager electionManager, ArticlesProvider articlesProvider){
        this.electionManager = electionManager;
        this.articlesProvider = articlesProvider;
    }

    @Override
    public void Execute(Delegatable delegatable) {
        var candidatesToBePresident = new ArrayList<Player>();
        var currentPresident = table.getPresident();
        var game = table.getGame();
        var activePlayers = game.getActivePlayers(table.getGame().getPlayers());

        candidatesToBePresident = (ArrayList<Player>) activePlayers;
        candidatesToBePresident.remove(currentPresident);
        var candidatesPull = game.getElectionPull(candidatesToBePresident);

        var chosenPresidentId = electionManager.getChosenCandidate(currentPresident.getId(), candidatesPull);

        var candidates = game.generateCandidatePull(currentPresident, table.getPreviousChancellor(), activePlayers);
        var electionData = game.getElectionPull(candidates);
        var chosenChancellorId = electionManager.getChosenCandidate(chosenPresidentId, electionData);

        var variants = new ArrayList<>(Arrays.asList("Ja", "Nien"));
        var votingResults = electionManager.getVotes(activePlayers, variants, "Vote for Chancellor");//add here candidate name

        if (game.isElectionSucceed(votingResults)) {
            //table.setElectionTracker(table.getElectionTracker() + 1);
            return;
        }

        //articles part
        var sendingArticles = table.getTopTreeArticles();
        var presidentDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                "Choose article to discard", chosenPresidentId);

        var discartingArticle = sendingArticles.stream().filter(e -> e.getId().equals(presidentDiscardArticle))
                .findFirst().get();
        table.discardArticle(discartingArticle);

        //send here message to President that his next message will be sent to other players, and he must write what cards he got

        sendingArticles.remove(discartingArticle);

        var chancellorDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                "Choose article to discard", chosenChancellorId);

        discartingArticle = sendingArticles.stream().filter(e -> e.getId().equals(chancellorDiscardArticle))
                .findFirst().get();
        table.discardArticle(discartingArticle);

        //add left article to actives
        //add veto logic
        table.addArticleToActives(sendingArticles.getFirst());

        var newPreviousChancellor = activePlayers.stream().filter(e -> e.getId().equals(chosenChancellorId))
                .findFirst().get();
        table.setPreviousChancellor(newPreviousChancellor);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        if (isExecuted || gameType == GameType.Small)
            return false;
        this.table = table;

        var fascistsActiveArticlesCount = table.getFascistActiveArticles().size();

        return fascistsActiveArticlesCount == 3;
    }
}
