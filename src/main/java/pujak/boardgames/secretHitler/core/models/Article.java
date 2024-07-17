package pujak.boardgames.secretHitler.core.models;

import pujak.boardgames.secretHitler.core.models.enums.ArticleType;

import java.util.UUID;

public class Article {
    private final ArticleType type;
    private final UUID id;
    private int placedStage;

    public UUID getId() {
        return id;
    }

    public ArticleType getType() {
        return type;
    }

    public void setPlacedStage(int placedStage) {
        this.placedStage = placedStage;
    }

    public int getPlacedStage(){
        return this.placedStage;
    }

    public Article(ArticleType type) {
        id = UUID.randomUUID();
        this.type = type;
    }
}