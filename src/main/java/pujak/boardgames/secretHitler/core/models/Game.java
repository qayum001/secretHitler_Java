package pujak.boardgames.secretHitler.core.models;

import java.util.*;
import java.util.stream.Collectors;

import pujak.boardgames.secretHitler.core.Interfaces.Delegate;
import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.enums.Party;
import pujak.boardgames.secretHitler.core.models.enums.ResponsibilityType;
import pujak.boardgames.secretHitler.core.models.stage.ArticleStage;
import pujak.boardgames.secretHitler.core.models.stage.ElectionStage;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.events.GameEvent;
import pujak.boardgames.secretHitler.core.services.MessageSender;

public class Game implements Delegate {
    // <editor-fold desc="Fields">
    private final ElectionManager electionManager;
    private final ArticlesProvider articlesProvider;
    private final MessageSender messageSender;
    private final Table table;
    private final ArrayList<Player> players;
    private boolean isGameOver;
    private GameType gameType;
    private int stageCounter;

    private Party winnerParty;
    // </editor-fold>

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Table getTable(){
        return this.table;
    }

    public Game(ArrayList<Player> players,
            ElectionManager electionManager,
            MessageSender messageSender,
            ArticlesProvider articlesProvider,
            EventFactory eventFactory) {

        this.isGameOver = false;
        this.electionManager = electionManager;
        this.articlesProvider = articlesProvider;
        this.messageSender = messageSender;
        table = new Table(this, eventFactory);
        this.players = players;
    }

    public void commandStart(){

    }

    public GameResult Start(GameRules gameRules) {
        if (!isPlayersCountCorrect(players, gameRules))
            throw new RuntimeException(
                    String.format("Players count not correct, you need minimum %2d players, and less then %2d",
                            gameRules.minPlayersToStart(), gameRules.maxPlayersToStart()));

        setGameType(players.size());
        table.setGameRules(gameRules);
        table.fillDrawPile();

        var gameStages = List.of(
            new ElectionStage(this, electionManager),
            new ArticleStage(this, articlesProvider)
        );

        stageCounter = 0;
        while (!isGameOver){
            for (var stage: gameStages){
                stage.runStage();
                runEvents();
                isGameOver = isGameOver();
                if (isGameOver) { break; }
            }
            messageSender.sendMessageToMany(players, table.getTableInfo());
            incrementStageCounter();
        }

        return getGameResult();
    }

    public void incrementStageCounter(){
        this.stageCounter++;
    }

    private GameResult getGameResult(){
        var winners = players.stream().filter(e -> e.getRole().getParty() == winnerParty).toList();

        return new GameResult(winners, players, winnerParty, UUID.randomUUID());
    }

    // <editor-fold desc="Public methods">

    public int getStageCounter(){
        return this.stageCounter;
    }

    public void runEvents(){
        var availableEvents = table.getExecutableEvents();
        for (GameEvent gameEvent : availableEvents)
            gameEvent.Execute(this);
    }

    public boolean isPresidentAgreed(){
        var yesId = (long)1;
        var noId = (long)0;

        var agreements = new HashMap<Long, String>();
        agreements.put(yesId, "Yes");
        agreements.put(noId, "No");

        var agreement = electionManager.getChosenVariant(table.getPresident().getTelegramId(), agreements);

        return Objects.equals(agreement, yesId);
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

        return yes > no;
    }

    public List<Player> getActivePlayers(ArrayList<Player> players) {
        return players.stream().filter(user -> !user.isDead())
                .collect(Collectors.toList());
    }

    public ArrayList<Player> generateCandidatePull(Player president, Player previousChancellor, List<Player> players) {
        var resArr = new ArrayList<Player>();

        for (var item : players) {
            if (item.isDead() || item.getTelegramId() == president.getTelegramId())
                continue;
            resArr.add(item);
        }

        resArr.remove(previousChancellor);

        return resArr;
    }

    public Map<Long, String> getElectionPull(ArrayList<Player> candidates) {
        var res = new HashMap<Long, String>();

        for (Player player : candidates) {
            res.put(player.getTelegramId(), player.getName() + " isDead: " + player.isDead());
        }

        return res;
    }

    public void killPlayer(long playerId){
        var player = players.stream().filter(e -> e.getTelegramId() == playerId).findFirst();
        player.ifPresent(value -> value.setDead(true));
    }

    @Override
    public void Execute(GameResult gameResult) {
        this.isGameOver = true;
    }

    public void checkElectionTracker(){
        var tracker = table.getElectionTracker();

        if (tracker == table.getGameRules().electionTrackerMax()){
            var article = table.getTopArticlesByCount(1).getFirst();

            messageSender.sendMessageToMany(players, "Article to enact: " + article.getType());
            table.addArticleToActives(article);
            table.setPreviousChancellor(null);
        }
    }

    public void discardArticle(UUID discardingArticleId, ArrayList<Article> articles){
        var discartingArticle = articles.stream().filter(e -> e.getId().equals(discardingArticleId))
                .findFirst().orElseThrow();
        table.discardArticle(discartingArticle);
        articles.remove(discartingArticle);
    }

    public boolean isGameOver(){
        if (table.getFascistActiveArticles().size() >= 3
                && table.getChancellor().getRole().getResponsibilityType() == ResponsibilityType.SecretHitler) {
            if (table.getFascistActiveArticles().get(2).getPlacedStage() != stageCounter){
                this.winnerParty = Party.Fascist;
                return true;
            }
        }
        var gameRules = table.getGameRules();

        if (table.getFascistActiveArticles().size() == gameRules.fascistWinArticlesCount()) {
            this.winnerParty = Party.Fascist;
            return true;
        }

        if (table.getLiberalActiveArticles().size() == gameRules.liberalWinArticlesCount()) {
            this.winnerParty = Party.Liberal;
            return true;
        }

        var players = table.getGame().getPlayers();
        if (players.stream().anyMatch(e -> e.getRole().getResponsibilityType()
                == ResponsibilityType.SecretHitler && e.isDead())){
            winnerParty = Party.Liberal;
            return true;
        }

        return false;
    }
    // </editor-fold>

    private void setGameType(int playersCount){
        gameType = GameType.Small;

        if (playersCount == 7 || playersCount == 8)
            gameType = GameType.Usual;
        if (playersCount == 9 || playersCount == 10)
            gameType = GameType.Big;
    }

    private static boolean isPlayersCountCorrect(ArrayList<Player> players, GameRules gameRules) {
        if (players == null)
            return false;

        var count = players.size();
        return count >= gameRules.minPlayersToStart() || count <= gameRules.maxPlayersToStart();
    }

    public GameType getGameType(){
        return gameType;
    }
}