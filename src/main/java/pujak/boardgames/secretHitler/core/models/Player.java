package pujak.boardgames.secretHitler.core.models;

import com.google.common.primitives.UnsignedInteger;

import java.util.UUID;

public class Player {
    private final UUID id;
    public UUID getId(){ return  id; }
    private final Role role;
    public Role getRole() {
        return role;
    }
    private final String name;
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
    
    public Player(Role role, String name) {
        this.role = role;
        this.name = name;
        this.id = UUID.randomUUID();
    }
}
