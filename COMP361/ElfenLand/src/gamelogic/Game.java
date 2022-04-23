package gamelogic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Observable;

public class Game{
	public String myUsername;
	protected Player currentPlayer;
	protected int numOfRounds;
	protected int currentRound = 0;
	protected GamePhase currentPhase;
	final protected GameVariant gameVariant;
	final protected LinkedHashMap<String, Player> participants;
	final protected HashSet<Player> finishedPhase = new HashSet<>(); 
	protected ArrayList<Counter> faceUpCounters = new ArrayList<>();
	protected ArrayList<String> playerIndex;
	protected transient Observer observer = Client.instance();
	public Trade currentTrade;
	public ArrayList<Trade> tradeResponses = new ArrayList<>();
	public ArrayList<Player> respondedToTrade = new ArrayList<>();
	
	
	public Game(GameVariant gv, String userName) {
		this.gameVariant = gv;
		this.setNumOfRounds();
		this.participants = new LinkedHashMap<String, Player>();
		playerIndex = new ArrayList<String>();
		myUsername = userName;
		//currentPlayer = (Player) players.values().toArray()[0];

		
		// initiate townAdjacency
		
		
	}
	
	protected void setNumOfRounds() {
		if(gameVariant == GameVariant.FOURROUNDS) {
			this.numOfRounds = 4;
		}
		else {
			this.numOfRounds = 3;
		}
	}
	
	public void setPlayerIndex(ArrayList<String> playerOrder)
	{
		playerIndex = new ArrayList<>(playerOrder);
	}
	
	public int getRound()
	{
		return currentRound;
	}
	
	public void setObserver() {
		observer = Client.instance();
	}
	
	public void nextRound()
	{
		currentRound++;
	}
	
	//returns null when no player
	public Player getPlayer(String id) {
		return participants.get(id);
	}
	
	public void addPlayer(String id, Player p) {
		participants.put(id, p);
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public Collection<Player> getParticipants()
	{
		return participants.values();
	}
	
	public GameVariant getVariant()
	{
		return gameVariant;
	}
	
	public GamePhase getCurrentPhase()
	{
		return currentPhase;
	}
	
	public void setCurrentPhase(GamePhase g) {
		currentPhase = g;
	}
	
	public void startChooseCounterPhase()
	{		
		System.out.println("IN StartChooseCounterPhase in Game.java");
		observer.restartChooseCounterGUI();
		System.out.println("DONEEEEEEE StartChooseCounterPhase in Game.java");
		
		return;
	}
	
	
	public void chooseBoot(String playerID, BootColor c) {
		
		System.out.println("CHOOSING BOOT :)");
		Player sendingPlayer = getPlayer(playerID);
		
		//set boot
		c.setTaken(true);
		
		sendingPlayer.setBoot(c);
		System.out.print("Player's boot:");
		System.out.print(sendingPlayer.getBoot().name());
		System.out.print(participants.get(playerID).getBoot().name());
		
	}
	
	public void setupFirstRound() {
		
		//move onto next part of game
		currentRound = 1;
		
		//give all players an obstacle
		for (String id : participants.keySet()) {
			Player p = participants.get(id);
			p.setLocation(TownName.ELVENHOLD);
			Counter obstacle = new Counter(CounterKind.OBSTACLE, false);
			p.addCounter(obstacle);
		}
		
		
		
	}
	
	public void setFaceUpCounters(ArrayList<Counter> pFaceUpCounters) 
	{
		System.out.println("In set Face up counters on the client game.java");
		faceUpCounters = pFaceUpCounters;
	}
	
	public void SetAuctionCounters(ArrayList<Counter> pAuctionCounters) {
		
	}
	
	
	public void setDistributedCardsAndTC(String playerID, Counter counter, ArrayList<Card> cards)
	{
		
		System.out.println("In the game.java setDistributedCards and TC function!");
		
		Player p = participants.get(playerID);
		
		if(counter != null) {
			p.addCounter(counter);
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
					
			currentPhase = GamePhase.DRAWFACEUPCOUNTERONE;
//			if(playerID == myUsername) {
//				Client.startChooseCounter(Client.gui);
//			}
			
		}
		
	}
	
	
	public ArrayList<Counter> getFaceUpCounters()
	{
		return new ArrayList<Counter>(faceUpCounters);
	}
	
	public void addFaceUpCounters(Counter c)
	{
		faceUpCounters.add(c);
	}
	
	public void removeFaceUpCounter(int c)
	{
		faceUpCounters.remove(c);
	}
	
	public void setCurrentPlayer(Player player)
	{
		this.currentPlayer = player;
	}
	
	// This is used for both random counter and face up coutner
	public void drawCounter(Counter c) {
		
		System.out.println("CLIENT: IN THE draw counter method of game.java");
		
		// fix this it's not supposed to be this way
		//give counter to player
		c.setVisibility(true);
		currentPlayer.addCounter(c);
		
		
		
		
		
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
			if(currentPhase == GamePhase.DRAWFACEUPCOUNTERONE) {
				nextPhase = GamePhase.DRAWFACEUPCOUNTERTWO;
			}
			if(currentPhase == GamePhase.DRAWFACEUPCOUNTERTWO) {
				nextPhase = GamePhase.DRAWFACEUPCOUNTERTHREE;
			}
			if(currentPhase == GamePhase.DRAWFACEUPCOUNTERTHREE) {
				nextPhase = GamePhase.PLANTRAVEL;
			}
			
			currentPhase = nextPhase;
			
			
		}
		
		System.out.println("CLIENT: IN THE draw counter method of game.java the current phase is: " + currentPhase);
		
		if(currentPhase == GamePhase.PLANTRAVEL)
		{
			System.out.println("Notifying observers now!");
			
			observer.restartPlaceCounterOnRoadGUI();
		}
		else
		{
			System.out.println("Notifying observers now!");
			
			observer.restartChooseCounterGUI();
		}
		
		
		
		return;
		
	}
	
	
	public void chooseCounterToKeep(int i, String playerID) {
		//get player
		Player sendingPlayer = getPlayer(playerID);
		//fix 
		if (sendingPlayer == null) {
			return;
		}
		
		//remove all the other counters
		System.out.println("counter to keep index: "+ i);
		Counter c = sendingPlayer.getCounters().get(i);
		
		for (Counter currentC : sendingPlayer.getCounters()) {
			if (c != currentC && currentC.getCounterKind() != CounterKind.OBSTACLE) {
				sendingPlayer.removeCounter(currentC);
			}
		}
		
//		//check if ready and wrap up the round
//		finishedPhase.add(sendingPlayer);
//		if(finishedPhase.size() == participants.size() && currentRound < numOfRounds) {
//			this.finishRound();
//		}
//		return;
	}
	
