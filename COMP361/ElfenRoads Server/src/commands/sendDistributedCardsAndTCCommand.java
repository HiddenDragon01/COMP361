package commands;

import gamelogic.Counter;

import java.util.ArrayList;

import gamelogic.Card;

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
		
		
	}

}
