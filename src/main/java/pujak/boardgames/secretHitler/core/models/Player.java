package pujak.boardgames.secretHitler.core.models;

import com.google.common.primitives.UnsignedLong;

public class Player {
    private UnsignedLong id;
    public UnsignedLong getId() {
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
}
