package gamelogic;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class MoveValidator {
	private Player thisPlayer;
	private Player currentPlayer;
	//transportation chart where you look up by region then by transportation kind to get the number of cards
	private static Hashtable<RegionKind, Hashtable<TransportationKind, Integer>> transportationChart = 
			new Hashtable<RegionKind, Hashtable<TransportationKind,Integer>>();
	static {
		Hashtable<TransportationKind, Integer> travelPlains = 
			new Hashtable<TransportationKind, Integer>();
		travelPlains.put(TransportationKind.GIANTPIG, 1);
		travelPlains.put(TransportationKind.ELFCYCLE, 1);
		travelPlains.put(TransportationKind.MAGICCLOUD, 2);
		travelPlains.put(TransportationKind.TROLLWAGON, 1);
		travelPlains.put(TransportationKind.DRAGON, 1);
		transportationChart.put(RegionKind.PLAINS, travelPlains);
		Hashtable<TransportationKind, Integer> travelWoods = 
			new Hashtable<TransportationKind, Integer>();
		travelWoods.put(TransportationKind.GIANTPIG, 1);
		travelWoods.put(TransportationKind.ELFCYCLE, 1);
		travelWoods.put(TransportationKind.MAGICCLOUD, 2);
		travelWoods.put(TransportationKind.TROLLWAGON, 2);
		travelWoods.put(TransportationKind.DRAGON, 2);
		travelWoods.put(TransportationKind.UNICORN, 1);
		transportationChart.put(RegionKind.WOODS, travelWoods);
		Hashtable<TransportationKind, Integer> travelDesert = 
			new Hashtable<TransportationKind, Integer>();
		travelDesert.put(TransportationKind.UNICORN, 2);
		travelDesert.put(TransportationKind.TROLLWAGON, 2);
		travelDesert.put(TransportationKind.DRAGON, 1);
		transportationChart.put(RegionKind.DESERT, travelDesert);
		Hashtable<TransportationKind, Integer> travelMountains = 
			new Hashtable<TransportationKind, Integer>();
		travelMountains.put(TransportationKind.ELFCYCLE, 2);
		travelMountains.put(TransportationKind.MAGICCLOUD, 1);
		travelMountains.put(TransportationKind.TROLLWAGON, 2);
		travelMountains.put(TransportationKind.DRAGON, 1);
		travelMountains.put(TransportationKind.UNICORN, 1);
		transportationChart.put(RegionKind.MOUNTAINS, travelMountains);
	}
	
	public static boolean chooseBoot(Player p, BootColor c) {
		if (! p.hasBoot()) {
			return true;
		}
		return false;
	}
	
	// we can have the mouse handler only active if current player and if its the appropriate phase
	//but still have this so that once it returns true, we block the gui from sending a move
	public static boolean drawRandomCounter() {
		//send message
		return true;
	}
	
	//same for draw counter 
	public static boolean drawCounter(Counter c) {
		//send message
		return true;
	}
	
	//check if bid is higher than zero and higher than current bid
	//in GUI please pass Game.getCurrentBid()
	public static boolean placeBid(int proposedBid, int currentBid) {
		if(proposedBid > 0 && proposedBid > currentBid) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean placeCounter(Counter c, Road r) {
		//i was thinknig of returning strings with the reason why it doesn't work 
		
		//if road is a river or lake then can only place sea monster if no counter
		if(r.getRegionKind() == RegionKind.RIVER || r.getRegionKind() == RegionKind.LAKE) {
			if (r.getNumCounters() == 0 && c.getCounterKind() == CounterKind.SEAMONSTER) {
				return true;
			}
			else {
				return false;
			}
		}
		
		
		//if counter is an obstacle, make sure there's a transportation counter but no gold counter on the road
		//if counter is a gold piece, make sure there's a transportation counter but no obstacle on the road

		if(c.getCounterKind() == CounterKind.OBSTACLE || c.getCounterKind()==CounterKind.GOLD) {
			if(r.getNumCounters() == 0) {
				return false;
			}
			for (Counter tempCounter : r.getCounters()) {
				if (tempCounter.getCounterKind() == CounterKind.GOLD || tempCounter.getCounterKind() == CounterKind.OBSTACLE) {
					return false;
				}
			}
			return true;
		}
		
		
		//to place a double travel or exchange, there must be at least a travel counter already
		if(c.getCounterKind()==CounterKind.DOUBLE || c.getCounterKind()==CounterKind.EXCHANGE) {
				if(r.getNumCounters()>=1) {
					return true;
				}
				else {
					return false;
				}
		}
		
		//otherwise make sure there are no counters on the road
		else if (c instanceof TravelCounter){
			if(r.getNumCounters() == 0) {
				//check that that kind of counter is ok for the road region kind
				//get the hashtable for regionkind and check if it includes the counter's transportation kind as a key then u can place the counter
				if (transportationChart.get(r.getRegionKind()).containsKey(((TravelCounter) c).getTransportationKind())){
					//send message to server
					return true;
				}
			}
		}
		return false;
	}

	public static boolean placeCounterElfenGold(ArrayList<Counter> c, Road r) {
		//i was thinknig of returning strings with the reason why it doesn't work 
		int trueCount = 0;
		if (c.size() > 2) {
			return false;
		}
		for (Counter counter:c ) {
			System.out.println(counter.getCounterKind());
			if (counter.getCounterKind()!=CounterKind.TRANSPORTATION) {
				if (placeCounter(counter,r)) {
					trueCount += 1;
				}
			}else if (counter instanceof TravelCounter){
				if (transportationChart.get(r.getRegionKind()).containsKey(((TravelCounter) counter).getTransportationKind())){
					//send message to server
					trueCount +=1;
				}
			}
		}
		if (trueCount == c.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean exchangeRoadTravelCounters(Road r1, Road r2, Counter c1, Counter c2) {
		//make sure they both have a travel counter
		if(r1.getNumCounters() >= 1 &&  r2.getNumCounters()>=1) {
			if(c1 instanceof TravelCounter && c2 instanceof TravelCounter) {
				//check if the travel counter of r2 is good for the region of r1
				if(transportationChart.get(r1.getRegionKind()).containsKey(((TravelCounter) (c2)).getTransportationKind())) {
					//check if the travel counter of r1 is good for the region of r2
					if(transportationChart.get(r2.getRegionKind()).containsKey(((TravelCounter) c2).getTransportationKind())) {
						return true;
					}		
				}
			}
		}
		return false;
	}
	
	//pre-condition : should only be called after placing a double counter has been validated
	//		and should be the same road as when it was validated
	public static boolean placeDoubleCounter(Counter c, Road r) {
		//make sure counter is a travel counter and not the same type as the first one
		if(c instanceof TravelCounter) {
			if (r.getNumCounters() >= 1 
					&& ((TravelCounter) r.getCounters().get(0)).getTransportationKind() != ((TravelCounter) c).getTransportationKind()
					&& transportationChart.get(r.getRegionKind()).containsKey(((TravelCounter) c).getTransportationKind())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean takeGoldDeck(Player p) {
		if(p.getDrewGoldCard()) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean travelOnRoad(Player p, TownName t, ArrayList<Card> cardsUsed) {
		//return if no cards first of all
		if(cardsUsed.isEmpty()) {
			System.out.println("no cards selected");
			return false;
		}
		//or no town name
		if(t == null) {
			System.out.println("no town selected");
			return false;
		}
		
		//elven witch - any town, as long as its one elven with card and player can pay 3 gold
		if(cardsUsed.size() == 1 && cardsUsed.get(0).getCardKind() == CardKind.ELVENWITCH) {
			if(p.getGoldCoins() >= 3) {
				return true;
			}
			else {
				return false;
			}
		}
		
		System.out.println("Right before caravan");
		
		//CARAVAN - three or four cards and only used on roads (not water)
		if(cardsUsed.size() >= 3 && p.getLocation().hasRoad(t)) {
			//if no obstacle (less than two counters) and used 3 cards
			if(p.getLocation().getRoad(t).getNumCounters() < 2) {
				if(cardsUsed.size() == 3) {
					return true;
				}
			}
			//or true if there's an obstacle (2 counters) and four cards are used
			else {
				if (cardsUsed.size() == 4) {
					return true;
				}
			}
		}
		
		//from here on, it's false if cards are not all the same kind - unless one is elvenwitch
		boolean hasElvenWitch = false;
		
		TransportationKind cardKind = null;
		
		for (Card c : cardsUsed) {
			if (c instanceof TravelCard) {
				if(cardKind == null) {
					cardKind = ((TravelCard) c).getTransportationKind();
					continue;
				}
				else {
					if (((TravelCard) c).getTransportationKind() != cardKind) {
						System.out.println("Cards don't match and not caravan");
						return false;
					}
				}
			}
			else if (c.getCardKind() == CardKind.ELVENWITCH) {
				//i've decided you can only play one elven witch
				if(hasElvenWitch) {
					return false;
				}
				else {
					hasElvenWitch = true;
					continue;
				}
			}
			//if it's not a travel card and it's not an elvenwitch then idk what it is
			else {
				return false;
			}
		}
		
		//if they're raft kind then do the water route process
		if(cardKind == TransportationKind.RAFT) {
			//check if there's a water route connecting their location and this town
			if(p.getLocation().hasRiver(t)) {
				//get that path
				Road r = p.getLocation().getRiver(t);
				//if only one card
				if(cardsUsed.size() == 1) {
					//true if road is a river, it's coming from source town, and there's no sea monster (and no elvenwitch
					if(p.getLocation() == r.getSourceTown() && r.getRegionKind() == RegionKind.RIVER && r.getNumCounters() == 0 && !hasElvenWitch) {
						return true;
					}
				}
				//if two cards 
				else if(cardsUsed.size() == 2) {
					//true if coming from river destination town with no sea monster (and no elvenwitch)
					if (r.getRegionKind() == RegionKind.RIVER && p.getLocation() == r.getDestinationTown()  && r.getNumCounters() == 0 && !hasElvenWitch) {
						return true;
					}
					//true if coming from river source town with sea monster
					else if(r.getRegionKind() == RegionKind.RIVER && p.getLocation() == r.getSourceTown() && r.getNumCounters() == 1) {
						//has to have enough to pay if using elvenwitch
						if(hasElvenWitch) {
							if(p.getGoldCoins()>=1) {
								return true;
							}
							else {
								return false;
							}
						}
						//if no elvenwitch, return true
						return true;
					}
					//true if lake with no sea monster (and no elvenwitch)
					else if(r.getRegionKind() == RegionKind.LAKE && r.getNumCounters()==0 && !hasElvenWitch) {
						return true;
					}
				}
				//if three cards
				else if (cardsUsed.size() == 3) {
					//true if lake with sea monster 
					if(r.getRegionKind() == RegionKind.LAKE && r.getNumCounters() == 1) {
						//make sure can pay with elvenwitch
						if(hasElvenWitch) {
							if(p.getGoldCoins()>=1) {
								return true;
							}
							else {
								return false;
							}
						}
						//otherwise no elvenwitch, all good
						return true;
					}
				}
			}
			//this is to make sure no raft card moves make it past here for some weird reason
			else {
				return false;
			}
		}
		
		System.out.println("Is there a road?");
		//otherwise check if there's a road between towns
		if(p.getLocation().hasRoad(t)) {
			System.out.println("There is a road");
			//get road
			Road r = p.getLocation().getRoad(t);
			//false if no counter on road
			if (r.getNumCounters() == 0) {
				System.out.println("There's no counter");
				return false;
			}
			boolean hasObstacle = false;
			for(Counter c : r.getCounters()) {
				if (c.getCounterKind() == CounterKind.OBSTACLE) {
					hasObstacle = true;
				}
			}
			//get the travel counter (can have multiple)
			for (Counter c : r.getCounters()) {
				if(c instanceof TravelCounter) {
					//if the cards do not match the counter, continue to next counter
					if (cardKind != ((TravelCounter) c).getTransportationKind()) {
						continue;
					}
					//look up how many cards are needed for this region with this transportation kind
					//true if that's how many cards and no obstacle and no elvenwitch
					if(cardsUsed.size() == transportationChart.get(r.getRegionKind()).get(cardKind) && !hasObstacle && !hasElvenWitch){
						return true;
					}
					//true if transportation chart number + 1 and there's an obstacle
					if ((cardsUsed.size() == transportationChart.get(r.getRegionKind()).get(cardKind) + 1) && hasObstacle) {
						//check if can pay if using elvenwitch
						if(hasElvenWitch) {
							if(p.getGoldCoins()>=1) {
								return true;
							}
							else {
								return false;
							}
						}
						//all good if not using elvenwitch
						return true;
					}
				}
				System.out.println("it was bad for counter kind "+ c.getCounterKind());
			}
		}
		return false;
	}

		
		
		
		
	
	//Commented out so we could implement this with town name instead
	//But please keep in case we want both or something
//	public static boolean travelOnRoad(Player p, Road r, ArrayList<TravelCard> cardsUsed) {
//		//false if road is not connected to player location
//		if (p.getLocation() != r.getSourceTown() && p.getLocation() != r.getDestinationTown()) {
//			return false;
//		}
//		//false if cards are not all the same kind
//		TransportationKind cardKind = cardsUsed.get(0).getTransportationKind();
//		for (TravelCard c : cardsUsed) {
//			if (c.getTransportationKind() != cardKind) {
//				return false;
//			}
//		}
//		
//		//first check if river
//		if(r.getRegionKind() == RegionKind.RIVER) {
//			//false if not using raft card(s)
//			if(cardKind != TransportationKind.RAFT) {
//				return false;
//			}
//			//true if one card and coming from source town
//			if(cardsUsed.size() == 1 && p.getLocation() == r.getSourceTown()) {
//				//send message to server
//				return true;
//			}
//			//true if two cards and coming from destination town
//			else if(cardsUsed.size() == 2 && p.getLocation() == r.getDestinationTown()) {
//				//send message to server
//				return true;
//			}
//			//this is to make sure no river moves make it past here for some weird reason like having three cards or something
//			else {
//				return false;
//			}
//		}
//		
//		//false if no counter on road
//		if (r.getNumCounters() == 0) {
//			return false;
//		}
//		//get the first counter, which has to be a travelcounter by placeCounter's logic
//		TravelCounter tc = (TravelCounter) r.getCounters().get(0);
//		//if the cards do not match the counter
//		if(cardKind != tc.getTransportationKind()) {
//			//if there are 3 cards (of the same type) and no obstacle
//			//or 4 cards (of the same type) and an obstacle
//			if((cardsUsed.size() == 3 && r.getNumCounters() == 1) || (cardsUsed.size() == 4 && r.getNumCounters() == 2)) {
//				//send message
//				return true;
//			}
//			//if cards do not match counter and there's not enough
//			return false;
//		}
//		
//		//look up how many cards are needed for this region with this transportation kind
//		//true if that's how many cards and no obstacle
//		if((cardsUsed.size() == transportationChart.get(r.getRegionKind()).get(cardKind)) && r.getNumCounters() == 1) {
//			//send message
//			return true;
//		}
//		//true if transportation chart number + 1 and there's an obstacle
//		if ((cardsUsed.size() == transportationChart.get(r.getRegionKind()).get(cardKind) + 1) && r.getNumCounters() == 2) {
//			//send message
//			return true;
//		}
//		
//		return false;
//	}
	
	//same logic as with draw counter
	public static boolean passTurn() {
		//send message
		return true;
	}
	
	//also same
	public static boolean chosenCounterToKeep(Counter c) {
		//send message
		return true;
	}
	
}
