package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class SpecialElectionEvent implements GameEvent {
    private Table table;

    private final ElectionManager electionManager;
    private final ArticlesProvider articlesProvider;
    private Game game;
    private GameType gameType;
    private boolean isExecuted;

    public SpecialElectionEvent(ElectionManager electionManager, ArticlesProvider articlesProvider){
        this.electionManager = electionManager;
        this.articlesProvider = articlesProvider;
    }

    @Override
    public void Execute(Delegate delegate) {

        game.incrementStageCounter();

        System.out.println("From Special Elections event");

        var currentPresident = table.getPresident();
        var activePlayers = game.getActivePlayers(table.getGame().getPlayers());
        var chosenPresidentId = getChosenPresidentId((ArrayList<Player>) activePlayers, currentPresident, game);

        var candidates = game.generateCandidatePull(currentPresident, table.getPreviousChancellor(), activePlayers);
        var newPresident = activePlayers.stream().filter(e -> e.getId().equals(chosenPresidentId)).findFirst().orElseThrow();
        candidates.remove(newPresident);

        var electionData = game.getElectionPull(candidates);
        var chosenChancellorId = electionManager.getChosenVariant(chosenPresidentId, electionData);

        var variants = new ArrayList<>(Arrays.asList("Ja", "Nien"));
        var votingResults = electionManager.getVotes(activePlayers, variants, "Vote for Chancellor");//add here candidate name

        if (!game.isElectionSucceed(votingResults)) {
            table.setElectionTracker(table.getElectionTracker() + 1);
            game.checkElectionTracker();
            return;
        }

        var chosenChancellor = activePlayers.stream().filter(e -> e.getId().equals(chosenChancellorId)).findFirst().orElseThrow();
        table.setChancellor(chosenChancellor);

        if (game.isGameOver()){
            System.out.println("Game over triggered in Special elections event");
            return;
        }


        //articles part
        var sendingArticles = table.getTopArticlesByCount(3);
        var presidentDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                "Choose article to discard", chosenPresidentId);

        game.discardArticle(presidentDiscardArticle, sendingArticles);

        if (table.isVetoPowerAvailable()){
            var vetoPowerId = UUID.randomUUID();
            var chancellorDiscardArticle = articlesProvider.getDiscardArticleWithAvailableVetoPower(sendingArticles,
                    "Choose article to discard or veto to discard two articles", chosenChancellorId, vetoPowerId);

            if (chancellorDiscardArticle.equals(vetoPowerId)){
                if (game.isPresidentAgreed()){
                    while (!sendingArticles.isEmpty())
                        game.discardArticle(sendingArticles.getFirst().getId(), sendingArticles);
                }else {
                    var chancellorNewDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                            "Choose article to discard or veto to discard two articles", chosenChancellorId);
                    game.discardArticle(chancellorNewDiscardArticleId, sendingArticles);
                }
            } else { game.discardArticle(chancellorDiscardArticle, sendingArticles); }
        } else {
            var chancellorDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", chosenChancellorId);

            game.discardArticle(chancellorDiscardArticleId, sendingArticles);
        }

        if (!sendingArticles.isEmpty())
            table.addArticleToActives(sendingArticles.getFirst());

        table.setPreviousChancellor(table.getChancellor());
    }

    private UUID getChosenPresidentId (ArrayList<Player> activePlayers, Player currentPresident, Game game){
        var candidatesToBePresident = new ArrayList<Player>();

        candidatesToBePresident = activePlayers;
        candidatesToBePresident.remove(currentPresident);
        var candidatesPull = game.getElectionPull(candidatesToBePresident);

        return  electionManager.getChosenVariant(currentPresident.getId(), candidatesPull);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        if (isExecuted || gameType == GameType.Small || table.getGame().isGameOver())
            return false;

        this.table = table;
        this.game = table.getGame();

        var fascistsActiveArticlesCount = table.getFascistActiveArticles().size();

        return fascistsActiveArticlesCount == 3;
    }
}

