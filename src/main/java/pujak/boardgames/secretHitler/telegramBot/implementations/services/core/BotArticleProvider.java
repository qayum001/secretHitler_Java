package pujak.boardgames.secretHitler.telegramBot.implementations.services.core;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import pujak.boardgames.secretHitler.core.models.Article;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.telegramBot.SecretHitlerBot;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class BotArticleProvider implements ArticlesProvider {

    @Autowired
    private SecretHitlerBot secretHitlerBot;

    @Override
    public UUID getDiscardArticle(List<Article> articles, String message, long receiverId) {
        var rand = new Random();

        return articles.get(rand.nextInt(articles.size())).getId();
    }

    @Override
    public UUID getDiscardArticleWithAvailableVetoPower(List<Article> articles, String message, long receiverId, UUID vetoVariant) {
        var rand = new Random();

        return articles.get(rand.nextInt(articles.size())).getId();
    }
}
