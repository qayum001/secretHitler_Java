package pujak.boardgames.secretHitler.persistence.dbModels;

import jakarta.persistence.*;
import pujak.boardgames.secretHitler.core.models.enums.Party;
import pujak.boardgames.secretHitler.core.models.enums.ResponsibilityType;

@Entity
public class PlayerGameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerModel player;

    @ManyToOne
    @JoinColumn(name = "game_result_id")
    private GameResultModel gameResult;

    @Enumerated(EnumType.STRING)
    private ResponsibilityType playerResponsibility;

    @Enumerated(EnumType.STRING)
    private Party party;

    public long getId(){ return id; }

    public PlayerModel getPlayer(){ return player; }

    public ResponsibilityType getPlayerResponsibility() { return playerResponsibility; }

    public Party getPlayerParty() { return party; }

    public GameResultModel getGameResult() { return gameResult; }

    public PlayerGameModel(PlayerModel player, GameResultModel gameResult, ResponsibilityType responsibilityType, Party party){
        this.player = player;
        this.gameResult = gameResult;
        this.playerResponsibility = responsibilityType;
        this.party = party;
    }

    public PlayerGameModel(){}
}
