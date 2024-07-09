package pujak.boardgames.secretHitler.core.services;

import java.util.ArrayList;

import com.google.common.primitives.UnsignedInteger;

import pujak.boardgames.secretHitler.core.models.Article;

public interface ArticlesProvider {
    int getDiscardArticle(ArrayList<Article> aritcles, String message, UnsignedInteger recieverId);
}
