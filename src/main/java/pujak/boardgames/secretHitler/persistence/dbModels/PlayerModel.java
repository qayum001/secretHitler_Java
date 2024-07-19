package pujak.boardgames.secretHitler.persistence.dbModels;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "player")
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private long id;

    @Column(name = "telegramID", nullable = false, unique = true)
    private long telegramId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating", nullable = false)
    private int rating;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerGameModel> playedGames;

    public PlayerModel(long telegramId, String name){
        this.telegramId = telegramId;
        this.name = name;
    }

    public PlayerModel() {}

    public long getId(){
        return this.id;
    }

    public long getTelegramId(){
        return telegramId;
    }

    public String getName(){
        return name;
    }

    public int getRating(){
        return rating;
    }

    public List<PlayerGameModel> getPlayedGames(){
        return playedGames;
    }

    public void addRating(int value){
        rating += value;
    }
}
