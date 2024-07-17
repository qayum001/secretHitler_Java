package pujak.boardgames.secretHitler.core.models;

public record GameRules(int minPlayersToStart,
        int maxPlayersToStart,
        int fascistArticlesCount,
        int liberalsArticlesCount,
        int liberalWinArticlesCount,
        int fascistWinArticlesCount,
        int vetoPowerActivatingCount,
        int electionTrackerMax) {}