	public void endTravel() {
		if(currentPlayer.getNumCards() > 4 && currentRound != numOfRounds) {
			observer.restartChooseCardsToKeep();
		}
		else {
			Client.instance().executePassTurn();
		}
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
		passTurn();
	}
	
	public void passTurn() {
		
		System.out.println("IN PASS TURN METHOD OF GAME.JAVA");
		
		finishedPhase.add(currentPlayer);
		
		//if everyone has passed, move to next phase
		if (finishedPhase.size() == participants.size()) {
			GamePhase nextPhase = null;
			if(currentPhase == GamePhase.PLANTRAVEL) {
				nextPhase = GamePhase.MOVEBOOT;
				finishedPhase.clear();
				currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
			}
			if(currentPhase == GamePhase.MOVEBOOT && currentRound<numOfRounds) {
				nextPhase = GamePhase.FINISHROUND;
			
				for (String id : participants.keySet()) {
					Player p = participants.get(id);
					int numCounters = p.getNumCounters();
					
					if(this instanceof ElfengoldGame) {
						if (numCounters > 2) {
							finishedPhase.remove(p);
						}
					}
					
					else {
						boolean hasObstacle = false;
						for(Counter currentC : p.getCounters()) {
							if(currentC.getCounterKind() == CounterKind.OBSTACLE) {
								hasObstacle = true;
								break;
							}
						}
						if (numCounters > 1 && !hasObstacle) {
							finishedPhase.remove(p);
						}
						else if(numCounters > 2 && hasObstacle) {
							finishedPhase.remove(p);
						}
						
					}
					
				}
				//but if no one has any counters left, just finish round here
				if (finishedPhase.size() == participants.size()) {
					this.finishRound();
					return;
				}
			}
			if(nextPhase != null) {
				currentPhase = nextPhase;
			}
			
			System.out.println("Current player is: " + currentPlayer);
			
			if(currentPhase == GamePhase.MOVEBOOT) {
				
				System.out.println("Notifying observers now!");
				
				observer.restartTravelGUI();
				
			}
			else if(currentPhase == GamePhase.PLANTRAVEL) {
				System.out.println("Notifying observers now!");
				
				observer.restartPlaceCounterOnRoadGUI();
			}
			
			else if(currentPhase == GamePhase.FINISHROUND) {
				System.out.println("Notifying observers now!");
				
				boolean hasObstacle = false;
				for(Counter currentCounter : participants.get(myUsername).getCounters()) {
					if(currentCounter.getCounterKind() == CounterKind.OBSTACLE) {
						hasObstacle = true;
					}
				}
				
				if(!(this instanceof ElfengoldGame) && participants.get(myUsername).getNumCounters() > 1 && !hasObstacle) {
					
					observer.restartChooseCounterToKeep();
				}
				else if(participants.get(myUsername).getNumCounters() > 2 && (this instanceof ElfengoldGame || hasObstacle)) {

					observer.restartChooseCounterToKeep();
				}
			}
			
			
			return;
		}
		
		// otherwise set currentPlayer to the next player
		int currentPlayerIndex = playerIndex.indexOf(currentPlayer.getName());
		int nextPlayerIndex = currentPlayerIndex;
		if (currentPlayerIndex < participants.size() - 1) {
			nextPlayerIndex++;
		}
		else {
			nextPlayerIndex = 0;
		}
		currentPlayer = participants.get(playerIndex.get(nextPlayerIndex));
		
		if(currentPhase == GamePhase.MOVEBOOT) {
			
			System.out.println("Notifying observers now!");
			
			observer.restartTravelGUI();
			
		}
		else if(currentPhase == GamePhase.PLANTRAVEL) {
			System.out.println("Notifying observers now!");
			
			observer.restartPlaceCounterOnRoadGUI();
		}
	}
	
