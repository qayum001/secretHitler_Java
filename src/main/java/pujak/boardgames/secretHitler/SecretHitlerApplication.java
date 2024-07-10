package pujak.boardgames.secretHitler;

import org.glassfish.jersey.process.internal.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pujak.boardgames.secretHitler.console.ConsoleArticleProvider;
import pujak.boardgames.secretHitler.console.ConsoleElectionManager;
import pujak.boardgames.secretHitler.console.ConsoleMessageSender;
import pujak.boardgames.secretHitler.core.events.EventFactory;
import pujak.boardgames.secretHitler.core.events.StageEndEvent;
import pujak.boardgames.secretHitler.core.models.GameRules;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Role;
import pujak.boardgames.secretHitler.core.models.Room;
import pujak.boardgames.secretHitler.core.models.Enums.Party;
import pujak.boardgames.secretHitler.core.models.Enums.ResponsibilityType;

import java.util.ArrayList;

//@SpringBootApplication
public class SecretHitlerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SecretHitlerApplication.class, args);

		System.out.println("Start here");

		var gameRules = new GameRules(3,
				4,
				5,
				5,
				1,
				1,
				2,
				2);

		var articleProvider = new ConsoleArticleProvider();
		var electionManager = new ConsoleElectionManager();
		var messageSender = new ConsoleMessageSender();
		var eventFactory = new EventFactory();

		eventFactory.Register(new StageEndEvent(messageSender));

		var liberalRole = new Role(ResponsibilityType.Liberal, Party.Liberal);
		var fascistRole = new Role(ResponsibilityType.Fascist, Party.Fascist);
		var hitlerRole = new Role(ResponsibilityType.SecretHitler, Party.Fascist);


		var playerLiberal = new Player(liberalRole, "Liberal_name");
		var playerFascist = new Player(fascistRole, "Fascist_name");
		var playerHitler = new Player(hitlerRole, "Hitler_name");

		var room = new Room(gameRules, articleProvider, electionManager, messageSender, eventFactory);

		room.addPlayer(playerHitler);
		room.addPlayer(playerFascist);
		room.addPlayer(playerLiberal);

		room.start();
	}
}
