package pujak.boardgames.secretHitler.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.models.Article;

public class ConsoleArticleProvider implements ArticlesProvider {


    @Override
    public UUID getDiscardArticle(List<Article> articles, String message, UUID receiverId) {
        System.out.println("Message: " + message + " To: " + receiverId);

        for (var article: articles){
            System.out.println("Id: " + article.getId() + " Type: " + article.getType());
        }

        var in = new Scanner(System.in);
        var input = in.nextLine();

        return UUID.fromString(input);
    }
}
