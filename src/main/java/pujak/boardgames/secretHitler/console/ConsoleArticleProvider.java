package pujak.boardgames.secretHitler.console;

import java.util.ArrayList;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.models.Article;

public class ConsoleArticleProvider implements ArticlesProvider {

    @Override
    public int getDiscardArticle(ArrayList<Article> aritcles, String message, UnsignedInteger recieverId) {
        return 0;
    }
}
