package gamelogic;
import java.util.ArrayList;
import java.util.Hashtable;

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
	
	public static boolean placeCounter(Counter c, Road r) {
		//i was thinknig of returning strings with the reason why it doesn't work 
		//but boolean means once you send something and get the confirmation, we could not allow them to send something else
		
		//if road is a river then cannot place a counter
		if(r.getRegionKind() == RegionKind.RIVER) {
			return false;
		}
		
		//if counter is an obstacle, make sure there's one counter on the road
		if(c.getCounterKind() == CounterKind.OBSTACLE) {
			if(r.getNumCounters() == 1) {
				//send message to server
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
				if (transportationChart.get(r.getRegionKind()).contains(((TravelCounter) c).getTransportationKind())){
					//send message to server
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean travelOnRoad(Player p, Road r, ArrayList<TravelCard> cardsUsed) {
		//false if road is not connected to player location
		if (p.getLocation() != r.getSourceTown() && p.getLocation() != r.getDestinationTown()) {
			return false;
		}
		//false if cards are not all the same kind
		TransportationKind cardKind = cardsUsed.get(0).getTransportationKind();
		for (TravelCard c : cardsUsed) {
			if (c.getTransportationKind() != cardKind) {
				return false;
			}
		}
		
		//first check if river
		if(r.getRegionKind() == RegionKind.RIVER) {
			//false if not using raft card(s)
			if(cardKind != TransportationKind.RAFT) {
				return false;
			}
			//true if one card and coming from source town
			if(cardsUsed.size() == 1 && p.getLocation() == r.getSourceTown()) {
				//send message to server
				return true;
			}
			//true if two cards and coming from destination town
			else if(cardsUsed.size() == 2 && p.getLocation() == r.getDestinationTown()) {
				//send message to server
				return true;
			}
			//this is to make sure no river moves make it past here for some weird reason like having three cards or something
			else {
				return false;
			}
		}
		
		//false if no counter on road
		if (r.getNumCounters() == 0) {
			return false;
		}
		//get the first counter, which has to be a travelcounter by placeCounter's logic
		TravelCounter tc = (TravelCounter) r.getCounters().get(0);
		//if the cards do not match the counter
		if(cardKind != tc.getTransportationKind()) {
			//if there are 3 cards (of the same type) and no obstacle
			//or 4 cards (of the same type) and an obstacle
			if((cardsUsed.size() == 3 && r.getNumCounters() == 1) || (cardsUsed.size() == 4 && r.getNumCounters() == 2)) {
				//send message
				return true;
			}
			//if cards do not match counter and there's not enough
			return false;
		}
		
		//look up how many cards are needed for this region with this transportation kind
		//true if that's how many cards and no obstacle
		if((cardsUsed.size() == transportationChart.get(r.getRegionKind()).get(cardKind)) && r.getNumCounters() == 1) {
			//send message
			return true;
		}
		//true if transportation chart number + 1 and there's an obstacle
		if ((cardsUsed.size() == transportationChart.get(r.getRegionKind()).get(cardKind) + 1) && r.getNumCounters() == 2) {
			//send message
			return true;
		}
		
		return false;
	}
	
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
