package pujak.boardgames.secretHitler;

import pujak.boardgames.secretHitler.console.ConsoleArticleProvider;
import pujak.boardgames.secretHitler.console.ConsoleElectionManager;
import pujak.boardgames.secretHitler.console.ConsoleMessageSender;
import pujak.boardgames.secretHitler.core.events.*;
import pujak.boardgames.secretHitler.core.models.GameRules;
import pujak.boardgames.secretHitler.core.models.Player;
import pujak.boardgames.secretHitler.core.models.Role;
import pujak.boardgames.secretHitler.core.models.Room;
import pujak.boardgames.secretHitler.core.models.enums.Party;
import pujak.boardgames.secretHitler.core.models.enums.ResponsibilityType;

//@SpringBootApplication
public class SecretHitlerApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SecretHitlerApplication.class, args);

		System.out.println("Start here");

		var gameRules = new GameRules(5,
				10,
				11,
				6,
				2,
				3,
				5,
				6,
				1);

		var articleProvider = new ConsoleArticleProvider();
		var electionManager = new ConsoleElectionManager();
		var messageSender = new ConsoleMessageSender();
		var eventFactory = new EventFactory();

		eventFactory.register(new StageEndEvent(messageSender));
		//eventFactory.register(new ExecutionEvent(electionManager));
		//eventFactory.register(new ArticlePeekEvent(messageSender));
		//eventFactory.register(new LoyaltyInvestigationEvent(electionManager, messageSender));
		//eventFactory.register(new SpecialElectionEvent(electionManager, articleProvider));
		//eventFactory.register(new GameOverEvent(messageSender));

		var liberalRole = new Role(ResponsibilityType.Liberal, Party.Liberal);
		var fascistRole = new Role(ResponsibilityType.Fascist, Party.Fascist);
		var hitlerRole = new Role(ResponsibilityType.SecretHitler, Party.Fascist);


		var player1 = new Player(liberalRole, "Liberal_1");
		var player2 = new Player(liberalRole, "Liberal_2");
		var player3 = new Player(liberalRole, "Liberal_3");
		var player4 = new Player(liberalRole, "Liberal_4");
		var player5 = new Player(fascistRole, "Fascist_1");
		var player6 = new Player(fascistRole, "Fascist_2");
		var player7 = new Player(hitlerRole, "Hitler_1");

		var room = new Room(gameRules, articleProvider, electionManager, eventFactory);

		room.addPlayer(player1);
		room.addPlayer(player2);
		room.addPlayer(player3);
		room.addPlayer(player4);
		room.addPlayer(player5);
		room.addPlayer(player6);
		room.addPlayer(player7);

		var gameResult = room.start();
		System.out.println(gameResult.getWinnerParty());
	}
}
