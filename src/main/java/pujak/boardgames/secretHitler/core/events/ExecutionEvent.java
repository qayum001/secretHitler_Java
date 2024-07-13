package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.GameResult;
import pujak.boardgames.secretHitler.core.models.Table;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExecutionEvent implements GameEvent{
    private final ElectionManager electionManager;
    private Table table;

    private boolean isFirstExecutionDone;
    private boolean isSecondExecutionDone;

    public ExecutionEvent(ElectionManager electionManager){
        this.electionManager = electionManager;
    }

    @Override
    public void Execute(Delegatable delegatable) {
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

            candidatesMap.put(candidate.getId(), candidate.getName());
        }

        var chosenCandidate = electionManager.getChosenCandidate(presidentId, candidatesMap);

        table.getGame().killPlayer(chosenCandidate);
    }

    @Override
    public boolean isConditionsMatched(Table table) {
        this.table = table;
        var fascistActiveArticles = table.getFascistActiveArticles().size();

        if (fascistActiveArticles == 4 && !isFirstExecutionDone)
            return true;

        return fascistActiveArticles == 5 && !isSecondExecutionDone;

    }
}
