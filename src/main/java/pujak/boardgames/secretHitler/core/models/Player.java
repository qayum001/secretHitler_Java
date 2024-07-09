package pujak.boardgames.secretHitler.core.models;

import com.google.common.primitives.UnsignedInteger;

public class Player {
    private static UnsignedInteger idCounter; 

    public static UnsignedInteger getIdCounter() {
        return idCounter;
    }

    private UnsignedInteger id;
    public UnsignedInteger getId() {
        return id;
    }
    private Role role;
    public Role getRole() {
        return role;
    }
    private String name;
    public String getName() {
        return name;
    }
    private boolean isDead;
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
    
    public Player(Role role) {
        this.role = role;
        this.id = idCounter.plus(UnsignedInteger.ONE);
    }
}
