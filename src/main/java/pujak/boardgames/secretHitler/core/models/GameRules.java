package pujak.boardgames.secretHitler.core.models;

public record GameRules(int minPlayersToStart,
        int maxPlayersToStart,
        int fascistArticlesCount,
        int libersArticlesCount,
        int fascistsCount,
        int liberalsCount,
        int liberalWinArticlesCount,
        int fascistWinArticlesCount) {}

