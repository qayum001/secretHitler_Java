package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.Interfaces.Delegatable;
import pujak.boardgames.secretHitler.core.Services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.Services.ElectionManager;
import pujak.boardgames.secretHitler.core.Services.MessageSender;
import pujak.boardgames.secretHitler.core.events.GameEvent;

public class Game implements Delegatable {
    private Table table;
    private ArrayList<Player> players;
    private MessageSender messageSender;
    private ElectionManager electionManager;
    private ArticlesProvider articlesProvider;

    public Game(ArrayList<Player> players,
            MessageSender messageSender,
            ElectionManager electionManager,
            ArticlesProvider articlesProvider) {

        this.messageSender = messageSender;
        this.electionManager = electionManager;
        this.articlesProvider = articlesProvider;
        table = new Table();
        this.players = players;
    }
    
    public void Start(GameRules gameRules) {

        if (!isPlayersCountCorrect(players, gameRules))
            throw new RuntimeException(
                    String.format("Players count not correct, you need minum %2d players, and less then %2d",
                            gameRules.minPlayersToStart(), gameRules.maxPlayersToStart()));

        var isGameOver = false;

        while (!isGameOver) {
            //todo: 
            //Add here gameover check

            //stage start
            table.setPresident(players);

            var ids = ((ArrayList<UnsignedLong>) players.stream().map(Player::getId).collect(Collectors.toList()));
            messageSender.sendMessageToMany(ids, table.getTableInfo());

            //send to president chancellor candidates
            var candidates = generateCandidatePull(table.getPresident(), table.getPreviousChancellor(), players);
            var electionData = getElectionPull(candidates);

            var candidate = electionManager.getChosenCandidate(table.getPresident().getId(), electionData);

            //start Chancellor election
            var variants = new ArrayList<String>(Arrays.asList("Ja", "Nien"));
            var votingResults = electionManager.getVotes(getActivePlayers(players), variants, "Vote for Chancellor");//add here candidate name
            //manage votes
            //todo: extract method
            var yes = 0;
            var no = 0;

            for (String item : votingResults) {
                if (item == "Ja")
                    yes++;
                if (item == "Nien")
                    no++;
            }

            if (yes <= no) {
                table.setElectionTracker(table.getElectionTracker() + 1 );
                continue;
            }

            table.setChancellor(
                    getActivePlayers(players).stream().filter(e -> e.getId() == candidate).findFirst().get());

            // send table info and 3 articles to president
            messageSender.sendMessageToMany(ids, table.getTableInfo());

            var sendingArticles = table.getTopTreeArticles();
            var presidentDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", table.getPresident().getId());

            var discartingArticle = sendingArticles.stream().filter(e -> e.getId() == presidentDiscardArticle)
                    .findFirst().get();
            table.discardArticle(discartingArticle);

            //send here message to President that his next message will be send to other players and he must write what cards he got

            sendingArticles.remove(discartingArticle);

            var chancellorDiscardArticle = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", table.getPresident().getId());

            discartingArticle = sendingArticles.stream().filter(e -> e.getId() == chancellorDiscardArticle)
                    .findFirst().get();
            table.discardArticle(discartingArticle);

            //add left article to actives
            //add veto logic
            table.addArticleToActives(sendingArticles.getFirst());

            //send table info
            messageSender.sendMessageToMany(ids, table.getTableInfo());

            var availableEvents = table.getExecutableEvents();

            for (GameEvent gameEvent : availableEvents) {
                gameEvent.Execute(this);
            }
        }
    }

    

    private static ArrayList<Player> getActivePlayers(ArrayList<Player> players) {
        return ((ArrayList<Player>) players.stream().filter(user -> !user.isDead())
        .collect(Collectors.toList()));
    }

    private static boolean isPlayersCountCorrect(ArrayList<Player> players, GameRules gameRules) {
        if (players == null)
            return false;

        var count = players.size();
        if (count >= gameRules.minPlayersToStart() || count <= gameRules.maxPlayersToStart())
            return true;

        return false;
    }
    
    private static ArrayList<Player> generateCandidatePull(Player president, Player previousChancellor,
            ArrayList<Player> players) {
        var resArr = new ArrayList<Player>();

        for (var item : players) {
            if (item.isDead() || item.getId() == president.getId() || item.getId() == previousChancellor.getId())
                continue;
            resArr.add(item);
        }

        return resArr;
    }
    
    private static Map<UnsignedLong, String> getElectionPull(ArrayList<Player> candidates) {
        var res = new HashMap<UnsignedLong, String>();

        for (Player player : candidates) {
            res.put(player.getId(), player.getName());
        }

        return res;
    }

    private void endGame() {
        
    }

    @Override
    public void Execute(GameResult e) {
        endGame();
    }
}