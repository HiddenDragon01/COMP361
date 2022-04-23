package gamelogic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class Game {
	public String  myUsername;
	protected Player currentPlayer;
	protected int numOfRounds;
	protected int currentRound = 0;
	protected GamePhase currentPhase;
	final protected GameVariant gameVariant;
	final protected LinkedHashMap<String, Player> participants;
	protected ArrayList<Card> cardDeck = new ArrayList<>();
	protected ArrayList<Card> discardPile = new ArrayList<>();
	final protected HashSet<Player> finishedPhase = new HashSet<>();             
//	final protected ArrayList<Road> roads = new ArrayList<>();
	final protected ArrayList<Counter> counterPile= new ArrayList<>();
	final protected ArrayList<Counter> faceUpCounters = new ArrayList<>();
	final protected ArrayList<String> playerIndex = new ArrayList<>();
	
	final static protected HashMap<TownName, ArrayList<TownName>> townConnections = new HashMap<>();
	static {
		townConnections.put(TownName.BEATA, new ArrayList<>(Arrays.asList(
				TownName.ELVENHOLD,
				TownName.STRYKHAVEN
				)));
		townConnections.put(TownName.ERGEREN, new ArrayList<>(Arrays.asList(
				TownName.ELVENHOLD,
				TownName.TICHIH
				)));
		townConnections.put(TownName.GRANGOR,new ArrayList<>(Arrays.asList(
				TownName.MAHDAVIKIA,
				TownName.YTTAR,
				TownName.PARUNDIA
				)));
		townConnections.put(TownName.JACCARANDA,new ArrayList<>(Arrays.asList(
				TownName.WYLHIEN,
				TownName.THROTMANNI,
				TownName.TICHIH
				)));
		townConnections.put(TownName.JXARA,new ArrayList<>(Arrays.asList(
				TownName.MAHDAVIKIA,
				TownName.DAGAMURA,
				TownName.LAPPHALIA,
				TownName.VIRST
				)));
		townConnections.put(TownName.MAHDAVIKIA,new ArrayList<>(Arrays.asList(
				TownName.GRANGOR,
				TownName.DAGAMURA,
				TownName.JXARA
				)));
		townConnections.put(TownName.STRYKHAVEN,new ArrayList<>(Arrays.asList(
				TownName.VIRST,
				TownName.BEATA,
				TownName.ELVENHOLD
				)));
		townConnections.put(TownName.TICHIH,new ArrayList<>(Arrays.asList(
				TownName.THROTMANNI,
				TownName.JACCARANDA,
				TownName.ERGEREN,
				TownName.RIVINIA
				)));
		townConnections.put(TownName.USSELEN,new ArrayList<>(Arrays.asList(
				TownName.YTTAR,
				TownName.WYLHIEN,
				TownName.PARUNDIA
				)));
		townConnections.put(TownName.WYLHIEN,new ArrayList<>(Arrays.asList(
				TownName.JACCARANDA,
				TownName.USSELEN,
				TownName.PARUNDIA,
				TownName.ALBARAN
				)));
		townConnections.put(TownName.VIRST,new ArrayList<>(Arrays.asList(
				TownName.ELVENHOLD,
				TownName.LAPPHALIA,
				TownName.JXARA,
				TownName.STRYKHAVEN
				)));
		townConnections.put(TownName.YTTAR,new ArrayList<>(Arrays.asList(
				TownName.GRANGOR,
				TownName.USSELEN,
				TownName.PARUNDIA
				)));
		townConnections.put(TownName.ALBARAN,new ArrayList<>(Arrays.asList(
				TownName.FEODOR,
				TownName.WYLHIEN,
				TownName.THROTMANNI,
				TownName.DAGAMURA,
				TownName.PARUNDIA
				)));
		townConnections.put(TownName.KIHROMAH,new ArrayList<>(Arrays.asList(
				TownName.DAGAMURA
				)));
		townConnections.put(TownName.DAGAMURA,new ArrayList<>(Arrays.asList(
				TownName.KIHROMAH,
				TownName.MAHDAVIKIA,
				TownName.JXARA,
				TownName.FEODOR,
				TownName.ALBARAN,
				TownName.LAPPHALIA
				)));
		townConnections.put(TownName.PARUNDIA,new ArrayList<>(Arrays.asList(
				TownName.GRANGOR,
				TownName.YTTAR,
				TownName.USSELEN,
				TownName.WYLHIEN,
				TownName.ALBARAN
				)));
		townConnections.put(TownName.FEODOR,new ArrayList<>(Arrays.asList(
				TownName.THROTMANNI,
				TownName.RIVINIA,
				TownName.LAPPHALIA,
				TownName.DAGAMURA,
				TownName.ALBARAN
				)));
		townConnections.put(TownName.THROTMANNI,new ArrayList<>(Arrays.asList(
				TownName.JACCARANDA,
				TownName.TICHIH,
				TownName.RIVINIA,
				TownName.FEODOR,
				TownName.ALBARAN
				)));
		townConnections.put(TownName.RIVINIA,new ArrayList<>(Arrays.asList(
				TownName.THROTMANNI,
				TownName.TICHIH,
				TownName.ELVENHOLD,
				TownName.FEODOR,
				TownName.LAPPHALIA
				)));
		townConnections.put(TownName.LAPPHALIA,new ArrayList<>(Arrays.asList(
				TownName.VIRST,
				TownName.JXARA,
				TownName.RIVINIA,
				TownName.FEODOR,
				TownName.DAGAMURA,
				TownName.ELVENHOLD
				)));
		townConnections.put(TownName.ELVENHOLD,new ArrayList<>(Arrays.asList(
				TownName.VIRST,
				TownName.BEATA,
				TownName.RIVINIA,
				TownName.ERGEREN,
				TownName.LAPPHALIA,
				TownName.STRYKHAVEN
				)));
	}
	
	public Game(GameVariant gv, String userName) {
		this.gameVariant = gv;
		this.setNumOfRounds();
		this.participants = new LinkedHashMap<String, Player>();
		myUsername = userName;
		//currentPlayer = (Player) players.values().toArray()[0];
		
		
		//initiating cards and counters
		this.initiateCardsAndCounters();
		
		Collections.shuffle(cardDeck);
		Collections.shuffle(counterPile);
			
		
		return;
	}
	
	protected void setNumOfRounds() {
		if(gameVariant == GameVariant.FOURROUNDS) {
			this.numOfRounds = 4;
		}
		else {
			this.numOfRounds = 3;
		}
	}
	
	protected void initiateCardsAndCounters() {
		//initiating cards
		for (TransportationKind kind : TransportationKind.values()) {
			for (int i = 0; i<10; i++) {
				TravelCard card = new TravelCard(kind);
				cardDeck.add(card);
			}
		}
		for (int i = 0; i<2; i++) {
			TravelCard raftCard = new TravelCard(TransportationKind.RAFT);
			cardDeck.add(raftCard);
		}
		
		//initiating counters
		for (TransportationKind kind : TransportationKind.values()) {
			if(kind != TransportationKind.RAFT) {
				for (int i = 0; i<8; i++) {
					Counter c = new TravelCounter(kind, false);
					counterPile.add(c);
				}
			}
		}
	}
	
	public void addPlayerID(String playerID) {
		playerIndex.add(playerID);
	}
	
	public void shufflePlayerIndex() {
		Collections.shuffle(playerIndex);
	}
	
	public ArrayList<String> getPlayerArray(){
		return this.playerIndex;
	}
	
	public int getRound()
	{
		return currentRound;
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
	
	
	public void chooseBoot(String playerID, BootColor c) {
		
		System.out.println("SERVER: In chooseboot function of Game.java");
		
		Player sendingPlayer = getPlayer(playerID);
		if (sendingPlayer == null) {
			System.out.println("SERVER: sending player is null :(");
			return;
		}
		
		
		//leave if boot is unavailable
		if(c.isTaken()) {
			System.out.println("SERVER: boot is taken :(");
			return;
		}
		
		//set boot
		c.setTaken(true);
		sendingPlayer.setBoot(c);
		
		System.out.println("SERVER: executing send boot info function");
		//send player boot info to all clients
		Server.serverChannel.executeSendBootInfo(playerID, c);
		
		//leave if more boots have to be chosen
		for (String id : participants.keySet()) {
			Player p = participants.get(id);
			
			if (! p.hasBoot()) {
				return;
			}
		}
		
		System.out.println("All Players have picked their boots!");
		
		// Send a notification to clients to initiate setup round
		
		Server.serverChannel.setupRound();
		
		// Server also initiates setup round
		setupRound();
		
		
	}
	
	public void setupRound() {
		
		//move onto next part of game
		currentRound = 1;
		
		//give all players an obstacle
		for (String id : participants.keySet()) {
			Player p = participants.get(id);
			p.setLocation(TownName.ELVENHOLD);
			Counter obstacle = new Counter(CounterKind.OBSTACLE, false);
			p.addCounter(obstacle);
		}
		
		//set the face up counters
		while (faceUpCounters.size() < 5) {
			int i = (int) (Math.random()*(counterPile.size()));
			Counter counter = counterPile.remove(i);
			counter.setVisibility(true);
			faceUpCounters.add(counter);
		}
		
		System.out.println("Sending the face up counters now!!!");
		
		Server.serverChannel.setFaceUpCounters(faceUpCounters);
		
		
		//give each player one hidden counter and 8 cards
		for (String id : participants.keySet()) {
			Player p = participants.get(id);
			int i = (int) (Math.random()*(counterPile.size()));
			Counter counter = counterPile.remove(i);
			// counter.setVisibility(false);
			p.addCounter(counter);
			
			ArrayList<Card> cardsDist = new ArrayList<Card>();
			for (int j = 0; j<8; j++) {
				Card card = cardDeck.remove(0);
				p.receiveCard(card);
				cardsDist.add(card);
			}
			
			System.out.println("Sending the 8 cards and Transport Counter!!!");
			
			Server.serverChannel.setDistributedCardsAndTC(id, counter, cardsDist);
			
		}
		
		
		
		// If gamevariant is the towncard one you also get a random town and you send that one through as well
		if(gameVariant == GameVariant.TOWNCARDS) {
			this.assignTownCards();
		}
			
			
		
		//set player and round
		currentPlayer = participants.get(playerIndex.get((currentRound%playerIndex.size())));
		
		currentPhase = GamePhase.DRAWFACEUPCOUNTERONE;
		
		System.out.println("The current player is: " + currentPlayer.getName());
		
		Server.serverChannel.startDrawFaceUpCounterPhase();
		
	}
	
	protected void assignTownCards() {
		ArrayList<TownName> destTowns = new ArrayList<>(Arrays.asList(
				TownName.BEATA,
				TownName.ERGEREN,
				TownName.GRANGOR,
				TownName.JACCARANDA,
				TownName.JXARA,
				TownName.MAHDAVIKIA,
				TownName.STRYKHAVEN,
				TownName.TICHIH,
				TownName.USSELEN,
				TownName.VIRST,
				TownName.WYLHIEN,
				TownName.YTTAR
				));
		Collections.shuffle(destTowns);
		
		for (String playerID : participants.keySet()) {
			Player player = participants.get(playerID);
			TownName townName = destTowns.remove(0);
			player.setDestination(townName);
			
			// Send that player's towncard to everyone using the server
			Server.serverChannel.sendTownCardCommand(playerID, townName);
		}
	}
	
	
	public void drawCounter(int i) {
		
		Counter c = faceUpCounters.get(i);
		System.out.println("SERVER: In DrawCounter, the number of faceup coutners is: " + faceUpCounters.size());
		//give counter to player
		faceUpCounters.remove(c);
		c.setVisibility(true);
		currentPlayer.addCounter(c);
		
//		Server.serverChannel.sendChooseCounterCommand(c);
		
		System.out.println("SERVER: In DrawCounter, the number of faceup coutners is: " + faceUpCounters.size());
		
		
		//replace counter in face up pile
		int rand = (int) (Math.random()*(counterPile.size()));
		Counter newFaceUp = counterPile.remove(rand);
		newFaceUp.setVisibility(true);
		faceUpCounters.add(newFaceUp);
		
		System.out.println("SERVER: In DrawCounter, the number of faceup coutners is: " + faceUpCounters.size());
		
		Server.serverChannel.setFaceUpCounters(faceUpCounters);
		
		Server.serverChannel.sendChooseCounterCommand(c);
		
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
		if (nextPlayerIndex == currentRound%(participants.size()) ) {
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
		
		System.out.println("SERVER: ABOUT TO SEND THE NEW FACE UP COUNTERS the number of faceup coutners is: " + faceUpCounters.size());
		
//		Server.serverChannel.setFaceUpCounters(faceUpCounters);
		
		return;
		
	}
	
	
	public void drawRandomCounter() {
		
		System.out.println("SERVER in drawrandomcounter method of game.java");
		
		int rand = (int) (Math.random()*(counterPile.size()));
		Counter counter = counterPile.remove(rand);
		counter.setVisibility(true);
		
		Server.serverChannel.sendChooseCounterCommand(counter);

		System.out.println("SERVER sent the choose counter command!");
		
		currentPlayer.addCounter(counter);
		
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
		return;
	}
	
	public void chooseCounterToKeep(int i, String playerID) {
		
		System.out.println("IN CHOOSE COUTNER TO KEEP COMMAND");
		System.out.println("Player choosing counter to keep: " + playerID);
		//get player
		Player sendingPlayer = getPlayer(playerID);
		//fix 
		if (sendingPlayer == null) {
			return;
		}
		
		//remove all the other counters
		Counter c = sendingPlayer.getCounters().get(i);
				
		for (Counter currentC : sendingPlayer.getCounters()) {
			if (c != currentC && currentC.getCounterKind() != CounterKind.OBSTACLE) {
				sendingPlayer.removeCounter(currentC);
				currentC.setVisibility(false);
				counterPile.add(currentC);
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
	
	public void endTravel() {
		if(currentPlayer.getNumCards() <= 4) {
			passTurn();
		}
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
		passTurn();
	}
	
	public void passTurn() {
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
			if(currentPhase == GamePhase.MOVEBOOT && currentRound == numOfRounds) {
				getWinner();
				return;
			}
			if(nextPhase != null) {
				currentPhase = nextPhase;
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
	}
	
	public void placeCounter(int i, RegionKind kind, TownName source, TownName dest) {
		
		System.out.println("IN PLACE COUNTER!!!");
		
		System.out.println("counter index " +i);
		
		//to make sure it's the same road as instantiated here and not on the other side
		Road r = Road.get(kind, source, dest);
		Counter c = currentPlayer.removeCounterByIndex(i);
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
		return;
	}
	
	public void travelOnRoad(TownName t, ArrayList<Integer> usedCards) {
		currentPlayer.setLocation(t);
		Collections.sort(usedCards);
		
		for(int j = usedCards.size() - 1; j>=0; j--) {
			Integer cardInd = usedCards.get(j);
			int i = cardInd.intValue();
			currentPlayer.removeCardByIndex(i);
		}
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
	
	private Card drawCard(Player p) {
		
		//replenish if empty
		if(cardDeck.isEmpty()) {
			cardDeck = discardPile;
			Collections.shuffle(cardDeck);
			discardPile = new ArrayList<>();
		}
		
		//draw card
		Card topCard = cardDeck.remove(0);
		p.receiveCard(topCard);
		return topCard;
	}
	
	protected void finishRound() {
		
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
				if (c.getCounterKind() != CounterKind.OBSTACLE) {
					counterPile.add(c);
				}
				
			}
		}
		Collections.shuffle(counterPile);
		
		//replenish cards
		for (String id : participants.keySet()) {
			Player p = participants.get(id);
			int numCards = p.getNumCards();
			ArrayList<Card> cardsDist = new ArrayList<Card>();
			
			while (numCards<8) {
				cardsDist.add(this.drawCard(p));
				numCards = p.getNumCards();
			}
			Server.serverChannel.setDistributedCardsAndTC(id, null, cardsDist);
		}
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
				highestPoints = points;
			}
			else if(points == highestPoints) {
				if(winner != null) {
					if (p.getNumCards() > winner.getNumCards()) {
						winner = p;
						highestPoints = points;
					}
				}
				else {
					winner = p;
					highestPoints = points;
				}
			}
		}
		
		Server.serverChannel.sendWinnerCommand(winner.getName());

		return winner;	
	}
	
	public static int shortestPath(TownName t1, TownName t2) {
		HashMap<TownName, Integer> distance = new HashMap<>();
		ArrayList<TownName> toVisit = new ArrayList<>();
		
		for(TownName town : TownName.values()) {
			if (town == t1) {
				distance.put(town, 0);
				toVisit.add(town);
			}
			else {
				distance.put(town, 999);
			}
		}
		
		while(!toVisit.isEmpty()) {
			TownName currentTown = toVisit.remove(0);
			if(distance.get(currentTown) < distance.get(t2)) {
				for(TownName nextTown : townConnections.get(currentTown)) {
					if(distance.get(currentTown) + 1 < distance.get(nextTown)) {
						distance.put(nextTown, (distance.get(currentTown)+1));
						toVisit.add(nextTown);
					}
				}
			}
		}
		return distance.get(t2);
	}
	
	public void drawCard(int i) {
		
	}
	
	public void drawRandomCard() {
		
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
