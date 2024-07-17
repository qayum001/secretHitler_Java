package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pujak.boardgames.secretHitler.core.models.enums.Party;

public record GameResult(List<Player> getWinners,
                         List<Player> getAllPlayers,
                         Party getWinnerParty, UUID gameId) {}