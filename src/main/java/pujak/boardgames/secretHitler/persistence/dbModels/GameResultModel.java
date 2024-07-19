package pujak.boardgames.secretHitler.persistence.dbModels;

import jakarta.persistence.*;
import pujak.boardgames.secretHitler.core.models.GameResult;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.enums.Party;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gameResult")
public class GameResultModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_result_id")
    private long id;

    @OneToMany(mappedBy = "gameResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerGameModel> allPlayers;

    @Enumerated(EnumType.STRING)
    private Party winnerParty;

    @Temporal(TemporalType.DATE)
    private Date playedDate;

    public long getId() { return id; }

    public List<PlayerGameModel> getAllPlayers() { return allPlayers; }

    public Party getWinnerParty() { return winnerParty; }

    public Date getPlayedDate() { return playedDate; }

    public void setAllPlayers(List<PlayerGameModel> players){
        this.allPlayers = players;
    }

    public void setWinnerParty(Party winnerParty){
        this.winnerParty = winnerParty;
    }

    public void setTime(){
        playedDate = Date.from(Instant.now());
    }

    public GameResultModel() {
    }
}
