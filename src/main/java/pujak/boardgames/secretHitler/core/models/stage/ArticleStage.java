package pujak.boardgames.secretHitler.core.models.stage;

import pujak.boardgames.secretHitler.core.models.Game;
import pujak.boardgames.secretHitler.core.models.GameResult;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ArticleStage implements Stage{

    private final Game game;
    private final ArticlesProvider articlesProvider;

    private boolean isGameOver;
    private GameResult gameResult;

    public ArticleStage(Game game, ArticlesProvider articlesProvider){
        this.game = game;
        this.articlesProvider = articlesProvider;
    }

    @Override
    public void runStage() {
        var table = game.getTable();

        var sendingArticles = table.getTopArticlesByCount(3);
        var presidentDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                "Choose article to discard", table.getPresident().getId());

        game.discardArticle(presidentDiscardArticleId, sendingArticles);

        if (table.isVetoPowerAvailable()){
            var vetoPowerId = UUID.randomUUID();
            var chancellorDiscardArticle = articlesProvider.getDiscardArticleWithAvailableVetoPower(sendingArticles,
                    "Choose article to discard or veto to discard two articles", table.getChancellor().getId(), vetoPowerId);

            if (chancellorDiscardArticle.equals(vetoPowerId)){
                if (game.isPresidentAgreed()){
                    while (!sendingArticles.isEmpty())
                        game.discardArticle(sendingArticles.getFirst().getId(), sendingArticles);
                }else {
                    var chancellorNewDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                            "Choose article to discard or veto to discard two articles", table.getChancellor().getId());
                    game.discardArticle(chancellorNewDiscardArticleId, sendingArticles);
                }
            } else { game.discardArticle(chancellorDiscardArticle, sendingArticles); }
        } else {
            var chancellorDiscardArticleId = articlesProvider.getDiscardArticle(sendingArticles,
                    "Choose article to discard", table.getChancellor().getId());

            game.discardArticle(chancellorDiscardArticleId, sendingArticles);
        }

        if (!sendingArticles.isEmpty())
            table.addArticleToActives(sendingArticles.getFirst());
    }
}
