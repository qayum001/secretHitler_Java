package pujak.boardgames.secretHitler.core.events;

import pujak.boardgames.secretHitler.core.events.enums.GameType;
import pujak.boardgames.secretHitler.core.models.Player;

import java.util.ArrayList;
import java.util.List;

public class EventFactory {

    private final ArrayList<GameEvent> registeredGameEvents;

    public EventFactory() {
        this.registeredGameEvents = new ArrayList<GameEvent>();
    }

    public ArrayList<GameEvent> getRegisteredGameEvents() {
        return registeredGameEvents;
    }

    public void register(GameEvent event) {
        registeredGameEvents.add(event);
    }
}