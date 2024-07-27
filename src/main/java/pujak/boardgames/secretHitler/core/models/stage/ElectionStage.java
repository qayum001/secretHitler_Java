package pujak.boardgames.secretHitler.core.models.stage;

import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.core.models.GameResult;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.services.ElectionManager;

import java.util.ArrayList;
import java.util.Arrays;

public class ElectionStage implements Stage {
    private final Game game;
    private final ElectionManager electionManager;

    private boolean isGameOver;
    private GameResult gameResult;

    public ElectionStage(Game game, ElectionManager electionManager){
        this.game = game;
        this.electionManager = electionManager;
    }

    @Override
    public void runStage() {
        var table = game.getTable();
        var activePlayers = game.getActivePlayers(game.getPlayers());

        table.setPresident((ArrayList<Player>) activePlayers);

        System.out.println("President: " + table.getPresident().getName());

        var candidates = game.generateCandidatePull(table.getPresident(), table.getPreviousChancellor(), activePlayers);
        var electionData = game.getElectionPull(candidates);
        var chosenCandidateId = electionManager.getChosenVariant(table.getPresident().getTelegramId(), electionData);

        var variants = new ArrayList<>(Arrays.asList("Ja", "Nien"));
        var votingResults = electionManager.getVotes(activePlayers, variants, "Vote for Chancellor");//add here candidate name

        if (!game.isElectionSucceed(votingResults)) {
            table.setElectionTracker(table.getElectionTracker() + 1);
            game.checkElectionTracker();
        }
        activePlayers.stream().filter(e -> e.getTelegramId() == chosenCandidateId).findFirst().ifPresent(table::setChancellor);
    }
}
