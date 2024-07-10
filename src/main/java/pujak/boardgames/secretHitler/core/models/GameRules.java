package pujak.boardgames.secretHitler.core.models;

public record GameRules(int minPlayersToStart,
        int maxPlayersToStart,
        int fascistArticlesCount,
        int liberalsArticlesCount,
        int fascistsCount,
        int liberalsCount,
        int liberalWinArticlesCount,
        int fascistWinArticlesCount) {}

