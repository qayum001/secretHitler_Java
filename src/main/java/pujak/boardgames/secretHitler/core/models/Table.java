package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.events.GameEvent;
import pujak.boardgames.secretHitler.core.models.enums.ArticleType;

public class Table {
    private GameRules gameRules;

    public GameRules getGameRules() {
        return gameRules;
    }

    private ArrayList<Article> drawPile;
    public ArrayList<Article> getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(ArrayList<Article> drawPile) {
        this.drawPile = drawPile;
    }

    private ArrayList<Article> discardPile;
    public ArrayList<Article> getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(ArrayList<Article> discardPile) {
        this.discardPile = discardPile;
    }

    private final ArrayList<Article> fascistActiveArticles;
    public ArrayList<Article> getFascistActiveArticles() {
        return fascistActiveArticles;
    }

    private final ArrayList<Article> liberalActiveArticles;
    public ArrayList<Article> getLiberalActiveArticles() {
        return liberalActiveArticles;
    }

    private Player president;
    public Player getPresident() {
        return president;
    }


    private Player chancellor;
    public Player getChancellor() {
        return chancellor;
    }

    public void setChancellor(Player newChancellor) {
        this.previousChancellor = this.chancellor;
        this.chancellor = newChancellor;
    }

    private Player previousChancellor;
    public Player getPreviousChancellor() {
        return previousChancellor;
    }

    public void setPreviousChancellor(Player previousChancellor) {
        this.previousChancellor = previousChancellor;
    }

    private Player previousPresident;
    public Player getPreviousPresident() {
        return previousPresident;
    }

    public void setPreviousPresident(Player previousPresident) {
        this.previousPresident = previousPresident;
    }

    private final EventFactory eventFactory;

    public EventFactory getEventFactory() {
        return this.eventFactory;
    }

    private int electionTracker;

    public int getElectionTracker() {
        return electionTracker;
    }

    public void setElectionTracker(int electionTracker) {
        this.electionTracker = electionTracker;
    }

    private final Game game;

    public Game getGame() {
        return game;
    }

    public Table(Game game, EventFactory eventFactory) {
        this.game = game;
        this.fascistActiveArticles = new ArrayList<>();
        this.liberalActiveArticles = new ArrayList<>();
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.eventFactory = eventFactory;
    }
    
    public ArrayList<GameEvent> getExecutableEvents() {
        return (ArrayList<GameEvent>)eventFactory.getRegisteredGameEvents().stream().filter(e -> e.isConditionsMatched(this)).collect(Collectors.toList());
    }

    public void discardArticle(Article article) {
        this.discardPile.add(article);
    }

    public void setPresident(ArrayList<Player> players) {
        previousPresident = president;

        var previousPresidentIndex = previousPresident == null ? players.size() - 1 : players.indexOf(previousPresident);
        int newPresidentIndex = (previousPresidentIndex + 1) % players.size();

        president = players.get(newPresidentIndex);
    }
    
    public ArrayList<Article> getTopTreeArticles() {
        return getFirstArticles(3, drawPile);
    }

    private static ArrayList<Article> getFirstArticles(int count, ArrayList<Article> fromList){
        var example = (ArrayList<Article>)fromList.stream().takeWhile(e -> fromList.indexOf(e) < count).collect(Collectors.toList());
        fromList.removeAll(example);
        return example;
    }

    public void fillDrawPile(GameRules gameRules){
        var libArticles = new ArrayList<Article>();
        var fascistArticles = new ArrayList<Article>();

        for (var i = 0; i < gameRules.fascistArticlesCount(); i++)
            fascistArticles.add(new Article(ArticleType.Red));
        for (var i = 0; i < gameRules.liberalsArticlesCount(); i++)
            libArticles.add(new Article(ArticleType.Blue));

        drawPile = (ArrayList<Article>) shuffleTwoLists(libArticles, fascistArticles);
    }

    private static <T> List<T> shuffleTwoLists(List<T> firstList, List<T> secondList){
        var res = new ArrayList<T>();
        var random = new Random();

        int i = 0, j = 0;
        while (i < firstList.size() && j < secondList.size()){
            var chance = random.nextInt(firstList.size() + secondList.size() - i - j);
            if (chance < firstList.size() - i)
                res.add(firstList.get(i++));
            else
                res.add(secondList.get(j++));
        }

        while (i < firstList.size()){
            res.add(firstList.get(i++));
        }
        while (j < secondList.size()){
            res.add(secondList.get(j++));
        }

        return res;
    }

    public void addArticleToActives(Article article) {
        electionTracker = 0;
        switch (article.getType()) {
            case ArticleType.Blue:
                liberalActiveArticles.add(article);
                break;
            case ArticleType.Red:
                fascistActiveArticles.add(article);
                break;
            default:
                break;
        }
    }

    public String getTableInfo() {

        return String.format("""
                        ===================================================\s
                        Table:\s
                        Active Articles:\s
                            Fascist Articles: %d\s
                            Liberal Articles: %d\s
                        President: %s\s
                        Chancellor: %s\s
                        Previous Chancellor: %s\s
                        Previous President: %s\s
                        Draw pile: %d articles\s
                        Discard pile: %d articles\s
                        Election tracker: %d\s
                        ===================================================""",
                fascistActiveArticles.size(),
                liberalActiveArticles.size(),
                president != null ? president.getName() : "not set",
                chancellor != null ? chancellor.getName() : "not set",
                previousChancellor != null ? previousChancellor.getName() : "not set",
                previousPresident != null ? previousPresident.getName() : "not set",
                drawPile.size(),
                discardPile.size(),
                electionTracker);
    }
}
