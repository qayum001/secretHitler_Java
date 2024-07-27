package pujak.boardgames.secretHitler.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.primitives.UnsignedInteger;

import pujak.boardgames.secretHitler.core.models.Article;

public interface ArticlesProvider {
    UUID getDiscardArticle(List<Article> articles, String message, long receiverId);
    UUID getDiscardArticleWithAvailableVetoPower(List<Article> articles, String message, long receiverId, UUID vetoVariant);
}
