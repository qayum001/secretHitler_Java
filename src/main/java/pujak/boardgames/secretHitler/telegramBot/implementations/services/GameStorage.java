package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Room;

import java.util.concurrent.ConcurrentHashMap;

public class GameStorage {
    private final ConcurrentHashMap<Long, Room> rooms;
    private final ConcurrentHashMap<Long, Player> inRoomPlayers;

    {
        rooms = new ConcurrentHashMap<>();
        inRoomPlayers = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<Long, Room> getRooms() { return rooms; }
    public void AddRoom(Long key, Room value){
        rooms.put(key, value);
    }

    public boolean isRoomExists(Long key){
        return rooms.containsKey(key);
    }

    public Room getRoom(Long key){
        return rooms.get(key);
    }

    public Room getRoomByUserId(Long id){
        for (var room: rooms.values()){
            if (room.isPlayerInThisRoom(id)){
                return room;
            }
        }
        return null;
    }

    public Room getRoomByUser(Player player){
        for (var room: rooms.values()){
            if (room.isPlayerInThisRoom(player)){
                return room;
            }
        }
        return null;
    }

    public void removeRoom(Long key){
        rooms.remove(key);
    }

    public void addPlayerToRoom(Long roomId, Player player){
        if (rooms.containsKey(roomId)){
            rooms.get(roomId).addPlayer(player);
        }
    }

    public boolean isUserHasRoom(Long userID){
        return rooms.values().stream().anyMatch(e -> e.getHostId() == userID);
    }

    public void addPlayerToRoom(Room room, Player player){
        room.addPlayer(player);
        inRoomPlayers.put(player.getTelegramId(), player);
    }

    public boolean isPlayerInRoom(Long id){
        return inRoomPlayers.containsKey(id);
    }
}
