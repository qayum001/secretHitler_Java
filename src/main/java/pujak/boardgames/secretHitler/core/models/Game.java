package pujak.boardgames.secretHitler.core.models;

import java.util.*;
import java.util.stream.Collectors;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.events.GameEvent;

public class Game implements Delegatable {
    private final Table table;
    private final ArrayList<Player> players;
    private GameType gameType;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private final ElectionManager electionManager;
    private final ArticlesProvider articlesProvider;
    private boolean isGameOver;
    private GameResult gameResult;

    public Game(ArrayList<Player> players,
            MessageSender messageSender,
            ElectionManager electionManager,
            ArticlesProvider articlesProvider,
            EventFactory eventFactory) {

        this.isGameOver = false;
        this.electionManager = electionManager;
        this.articlesProvider = articlesProvider;
        table = new Table(this, eventFactory);
        this.players = players;
    }
    
    public void Start(GameRules gameRules) {
        if (!isPlayersCountCorrect(players, gameRules))
            throw new RuntimeException(
                    String.format("Players count not correct, you need minimum %2d players, and less then %2d",
                            gameRules.minPlayersToStart(), gameRules.maxPlayersToStart()));

        setGameType(players.size());
        table.fillDrawPile(gameRules);

        while (!isGameOver) {
            //stage start

            var availableEvents = table.getExecutableEvents();
            for (GameEvent gameEvent : availableEvents)
                gameEvent.Execute(this);

            table.setPresident(players);

            //send to president chancellor candidates
            var candidates = generateCandidatePull(table.getPresident(), table.getPreviousChancellor(), getActivePlayers(players));
            var electionData = getElectionPull(candidates);

            var candidateId = electionManager.getChosenCandidate(table.getPresident().getId(), electionData);

            //start Chancellor election
            var variants = new ArrayList<>(Arrays.asList("Ja", "Nien"));
            var votingResults = electionManager.getVotes(getActivePlayers(players), variants, "Vote for Chancellor");//add here candidate name

            if (isElectionSucceed(votingResults)) {
                table.setElectionTracker(table.getElectionTracker() + 1);
                continue;
            }

            var getChancellor = players.stream().filter(e -> e.getId().equals(candidateId)).findFirst().get();
            table.setChancellor(getChancellor);

            var sendingArticles = table.getTopTreeArticles();
            var presidentDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", table.getPresident().getId());

            var discartingArticle = sendingArticles.stream().filter(e -> e.getId().equals(presidentDiscardArticle))
                    .findFirst().get();
            table.discardArticle(discartingArticle);

            //send here message to President that his next message will be sent to other players, and he must write what cards he got

            sendingArticles.remove(discartingArticle);

            var chancellorDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", table.getChancellor().getId());

            discartingArticle = sendingArticles.stream().filter(e -> e.getId().equals(chancellorDiscardArticle))
                    .findFirst().get();
            table.discardArticle(discartingArticle);

            //add left article to actives
            //add veto logic
            table.addArticleToActives(sendingArticles.getFirst());

            table.setPreviousChancellor(table.getChancellor());
        }
    }

    private void setGameType(int playersCount){
        gameType = GameType.Small;

        if (playersCount == 7 || playersCount == 8)
            gameType = GameType.Usual;
        if (playersCount == 9 || playersCount == 10)
            gameType = GameType.Big;
    }

    public boolean isElectionSucceed(ArrayList<String> votingResults) {
        var yes = 0;
        var no = 0;

        for (String item : votingResults) {
            if (Objects.equals(item, "Ja"))
                yes++;
            if (Objects.equals(item, "Nien"))
                no++;
        }

        return yes <= no;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public List<Player> getActivePlayers(ArrayList<Player> players) {
        return players.stream().filter(user -> !user.isDead())
        .collect(Collectors.toList());
    }

    private static boolean isPlayersCountCorrect(ArrayList<Player> players, GameRules gameRules) {
        if (players == null)
            return false;

        var count = players.size();
        return count >= gameRules.minPlayersToStart() || count <= gameRules.maxPlayersToStart();
    }
    
    public ArrayList<Player> generateCandidatePull(Player president, Player previousChancellor,
            List<Player> players) {

        var resArr = new ArrayList<Player>();

        for (var item : players) {
            if (item.isDead() || item.getId().equals(president.getId()))
                continue;
            resArr.add(item);
        }

        resArr.remove(previousChancellor);

        return resArr;
    }
    
    public Map<UUID, String> getElectionPull(ArrayList<Player> candidates) {
        var res = new HashMap<UUID, String>();

        for (Player player : candidates) {
            res.put(player.getId(), player.getName() + " isDead: " + player.isDead());
        }

        return res;
    }

    public void killPlayer(UUID playerId){
        var player = players.stream().filter(e -> e.getId().equals(playerId)).findFirst().get();

        player.setDead(true);
    }

    private void endGame() {
        this.isGameOver = true;
    }

    @Override
    public void Execute(GameResult gameResult) {
        this.gameResult = gameResult;
        endGame();
    }

    public GameType getGameType(){
        return gameType;
    }
}