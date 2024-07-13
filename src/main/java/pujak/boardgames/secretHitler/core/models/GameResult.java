package pujak.boardgames.secretHitler.core.models;

import java.util.ArrayList;
import java.util.UUID;

import pujak.boardgames.secretHitler.core.models.enums.Party;

public record GameResult(ArrayList<Player> getWinners, ArrayList<Player> getAllPlayers, Party getWinnerParty, UUID gameId) {} 