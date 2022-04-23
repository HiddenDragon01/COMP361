package gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class ElfengoldGame extends Game{
	private ArrayList<Counter> auctionPile = new ArrayList<>();
	private Counter currentAuctionCounter;
	protected ArrayList<String> currentBidders = new ArrayList<>();
	protected ArrayList<Integer> currentBids = new ArrayList<>();
	private Player currentHighestBidder;
	private int accumulatedGold = 0;
	private ArrayList<Card> faceUpCards = new ArrayList<>();
	private int goldCardPile = 0;
	protected transient Observer observer = Client.instance();

	public ElfengoldGame(GameVariant gv, String userName) {
		super(gv, userName);
		return;
	}
	
	@Override
	protected void setNumOfRounds() {
		this.numOfRounds = 6;
	}
	
	public int getAccumulatedGold()
	{
		return accumulatedGold;
	}
	
	@Override
	public void setupFirstRound() {
		
		//move onto next part of game
		currentRound = 1;
		
		
		//give each player two hidden counter, 5 cards, 7 gold coins
		for (String id : participants.keySet()) {

			Player p = participants.get(id);
			
			p.addGoldCoins(12);
		}
		
	}
	
	public void startChooseVisibleCounterPhase()
	{
		observer.restartChooseVisibleCounter();
	}
	
	public void setDistributedCardsAndTC(String playerID, ArrayList<Counter> counters, ArrayList<Card> cards)
	{
		System.out.println("In the ElfengoldGame.java setDistributedCards and TC function!");
		
		Player p = participants.get(playerID);
		
		for (Counter counter : counters) {
			if(counter != null) {
				p.addCounter(counter);
			}
			
		}
		
		for (Card card : cards) {
			p.receiveCard(card);
		}
		System.out.println("Done giving everyone their cards and tc");
		
		// this only happens in the first round:
		if(currentRound == 1) {
			//set player and round
			currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
			
			System.out.println("The current player is: " + currentPlayer.getName());
					
			currentPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
			
		}
	}
	
	public void setFaceUpCards(ArrayList<Card> pFaceUpCards) 
	{
		assert pFaceUpCards != null;
		System.out.println("In set Face up cards on the client Elfengoldgame.java");
		faceUpCards = pFaceUpCards;
	}
	
	public void setAuctionCounters(ArrayList<Counter> pAuctionCounters) 
	{
		System.out.println("In set auction pile on the client game.java");
		auctionPile = pAuctionCounters;
	}
	
	
	
	public ArrayList<Card> getFaceUpCards()
	{
		return new ArrayList<Card>(faceUpCards);
	}
	
	public void setGoldCardPile(int pGoldCardPile)
	{
		goldCardPile = pGoldCardPile;
	}
	
	public int getGoldCardPile()
	{
		return goldCardPile;
	}
	
	//used for both random and face up, same as counters
	public void drawCard(Card c) {
		
		currentPlayer.receiveCard(c);
		currentPlayer.setDrewGoldCard(false);
		
		if (currentPhase == GamePhase.DRAWCARDAFTERTRAVELTWO) {
			currentPhase = GamePhase.MOVEBOOT;
			passTurn();
			return;
		}
		if(currentPhase == GamePhase.DRAWCARDAFTERTRAVELONE) {
			currentPhase = GamePhase.DRAWCARDAFTERTRAVELTWO;
			return;
		}
		
		//get next player
		int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
		int nextPlayerIndex = currentPlayerIndex;
		if (currentPlayerIndex < participants.size() - 1) {
			nextPlayerIndex++;
		}
		else {
			nextPlayerIndex = 0;
		}
		currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
				
		//if all players have passed
		GamePhase nextPhase = null;
		//remember logic
		if (nextPlayerIndex == currentRound%playerIndex.size()) {
			if(currentPhase == GamePhase.DRAWTRAVELCARDSONE) {
				nextPhase = GamePhase.DRAWTRAVELCARDSTWO;
			}
			if(currentPhase == GamePhase.DRAWTRAVELCARDSTWO) {
				nextPhase = GamePhase.DRAWTRAVELCARDSTHREE;
			}
			if(currentPhase == GamePhase.DRAWTRAVELCARDSTHREE) {
				
				for (String id : participants.keySet()) {
					Player p = participants.get(id);
					p.addGoldCoins(2);
				}
				

				nextPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
			}
			currentPhase = nextPhase;
		}
		
		if(currentPhase == GamePhase.DRAWTOKENSANDCOUNTERS)
		{
			System.out.println("Notifying observers now!");
			
			observer.restartChooseVisibleCounter();
		}
		else
		{
			System.out.println("Notifying observers now!");
			
			observer.restartDrawCardGUI();
		}
		
		return;
		
	}
	
	public void chooseGoldCardPile() {
		currentPlayer.addGoldCoins(3*goldCardPile);
		goldCardPile = 0;
		
		//command send out gold card pile command
		
		if (currentPhase == GamePhase.DRAWCARDAFTERTRAVELTWO) {
			currentPhase = GamePhase.MOVEBOOT;
			passTurn();
			return;
		}
		if(currentPhase == GamePhase.DRAWCARDAFTERTRAVELONE) {
			currentPhase = GamePhase.DRAWCARDAFTERTRAVELTWO;
			observer.restartDrawCardGUI();
			return;
		}
		
		//get next player
		int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
		int nextPlayerIndex = currentPlayerIndex;
		if (currentPlayerIndex < participants.size() - 1) {
			nextPlayerIndex++;
		}
		else {
			nextPlayerIndex = 0;
		}
		currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
		
		System.out.println("New current player is " + currentPlayer.getName());
		
		//if all players have passed
		GamePhase nextPhase = null;
		//my logic here is if you have more rounds than participants, you get the modulo
		if (nextPlayerIndex == currentRound%(participants.size()) ) {
			if(currentPhase == GamePhase.DRAWTRAVELCARDSONE) {
				nextPhase = GamePhase.DRAWTRAVELCARDSTWO;
			}
			if(currentPhase == GamePhase.DRAWTRAVELCARDSTWO) {
				nextPhase = GamePhase.DRAWTRAVELCARDSTHREE;
			}
			if(currentPhase == GamePhase.DRAWTRAVELCARDSTHREE) {
				
				for (String id : participants.keySet()) {
					Player p = participants.get(id);
					p.addGoldCoins(2);
				}
				

				nextPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
			}
			currentPhase = nextPhase;
		}
		
		if(currentPhase == GamePhase.DRAWTOKENSANDCOUNTERS)
		{
			System.out.println("Notifying observers now!");
			
			observer.restartChooseVisibleCounter();
		}
		else
		{
			System.out.println("Notifying observers now!");
			
			observer.restartDrawCardGUI();
		}
		
		return;
	}
	
	

	public void chooseFaceUpCounter(int i, String playerID) {
	
		//get player
		Player sendingPlayer = this.getPlayer(playerID);
		//fix 
		if (sendingPlayer == null) {
			return;
		}
		
		Counter c = sendingPlayer.getCounters().get(i);
		c.setVisibility(true);
		
		//question - do we want to trigger this from server like in choose boot ?
		//check if ready and set to next phase
		finishedPhase.add(sendingPlayer);
		if(finishedPhase.size() == participants.size()) {
			finishedPhase.clear();
			currentPhase = GamePhase.AUCTION;
			currentAuctionCounter = auctionPile.remove(0);
			observer.restartAuctionGUI();
		}
		return;
	}
	
	public Integer getHighestBid()
	{
		if(currentBids.isEmpty()) {
			return 0;
		}
		return currentBids.get(0);
	}
	
	public String getHighestBidder()
	{
		return currentBidders.get(0);
	}
	
	public ArrayList<String> getCurrentBidders()
	{
		return new ArrayList<String>(currentBidders);
	}
	public ArrayList<Integer> getCurrentBids()
	{
		return new ArrayList<Integer>(currentBids);
	}
	
	public Counter getCurrentAuctionCounter()
	{
		return currentAuctionCounter;
	}
	
	public ArrayList<Counter> getAuctionPile()
	{
		return new ArrayList<Counter>(auctionPile);
	}

	//pre - bid must be higher than previous currentBid
	public void placeBid(int bid) {
		
		System.out.println("In PlaceBid in ElfengoldGAme.java on client");
		
		currentBids.add(0, bid);
		currentBidders.add(0, currentPlayer.getName());
		currentHighestBidder = currentPlayer;
		
		//this will deal with auction passes, where all but one person have passed and there is a bidder
		if(finishedPhase.size() == participants.size()-1) {
			
			//otherwise give the counter, remove the gold, and move on
			if(currentHighestBidder.getGoldCoins() >= currentBids.get(0)) {
				currentHighestBidder.removeGoldCoins(currentBids.get(0));
				currentHighestBidder.addCounter(currentAuctionCounter);
				if(!auctionPile.isEmpty()) {
					System.out.println("and there's more counters too!");
					currentAuctionCounter = auctionPile.remove(0);
					
					//reset for next counter auction or round
					currentHighestBidder = null;
					currentBids = new ArrayList<Integer>();
					currentBidders = new ArrayList<String>();
					finishedPhase.clear();
					currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
					
					observer.restartAuctionGUI();
				}
				else {
					currentPhase = GamePhase.PLANTRAVEL;
					System.out.println("and no more counters to bid on!");
					observer.restartPlaceCounterOnRoadGUI();
				}
			}
			//if not enough gold, take all their gold but keep the same counter
			else {
				currentHighestBidder.clearGold();
				System.out.println("and ur too poor for this piece!");
				
				//reset for next counter auction or round
				currentHighestBidder = null;
				currentBids = new ArrayList<Integer>();
				currentBidders = new ArrayList<String>();
				finishedPhase.clear();
				currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
						
				observer.restartAuctionGUI();
			}
					
			return;
		}
		
		//get next player
		int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
		int nextPlayerIndex = currentPlayerIndex;
		if (currentPlayerIndex < participants.size() - 1) {
			nextPlayerIndex++;
		}
		else {
			nextPlayerIndex = 0;
		}
		currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
	
		//if finishedPhase contains the next player, keep going until it doesn't
		while(finishedPhase.contains(currentPlayer)) {
			currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
			nextPlayerIndex = currentPlayerIndex;
			if (currentPlayerIndex < participants.size() - 1) {
				nextPlayerIndex++;
			}
			else {
				nextPlayerIndex = 0;
			}
			currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
		}
		System.out.println("restarting da gui");
		observer.restartAuctionGUI();
		
		System.out.println("Notified the observer");

	}
	
	@Override
	public void passTurn() {
		
		//end game if end of round and a player has visited all towns
		if(finishedPhase.size() == participants.size() - 1 && currentPhase == GamePhase.MOVEBOOT) {
			for (String id : participants.keySet()) {
				Player p = participants.get(id);
				if(p.getPoints() == TownName.values().length) {
					return;
				}
			}
		}
		//this will add the player to the finished phase 
		//if finished phase is full, it will move on to next phase
		//otherwise will go to next player
		super.passTurn();
		
		//this will deal with auction passes, where all but one person have passed and there is a bidder
		if(finishedPhase.size() == participants.size()-1 && currentPhase == GamePhase.AUCTION && currentHighestBidder != null) {
			System.out.println("there's one bidder left ");
			//otherwise give the counter, remove the gold, and move on
			if(currentHighestBidder.getGoldCoins() >= currentBids.get(0)) {
				currentHighestBidder.removeGoldCoins(currentBids.get(0));
				currentHighestBidder.addCounter(currentAuctionCounter);
				if(!auctionPile.isEmpty()) {
					System.out.println("and there's more counters too!");
					currentAuctionCounter = auctionPile.remove(0);
					
					//reset for next counter auction or round
					currentHighestBidder = null;
					currentBids = new ArrayList<Integer>();
					currentBidders = new ArrayList<String>();
					finishedPhase.clear();
					currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
					
					observer.restartAuctionGUI();
				}
				else {
					currentPhase = GamePhase.PLANTRAVEL;
					System.out.println("and no more counters to bid on!");
					
					//reset for next counter auction or round
					currentHighestBidder = null;
					currentBids = new ArrayList<Integer>();
					currentBidders = new ArrayList<String>();
					finishedPhase.clear();
					currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
					
					observer.restartPlaceCounterOnRoadGUI();
				}
			}
			//if not enough gold, take all their gold but keep the same counter
			else {
				currentHighestBidder.clearGold();
				System.out.println("and ur too poor for this piece!");
				
				//reset for next counter auction or round
				currentHighestBidder = null;
				currentBids = new ArrayList<Integer>();
				currentBidders = new ArrayList<String>();
				finishedPhase.clear();
				currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
				
				observer.restartAuctionGUI();
			}
			
			return;
		}
		
		//also auction pass, if no one bid and the last person passed
		if(finishedPhase.size() == participants.size() && currentPhase == GamePhase.AUCTION) {
			System.out.println("everyone passed");
			if(!auctionPile.isEmpty()) {
				System.out.println("and there are more counters!");
				currentAuctionCounter = auctionPile.remove(0);
				
				//reset for next counter auction or round
				currentHighestBidder = null;
				currentBids = new ArrayList<Integer>();
				currentBidders = new ArrayList<String>();
				finishedPhase.clear();
				currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
				
				observer.restartAuctionGUI();
			}
			else {
				System.out.println("and the counters are done. cancelled.");
				currentPhase = GamePhase.PLANTRAVEL;
				
				//reset for next counter auction or round
				currentHighestBidder = null;
				currentBids = new ArrayList<Integer>();
				currentBidders = new ArrayList<String>();
				finishedPhase.clear();
				currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
				
				observer.restartPlaceCounterOnRoadGUI();
			}
			
//			//reset for next counter auction or round
//			currentHighestBidder = null;
//			currentBids = new ArrayList<Integer>();
//			currentBidders = new ArrayList<String>();
//			finishedPhase.clear();
//			currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
			
			return;
		}

		//if finishedPhase contains the next player, keep going until it doesn't - only happens if didn't just finish an auction
		while(finishedPhase.contains(currentPlayer)) {
			int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
			int nextPlayerIndex = currentPlayerIndex;
			if (currentPlayerIndex < participants.size() - 1) {
				nextPlayerIndex++;
			}
			else {
				nextPlayerIndex = 0;
			}
			currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
		}
		
		if (currentPhase == GamePhase.AUCTION) {
			observer.restartAuctionGUI();
		}
		
		return;
	}
	
	public void endTravel() {
		if(currentPlayer.getNumCards() > 4 && currentRound != numOfRounds) {
			observer.restartChooseCardsToKeep();
		}
		else {
			observer.restartEndTravelGUI();
		}
	}
	
	@Override
	public void travelOnRoad(TownName t, ArrayList<Integer> usedCards) {
		accumulatedGold += t.getGoldValue();
		
		//if traveled on road with gold counter, add gold value a second time
		if(currentPlayer.getLocation().hasRoad(t)) {
			for (Counter tempCounter : currentPlayer.getLocation().getRoad(t).getCounters()) {
				if (tempCounter.getCounterKind() == CounterKind.GOLD) {
					accumulatedGold += t.getGoldValue();
					break;
				}
			}
		}
		
		super.travelOnRoad(t, usedCards);
	}
	
	public void chooseCardsToKeep(ArrayList<Integer> chosenCardIndices) {
		ArrayList<Card> chosenCards = new ArrayList<Card>();
		for(Integer i : chosenCardIndices) {
			chosenCards.add(currentPlayer.getCards().get(i));
		}
		
		System.out.println("have this many cards to keep: "+chosenCards.size());
		
		//remove all but the chosen cards and send the other cards to discard pile
		for(Card currentCard : currentPlayer.getCards()) {
			if(!chosenCards.contains(currentCard)) {
				currentPlayer.removeCard(currentCard);
			}
		}
		observer.restartEndTravelGUI();
	}
	
	
	public void passTravel(EndTravelOption choice) {
		if(choice == EndTravelOption.TAKEGOLD) {
			currentPlayer.addGoldCoins(accumulatedGold);
			passTurn();
		}
		else if(choice == EndTravelOption.TAKECARDS) {
			currentPhase = GamePhase.DRAWCARDAFTERTRAVELONE;
			observer.restartDrawCardGUI();
		}
		
		accumulatedGold = 0;
	}
	
	public void chooseCounterToKeep(ArrayList<Integer> i, String playerID) {
		
		System.out.println("IN CHOOSE COUTNER TO KEEP COMMAND");
		System.out.println("Player choosing counter to keep: " + playerID);
		//get player
		Player sendingPlayer = getPlayer(playerID);
		//fix 
		if (sendingPlayer == null) {
			return;
		}
		
		//get the counters they wanted to keep
		ArrayList<Counter> countersToKeep = new ArrayList<Counter>();
		
		for(Integer k : i) {
			countersToKeep.add(sendingPlayer.getCounters().get(k));
		}
		
		//remove all the other counters
		
		for (int j = 0; j<sendingPlayer.getNumCounters(); j++) {
			Counter currentCounter = sendingPlayer.getCounters().get(j);
			if (!countersToKeep.contains(currentCounter) && currentCounter.getCounterKind() != CounterKind.OBSTACLE) {
				sendingPlayer.removeCounterByIndex(j);
			}
		}
		
		return;
	}

	@Override
	public void finishRound() {
	
		currentPhase = GamePhase.DRAWTRAVELCARDSONE;
		currentRound++;
		finishedPhase.clear();
		
		currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
		
		//clear roads
		for (Integer key : Road.ROADS.keySet()) {
			Road r = Road.ROADS.get(key);
			ArrayList<Counter> roadCounters = r.getCounters();
			for (Counter c : roadCounters) {
				r.removeCounter(c);
			}
		}
		
		
		
		observer.restartDrawCardGUI();
		return;
	}
	
	
	public void placeExchange(RegionKind kind1, TownName source1, 
			TownName dest1, RegionKind kind2, TownName source2, TownName dest2, 
			int i1, int i2, int exc)
	{
		Road r1 = Road.get(kind1, source1, dest1);
		Road r2 = Road.get(kind2, source2, dest2);
		Counter c1 = r1.getCounters().get(i1);
		Counter c2 = r2.getCounters().get(i2);
	
		r1.removeCounter(c1);
		r2.removeCounter(c2);
		r1.placeCounter(c2);
		r2.placeCounter(c1);
		
		currentPlayer.removeCounterByIndex(exc);
		
		finishedPhase.clear();
		
		//get next player
		int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
		int nextPlayerIndex = currentPlayerIndex;
		if (currentPlayerIndex < participants.size() - 1) {
			nextPlayerIndex++;
		}
		else {
			nextPlayerIndex = 0;
		}
		currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
		
		observer.restartPlaceCounterOnRoadGUI();
		return;
	}
	
	public void placeDouble(RegionKind kind, TownName source, TownName dest, 
			int i1, int i2)
	{
		Road r = Road.get(kind, source, dest);
		Counter c1 = currentPlayer.getCounters().get(i1);
		Counter c2 = currentPlayer.getCounters().get(i2);
		
		currentPlayer.removeCounter(c1);
		currentPlayer.removeCounter(c2);

		r.placeCounter(c2);
		r.placeCounter(c1);
		
		finishedPhase.clear();
		
		//get next player
		int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
		int nextPlayerIndex = currentPlayerIndex;
		if (currentPlayerIndex < participants.size() - 1) {
			nextPlayerIndex++;
		}
		else {
			nextPlayerIndex = 0;
		}
		currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
		
		observer.restartPlaceCounterOnRoadGUI();
		return;
	}
}
