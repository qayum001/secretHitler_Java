package pujak.boardgames.secretHitler.core.Services;

import java.util.ArrayList;

import com.google.common.primitives.UnsignedLong;

import pujak.boardgames.secretHitler.core.models.Article;

public interface ArticlesProvider {
    int getDiscardArticle(ArrayList<Article> aritcles, String message, UnsignedLong recieverId);
}
