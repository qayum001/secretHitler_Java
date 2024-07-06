package pujak.boardgames.secretHitler.core.models;

import pujak.boardgames.secretHitler.core.models.Enums.ArticleType;

public class Article {
    private static int counter;
    private ArticleType type;
    private int id;

    public int getId() {
        return id;
    }

        public ArticleType getType() {
        return type;
    }

    public Article(String name, ArticleType type) {
        id = counter++;
        this.type = type;
    }
}