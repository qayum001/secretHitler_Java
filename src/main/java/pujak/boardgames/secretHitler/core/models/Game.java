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
            ElectionManager electionManager,
            ArticlesProvider articlesProvider,
            EventFactory eventFactory) {

        this.isGameOver = false;
        this.electionManager = electionManager;
        this.articlesProvider = articlesProvider;
        table = new Table(this, eventFactory);
        this.players = players;
    }
    
    public GameResult Start(GameRules gameRules) {
        if (!isPlayersCountCorrect(players, gameRules))
            throw new RuntimeException(
                    String.format("Players count not correct, you need minimum %2d players, and less then %2d",
                            gameRules.minPlayersToStart(), gameRules.maxPlayersToStart()));

        setGameType(players.size());
        table.setGameRules(gameRules);
        table.fillDrawPile();

        while (!isGameOver) {
            //stage start

            table.setPresident(players);

            //send to president chancellor candidates
            var candidates = generateCandidatePull(table.getPresident(), table.getPreviousChancellor(), getActivePlayers(players));
            var electionData = getElectionPull(candidates);

            var candidateId = electionManager.getChosenVariant(table.getPresident().getId(), electionData);

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
            var presidentDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", table.getPresident().getId());

            discardArticle(presidentDiscardArticleId, sendingArticles);

            if (table.isVetoPowerAvailable()){
                var vetoPowerId = UUID.randomUUID();
                var chancellorDiscardArticle = articlesProvider.getDiscardArticleWithAvailableVetoPower(sendingArticles,
                        "Choose article to discard or veto to discard two articles", table.getChancellor().getId(), vetoPowerId);

                if (chancellorDiscardArticle.equals(vetoPowerId)){
                    if (isPresidentAgreed()){
                        while (!sendingArticles.isEmpty())
                            discardArticle(sendingArticles.getFirst().getId(), sendingArticles);
                    }else {
                        var chancellorNewDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                                "Choose article to discard or veto to discard two articles", table.getChancellor().getId());
                        discardArticle(chancellorNewDiscardArticleId, sendingArticles);
                    }
                } else { discardArticle(chancellorDiscardArticle, sendingArticles); }
            } else {
                var chancellorDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                        "Choose article to discard", table.getChancellor().getId());

                discardArticle(chancellorDiscardArticleId, sendingArticles);
            }

            if (!sendingArticles.isEmpty())
                table.addArticleToActives(sendingArticles.getFirst());

            table.setPreviousChancellor(table.getChancellor());

            var availableEvents = table.getExecutableEvents();
            for (GameEvent gameEvent : availableEvents)
                gameEvent.Execute(this);
        }

        return this.gameResult;
    }

    private boolean isPresidentAgreed(){
        var yesId = UUID.randomUUID();
        var noId = UUID.randomUUID();

        var agreements = new HashMap<UUID, String>();
        agreements.put(yesId, "Yes");
        agreements.put(noId, "No");

        var agreement = electionManager.getChosenVariant(table.getPresident().getId(), agreements);

        return agreement.equals(yesId);
    }

    private void discardArticle(UUID discardingArticleId, ArrayList<Article> articles){
        var discartingArticle = articles.stream().filter(e -> e.getId().equals(discardingArticleId))
                .findFirst().get();
        table.discardArticle(discartingArticle);
        articles.remove(discartingArticle);
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

    @Override
    public void Execute(GameResult gameResult) {
        this.gameResult = gameResult;
        this.isGameOver = true;
    }

    public GameType getGameType(){
        return gameType;
    }
}