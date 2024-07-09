package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.spel.MethodReferenceNode.AggregationMethodReference.ArgumentType;

import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.events.GameEvent;
import pujak.boardgames.secretHitler.core.models.Enums.ArticleType;

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

    private ArrayList<Article> fascistActiveArticles;
    public ArrayList<Article> getFascistActiveArticles() {
        return fascistActiveArticles;
    }

    private ArrayList<Article> liberalActiveArticels; 
    public ArrayList<Article> getLiberalActiveArticels() {
        return liberalActiveArticels;
    }

    private Player president;
    public Player getPresident() {
        return president;
    }

    public void setPresident(Player president) {
        this.president = president;
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

    private EventFactory eventFactory;

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

    private Game game;

    public Game getGame() {
        return game;
    }

    public Table(Game game) {
        this.game = game;
    }
    
    public ArrayList<GameEvent> getExecutableEvents() {

        return (ArrayList<GameEvent>)eventFactory.getRegistredGameEvents().stream().filter(e -> e.isConditionsMatched(this)).collect(Collectors.toList());
    }

    public void discardArticle(Article article) {
        this.discardPile.add(article);
    }

    public void setPresident(ArrayList<Player> players) {
        var newPresident = players.getFirst();

        while (!newPresident.isDead()) {
            players.remove(newPresident);
            players.addLast(newPresident);
            newPresident = players.getFirst();
        }

        previousPresident = president;
        president = newPresident;
    }
    
    public ArrayList<Article> getTopTreeArticles() {
        throw new RuntimeException();
    }

    public void addArticleToActives(Article article) {
        switch (article.getType()) {
            case ArticleType.Blue:
                liberalActiveArticels.add(article);
                break;
            case ArticleType.Red:
                fascistActiveArticles.add(article);
                break;
            default:
                break;
        }
    }

    public String getTableInfo() {
        var res = String.format("Table: \n" +
            "Active Articles: \n" + 
            "    Facsist Articles: %d \n" +
            "    Liberal Artecles: %d \n" +
            "President: %s \n" +
            "Chancellor %s \n" +
            "Previous Chancellor: %s \n" +
            "Draw pile: %d articles \n" +
            "Discard pile: %d aritcles \n" +
                "Election tracker: %d",
                fascistActiveArticles.size(),
                liberalActiveArticels.size(),
                president.getName(),
                chancellor.getName(),
                previousChancellor.getName(),
                drawPile.size(),
                discardPile.size(),
                electionTracker);
         
        return res;
    }

}
