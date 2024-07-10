package pujak.boardgames.secretHitler.core.events;

import java.util.ArrayList;

public class EventFactory {

    private final ArrayList<GameEvent> registeredGameEvents;

    public EventFactory() {
        this.registeredGameEvents = new ArrayList<GameEvent>();
    }

    public ArrayList<GameEvent> getRegisteredGameEvents() {
        return registeredGameEvents;
    }

    public void Register(GameEvent event) {
        registeredGameEvents.add(event);
    }
}