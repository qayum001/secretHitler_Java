package pujak.boardgames.secretHitler.core.models;

import pujak.boardgames.secretHitler.core.models.enums.ArticleType;

import java.util.UUID;

public class Article {
    private final ArticleType type;
    private final UUID id;

    public UUID getId() {
        return id;
    }

    public ArticleType getType() {
        return type;
    }

    public Article(ArticleType type) {
        id = UUID.randomUUID();
        this.type = type;
    }
}