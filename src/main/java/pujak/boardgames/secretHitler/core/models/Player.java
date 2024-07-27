package pujak.boardgames.secretHitler.core.models;

public class Player {
    private final long telegramId;
    public long getTelegramId(){ return this.telegramId; }

    private Role role;
    public Role getRole() { return role; }
    public void serRole(Role role) { this.role = role; }

    private final String name;
    public String getName() { return name; }

    private boolean isDead;
    public boolean isDead() { return isDead; }

    private boolean isInRoom;
    public boolean isInRoom() { return isInRoom; }
    public void setInRoom(boolean status) { this.isInRoom = status; }

    public void setDead(boolean isDead) { this.isDead = isDead; }

    public Player(long telegramId, String name){
        this.name = name;
        this.telegramId = telegramId;
    }

}
