package pujak.boardgames.secretHitler.telegramBot.implementations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.events.LoyaltyInvestigationEvent;
import pujak.boardgames.secretHitler.core.models.GameRules;
import pujak.boardgames.secretHitler.core.models.Room;
import pujak.boardgames.secretHitler.core.services.ArticlesProvider;
import pujak.boardgames.secretHitler.core.services.ElectionManager;
import pujak.boardgames.secretHitler.core.services.MessageSender;
import pujak.boardgames.secretHitler.telegramBot.interfaces.services.RoomFactory;

@Component
public class RoomFactoryImpl implements RoomFactory {
    private static long nextId;

    private long nextId(){
        return nextId++;
    }
    @Autowired
    private ArticlesProvider articlesProvider;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private ElectionManager electionManager;


    @Override
    public Room createRoom() {
        return new Room(createGameRules(), articlesProvider, messageSender, electionManager, createEventFactory(), nextId());
    }

    private GameRules createGameRules(){
        return new GameRules(
                2,
                10,
                11,
                6,
                5,
                6,
                5,
                3);
    }

    private EventFactory createEventFactory(){
        var factory = new EventFactory();

        factory.register(new LoyaltyInvestigationEvent(electionManager, messageSender));

        return factory;
    }
}
