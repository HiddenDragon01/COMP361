package commands;

import gamelogic.Counter;
import gamelogic.ElfengoldGame;
import gamelogic.Game;

import java.util.ArrayList;

import gamelogic.Card;
import gamelogic.Client;

public class sendDistributedCardsAndTCCommand extends RemoteCommand{
	
	private String aPlayerID;
	private Counter aCounter;
	private ArrayList<Card> aCards;
	private ArrayList<Counter> aCounters;
	
	public sendDistributedCardsAndTCCommand(String pPlayerID, Counter pCounter, ArrayList<Card> pCards)
	{
		aPlayerID = pPlayerID;
		aCounter = pCounter;
		aCards = pCards;
		
	}
	
	public sendDistributedCardsAndTCCommand(String pPlayerID, ArrayList<Counter> pCounters, ArrayList<Card> pCards)
	{
		aPlayerID = pPlayerID;
		aCounters = pCounters;
		aCards = pCards;
		
	}

	@Override
	public void execute() {
		
		System.out.println("CLIENT: In the sendDistri... command now! ");
		
		Game game = Client.instance().getGame();
		
		if(game.getClass() == ElfengoldGame.class) 
		{
			game.setDistributedCardsAndTC(aPlayerID, aCounters, aCards);
		}
		else {
			game.setDistributedCardsAndTC(aPlayerID, aCounter, aCards);
		}
		
		
	}

}
