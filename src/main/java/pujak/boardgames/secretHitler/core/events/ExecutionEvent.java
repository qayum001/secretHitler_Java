package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.ElectionManager;

import java.util.HashMap;
import java.util.UUID;

public class ExecutionEvent implements GameEvent{
    private final ElectionManager electionManager;
    private Table table;

    private boolean isFirstExecutionDone;
    private boolean isSecondExecutionDone;

    public ExecutionEvent(ElectionManager electionManager){
        this.electionManager = electionManager;
    }

    @Override
    public void Execute(Delegate delegatable) {
        System.out.println("From Execution event");

        if (isFirstExecutionDone)
            isSecondExecutionDone = true;
        else
            isFirstExecutionDone = true;

        var players = table.getGame().getPlayers();
        var presidentId = table.getPresident().getId();

        var candidates = players.stream().filter(e -> !e.getId().equals(presidentId)).toList();
        var candidatesMap = new HashMap<UUID, String>();

        for (var candidate: candidates){
            if (presidentId.equals(candidate.getId()) || candidate.isDead())
                continue;

            candidatesMap.put(candidate.getId(), "Name: " + candidate.getName() + " IsDead: " + candidate.isDead());
        }

        System.out.println();

        var chosenCandidate = electionManager.getChosenVariant(presidentId, candidatesMap);

        table.getGame().killPlayer(chosenCandidate);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        if (table.getGame().isGameOver())
            return false;

        this.table = table;
        var fascistActiveArticles = table.getFascistActiveArticles().size();

        if (fascistActiveArticles == 4 && !isFirstExecutionDone)
            return true;

        return fascistActiveArticles == 5 && !isSecondExecutionDone;
    }
}
