package pujak.boardgames.secretHitler.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import jakarta.persistence.Convert;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.models.Article;

public class ConsoleArticleProvider implements ArticlesProvider {


    @Override
    public UUID getDiscardArticle(List<Article> articles, String message, long receiverId) {
        System.out.println("Message: " + message + " To: " + receiverId);

        for (var article: articles){
            System.out.println("Id: " + article.getId() + " Type: " + article.getType());
        }

        var in = new Scanner(System.in);

        return UUID.fromString(in.nextLine());
    }

    @Override
    public UUID getDiscardArticleWithAvailableVetoPower(List<Article> articles, String message, long receiverId, UUID vetoVariant) {
        System.out.println("Message: " + message + " To: " + receiverId);

        for (var article: articles){
            System.out.println("Id: " + article.getId() + " Type: " + article.getType());
        }
        System.out.println("Id: " + vetoVariant + " Suggest Veto");

        var in = new Scanner(System.in);
        return UUID.fromString(in.nextLine());
    }
}
