package gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class ElfengoldGame extends Game{
	final private ArrayList<Counter> auctionPile = new ArrayList<>();
	private Counter currentAuctionCounter;
	private int currentBid;
	private Player currentHighestBidder;
	private int accumulatedGold = 0;
	private ArrayList<Card> faceUpCards = new ArrayList<>();
	private int goldCardPile = 0;

	public ElfengoldGame(GameVariant gv, String userName) {
		super(gv, userName);
		return;
	}
	
	
	
	@Override
	protected void setNumOfRounds() {
		this.numOfRounds = 6;
	}
	
	@Override
	protected void initiateCardsAndCounters() {
		//initiating cards
		for (TransportationKind kind : TransportationKind.values()) {
			for (int i = 0; i<9; i++) {
				TravelCard card = new TravelCard(kind);
				cardDeck.add(card);
			}
		}
		if(gameVariant == GameVariant.ELVENWITCHES) {
			for (int i = 0; i<6; i++) {
				Card card = new Card(CardKind.ELVENWITCH);
				cardDeck.add(card);
			}
		}
		
		//counters
		for(int i = 0; i<4; i++) {
			TravelCounter dragonCounter = new TravelCounter(TransportationKind.DRAGON, false);
			counterPile.add(dragonCounter);
		}
		for(int i = 0; i<4; i++) {
			TravelCounter c = new TravelCounter(TransportationKind.MAGICCLOUD, false);
			counterPile.add(c);
		}
		for(int i = 0; i<5; i++) {
			TravelCounter c = new TravelCounter(TransportationKind.UNICORN, false);
			counterPile.add(c);
		}
		for(int i = 0; i<8; i++) {
			TravelCounter c = new TravelCounter(TransportationKind.ELFCYCLE, false);
			counterPile.add(c);
		}
		for(int i = 0; i<8; i++) {
			TravelCounter c = new TravelCounter(TransportationKind.TROLLWAGON, false);
			counterPile.add(c);
		}
		for(int i = 0; i<9; i++) {
			TravelCounter c = new TravelCounter(TransportationKind.GIANTPIG, false);
			counterPile.add(c);
		}
		for(int i = 0; i<2; i++) {
			Counter c = new Counter(CounterKind.GOLD, false);
			counterPile.add(c);
		}
		for(int i = 0; i<2; i++) {
			Counter c = new Counter(CounterKind.DOUBLE, false);
			counterPile.add(c);
		}
		for(int i = 0; i<2; i++) {
			Counter c = new Counter(CounterKind.EXCHANGE, false);
			counterPile.add(c);
		}
		for(int i = 0; i<2; i++) {
			Counter c = new Counter(CounterKind.OBSTACLE, false);
			counterPile.add(c);
		}
		for(int i = 0; i<2; i++) {
			Counter c = new Counter(CounterKind.SEAMONSTER, false);
			counterPile.add(c);
		}
		
	}
	
	@Override
	public void setupRound() {
		
		//move onto next part of game
		currentRound = 1;
		
		System.out.println("Counter Pile number of counters: " + counterPile.size());
		
		//set the counters to be auctioned
		while (auctionPile.size() < participants.size()*2) {
			int i = (int) (Math.random()*(counterPile.size()));
			Counter counter = counterPile.remove(i);
			counter.setVisibility(true);
			auctionPile.add(counter);
		}
		Server.serverChannel.setAuctionCounters(auctionPile);
		
		//give each player two hidden counter, 5 cards, 12 gold coins
		for (String id : participants.keySet()) {

			Player p = participants.get(id);
			
			p.addGoldCoins(12);
			
			ArrayList<Card> cardsDist = new ArrayList<Card>();
			for (int j = 0; j<5; j++) {
				Card card = cardDeck.remove(0);
				p.receiveCard(card);
				cardsDist.add(card);
			}
			
			//COMMAND SEND 5 CARDS
			ArrayList<Counter> countersDist = new ArrayList<Counter>();
			for(int j = 0; j<2; j++) {
				int i = (int) (Math.random()*(counterPile.size()));
				Counter counter = counterPile.remove(i);
				
				// counter.setVisibility(false);
				p.addCounter(counter);
				countersDist.add(counter);
			}
			
			//COMMAND send the two counters (all hidden) and with the next phase, allow players to choose face up one
			
			Server.serverChannel.setDistributedCardsAndTCElfenGold(id, countersDist, cardsDist);
		
			
		}
		
		//setFaceUpCards
		while (faceUpCards.size() < 3) {
			int i = (int) (Math.random()*(cardDeck.size()));
			Card card = cardDeck.remove(i);
			faceUpCards.add(card);
		}
		
//		command send face up cards
		Server.serverChannel.sendFaceUpCardsCommand(faceUpCards);
		
		// If game variant is the town card one you also get a random town and you send that one through as well
		if(gameVariant == GameVariant.TOWNCARDS) {
			this.assignTownCards();
		}
			
			
		
		//set player and round
		currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
		
		currentPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
		
		System.out.println("The current player is: " + currentPlayer.getName());
		
		Server.serverChannel.startChooseVisibleCounterPhase();
		
	}
	
	public void drawCard(int i) {
		
		Card c = faceUpCards.get(i);
		System.out.println("SERVER: In DrawCard, the number of faceup cards is: " + faceUpCounters.size());
		//give counter to player
		faceUpCards.remove(c);
		currentPlayer.receiveCard(c);
		
//		Server.serverChannel.sendChooseCardCommand(c);
		Server.serverChannel.sendDrawFaceUpCardCommand(c);
		
		
		System.out.println("SERVER: In DrawCounter, the number of faceup coutners is: " + faceUpCounters.size());
		
		
		//replace card in face up pile
		int rand = (int) (Math.random()*(cardDeck.size()));
		Card newFaceUp = cardDeck.remove(rand);
		faceUpCards.add(newFaceUp);
		
		System.out.println("SERVER: ABOUT TO SEND THE NEW FACE UP CARDS the number of faceup cards is: " + faceUpCards.size());
		
//		
//		Server.serverChannel.setFaceUpCards(faceUpCards);
		Server.serverChannel.sendFaceUpCardsCommand(faceUpCards);
		
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
		if (nextPlayerIndex%7 == currentRound%(participants.size()) ) {
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
					
					ArrayList<Card> cardsDist = new ArrayList<>();
					
					ArrayList<Counter> countersDist = new ArrayList<Counter>();
					for(int j = 0; j<2; j++) {
						Counter counter = counterPile.remove(0);
						
						// counter.setVisibility(false);
						p.addCounter(counter);
						countersDist.add(counter);
					}
					
					Server.serverChannel.setDistributedCardsAndTCElfenGold(id, countersDist, cardsDist);
				}
				

				nextPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
			}
			currentPhase = nextPhase;
		}
		
		return;
		
	}
	
	
	public void drawRandomCard() {
		
		System.out.println("SERVER in drawrandomcard method of game.java");
		
		int rand = (int) (Math.random()*(cardDeck.size()));
		Card card = cardDeck.remove(rand);
		
		if(card.getCardKind() == CardKind.GOLD) {
			goldCardPile++;
			
			//update currentPlayer.drewGoldCard
			//send command update goldCardPile
			Server.serverChannel.sendGoldCardPileCommand(goldCardPile);
			
			
			return;
		}
		
		Server.serverChannel.sendDrawRandomCardCommand(card);
		System.out.println("SERVER sent the choose card command!");
			
		currentPlayer.receiveCard(card);
		
		
//		Server.serverChannel.sendChooseCardCommand(card);
		Server.serverChannel.sendDrawRandomCardCommand(card);

		
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
					
					ArrayList<Card> cardsDist = new ArrayList<>();
					
					ArrayList<Counter> countersDist = new ArrayList<Counter>();
					for(int j = 0; j<2; j++) {
						Counter counter = counterPile.remove(0);
						
						// counter.setVisibility(false);
						p.addCounter(counter);
						countersDist.add(counter);
					}
					
					Server.serverChannel.setDistributedCardsAndTCElfenGold(id, countersDist, cardsDist);
				}
				
			}
				

			nextPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
		}
		currentPhase = nextPhase;
	}
		
	
	public void chooseGoldCardPile() {
		currentPlayer.addGoldCoins(3*goldCardPile);
		goldCardPile = 0;
		
		//command send out gold card pile command
		Server.serverChannel.sendGoldCardPileCommand(goldCardPile);
		
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
					
					ArrayList<Card> cardsDist = new ArrayList<>();
					
					ArrayList<Counter> countersDist = new ArrayList<Counter>();
					for(int j = 0; j<2; j++) {
						Counter counter = counterPile.remove(0);
						
						// counter.setVisibility(false);
						p.addCounter(counter);
						countersDist.add(counter);
					}
					
					Server.serverChannel.setDistributedCardsAndTCElfenGold(id, countersDist, cardsDist);
				}
				
			}
				

			nextPhase = GamePhase.DRAWTOKENSANDCOUNTERS;
		}
		currentPhase = nextPhase;
	}
		
	
	
	
	public void chooseFaceUpCounter(int i, String playerID) {
		//set the counter to visible - actually don't really need this for server ? wtv i did it anyway
		//c.setVisibility(true);
		//COMMAND ? to tell clients which of everyone's things is true
	
		//get player
		Player sendingPlayer = this.getPlayer(playerID);
		//fix 
		if (sendingPlayer == null) {
			return;
		}
		
		Counter c = sendingPlayer.getCounters().get(i);
		c.setVisibility(true);
		
		//check if ready and set to next phase
		finishedPhase.add(sendingPlayer);
		if(finishedPhase.size() == participants.size()) {
			finishedPhase.clear();
			currentPhase = GamePhase.AUCTION;
			currentAuctionCounter = auctionPile.remove(0);
//			Server.serverChannel.startAuctionPhase();
		}
		return;
	}
	
	//pre - bid must be higher than prev currentBid
	public void placeBid(int bid) {
		currentBid = bid;
		currentHighestBidder = currentPlayer;
		
		
		//this will deal with last person being the bidder and everyone else passed
		if(finishedPhase.size() == participants.size()-1) {
			//otherwise give the counter, remove the gold, and move on
			if(currentHighestBidder.getGoldCoins() >= currentBid) {
				currentHighestBidder.removeGoldCoins(currentBid);
				currentHighestBidder.addCounter(currentAuctionCounter);
				System.out.println(currentPlayer.getName()+" now has"+currentPlayer.getNumCounters()+" counters");
				if(!auctionPile.isEmpty()) {
					currentAuctionCounter = auctionPile.remove(0);
				}
				else {
					currentPhase = GamePhase.PLANTRAVEL;
				}
			}
			//if not enough gold, take all their gold but keep the same counter
			else {
				currentHighestBidder.clearGold();
			}
			
			//reset for next counter auction or round
			currentHighestBidder = null;
			currentBid = 0;
			finishedPhase.clear();
			currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
			
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
	}
	
	@Override
	public void passTurn() {
		
		//end game if end of round and a player has visited all towns
		if(finishedPhase.size() == participants.size() - 1 && currentPhase == GamePhase.MOVEBOOT) {
			for (String id : participants.keySet()) {
				Player p = participants.get(id);
				if(p.getPoints() == TownName.values().length) {
					getWinner();
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
			//otherwise give the counter, remove the gold, and move on
			if(currentHighestBidder.getGoldCoins() >= currentBid) {
				currentHighestBidder.removeGoldCoins(currentBid);
				currentHighestBidder.addCounter(currentAuctionCounter);
				System.out.println(currentPlayer.getName()+" now has"+currentPlayer.getNumCounters()+" counters");
				if(!auctionPile.isEmpty()) {
					currentAuctionCounter = auctionPile.remove(0);
				}
				else {
					currentPhase = GamePhase.PLANTRAVEL;
				}
			}
			//if not enough gold, take all their gold but keep the same counter
			else {
				currentHighestBidder.clearGold();
			}
			
			//reset for next counter auction or round
			currentHighestBidder = null;
			currentBid = 0;
			finishedPhase.clear();
			currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
			
			return;
		}
		
		//also auction pass, if no one bid and the last person passed
		if(finishedPhase.size() == participants.size() && currentPhase == GamePhase.AUCTION) {
			counterPile.add(currentAuctionCounter);
			if(!auctionPile.isEmpty()) {
				currentAuctionCounter = auctionPile.remove(0);
			}
			else {
				currentPhase = GamePhase.PLANTRAVEL;
			}
			
			//reset for next counter auction or round
			currentHighestBidder = null;
			currentBid = 0;
			finishedPhase.clear();
			currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
			
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
		
		return;
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
	
	public void passTravel(EndTravelOption choice) {
		if(choice == EndTravelOption.TAKEGOLD) {
			currentPlayer.addGoldCoins(accumulatedGold);
			passTurn();
		}
		else if(choice == EndTravelOption.TAKECARDS) {
			currentPhase = GamePhase.DRAWCARDAFTERTRAVELONE;
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
			if (!countersToKeep.contains(currentCounter)) {
				Counter counter = sendingPlayer.removeCounterByIndex(j);
				counter.setVisibility(false);
				counterPile.add(counter);
			}
		}
		
		//check if ready and wrap up the round
		finishedPhase.add(sendingPlayer);
		if(finishedPhase.size() == participants.size() && currentRound < numOfRounds) {
			
			System.out.println("Sending finish round command");
			Server.serverChannel.sendFinishRoundCommand();
			
			this.finishRound();
		}
		return;
	}
	
	public void chooseCardsToKeep(ArrayList<Integer> chosenCardIndices) {
		ArrayList<Card> chosenCards = new ArrayList<Card>();
		for(Integer i : chosenCardIndices) {
			chosenCards.add(currentPlayer.getCards().get(i));
		}
		
		
		//remove all but the chosen cards and send the other cards to discard pile
		for(Card currentCard : currentPlayer.getCards()) {
			if(!chosenCards.contains(currentCard)) {
				currentPlayer.removeCard(currentCard);
			}
		}
	}

	protected void finishRound() {
	
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
				c.setVisibility(false);
				counterPile.add(c);
				
			}
		}
		Collections.shuffle(counterPile);
		
		//COMMAND send the two counters (all hidden) and with the next phase, allow players to choose face up one
	
	}
	
	protected Player getWinner() {
		int highestPoints = 0;
		Player winner = null;
		for (String id : participants.keySet()) {
			Player p = participants.get(id);
			int points = p.getNumVisited();
			if(gameVariant == GameVariant.TOWNCARDS) {
				points -= shortestPath(p.getLocation(), p.getDestination());
			}
			if (points > highestPoints) {
				winner = p;
			}
			else if(points == highestPoints) {
				if(winner != null) {
					if (p.getGoldCoins() > winner.getGoldCoins()) {
						winner = p;
						highestPoints = points;
					}
					else if(p.getGoldCoins() == winner.getGoldCoins()) {
						if(p.getNumCards() > winner.getNumCards()) {
							winner = p;
							highestPoints = points;
						}
					}
				}
				else {
					winner = p;
					highestPoints = points;
				}
			}
		}
		//COMMAND TRIGGER SEND WINNER ?
		return winner;	
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
		
		return;
	}
	
	public void placeDouble(RegionKind kind, TownName source, TownName dest, 
			int i1, int i2)
	{
		Road r = Road.get(kind, source, dest);
		Counter c1 = currentPlayer.getCounters().get(i1);
		Counter c2 = currentPlayer.getCounters().get(i2);

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
		
		return;
	}
	
}