	public void placeCounter(int i, RegionKind kind, TownName source, TownName dest) {
		
		System.out.println("IN PLACE COUNTER!!!");
		
		System.out.println("counter index " +i);
		
		//to make sure it's the same road as instantiated here and not on the other side
		Road r = Road.get(kind, source, dest);
		Counter c = currentPlayer.removeCounterByIndex(i);
		
		System.out.println("The currentplayer has these counters:");
		
		for(Counter cou :currentPlayer.getCounters()) {
			
			System.out.println(cou.getCounterKind());
			
		}
		
		r.placeCounter(c);
		
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
		
		System.out.println("Notifying observers now!");
		
		observer.restartPlaceCounterOnRoadGUI();
		return;
	}
	
	public void travelOnRoad(TownName t, ArrayList<Integer> usedCards) {
		currentPlayer.setLocation(t);
		Collections.sort(usedCards);
		
		for(int j = usedCards.size() - 1; j>=0; j--) {
			Integer cardInd = usedCards.get(j);
			int i = cardInd.intValue();
			Card c = currentPlayer.removeCardByIndex(i);
			System.out.println("Just removed " + ((TravelCard) c).getTransportationKind());
		}
		
		System.out.println("Notifying observers now!");
		
		observer.restartTravelGUI();
		return;
	}
	
	//Commented out to do with town name but don't erase in case we want to do this too later
//	public void travelOnRoad(Road r, ArrayList<Card> usedCards) {
//		TownName oldLocation = currentPlayer.getLocation();
//		TownName sourceTown = r.getSourceTown();
//		TownName destinationTown = r.getDestinationTown();
//		if(oldLocation == sourceTown) {
//			currentPlayer.setLocation(destinationTown);
//		}
//		else {
//			currentPlayer.setLocation(sourceTown);
//		}
//		for(Card card : usedCards) {
//			currentPlayer.removeCard(card);
//			discardPile.add(card);
//		}
//		return;
//	}
	
	
	public void finishRound() {
		
		currentPhase = GamePhase.DRAWFACEUPCOUNTERONE;
		currentRound++;
		finishedPhase.clear();
		
		// Setting the new currentplayer, but not sure if this is good or not will have to see
		currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
		
		//clear roads
		for (Integer key : Road.ROADS.keySet()) {
			Road r = Road.ROADS.get(key);
			ArrayList<Counter> roadCounters = r.getCounters();
			for (Counter c : roadCounters) {
				r.removeCounter(c);
				c.setVisibility(false);
			}
		}
		System.out.println("Notifying observers now!");
		
		observer.restartChooseCounterGUI();
		
		System.out.println(Road.get(RegionKind.WOODS, TownName.ELVENHOLD, TownName.ERGEREN).getNumCounters());
		
	}
	
//	private Player getWinner() {
//		//get from server
//	}
	
	public void createInitialTrade(Trade t) {
		currentTrade = t;
		if(t.getP1().getName() != myUsername) {
			//start intialtraderesponsegui
		}
	}
	
	public void initialTradeResponse(Trade t) {
		tradeResponses.add(t);
		respondedToTrade.add(getPlayer(t.getP2().getName()));
	}
	
	public void startChooseVisibleCounterPhase()
	{
		
	}
	
	public void setDistributedCardsAndTC(String playerID, ArrayList<Counter> counters, ArrayList<Card> cards)
	{
		
	}
	
	public void setFaceUpCards(ArrayList<Card> pFaceUpCards) 
	{
		
	}
	
	public void setAuctionCounters(ArrayList<Counter> pAuctionCounters) 
	{
		
	}
	
	public void setGoldCardPile(int pGoldCardPile)
	{
		
	}
	
	public void drawCard(Card c) {
		
	}
	
	public void chooseGoldCardPile() {
		
	}
	
	public void chooseFaceUpCounter(int i, String playerID) {
		
	}
	
	public void placeBid(int bid) {
		
	}
	
	public void chooseCounterToKeep(ArrayList<Integer> i, String playerID) {
		
	}
	
	
	
	
	
} 