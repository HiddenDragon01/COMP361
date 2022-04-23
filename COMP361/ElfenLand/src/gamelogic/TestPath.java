package gamelogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

public class TestPath {
	
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
	
	public static void main(String[] args) {
		
		for (TownName t : TownName.values()) {
			for(TownName t2 : townConnections.get(t)) {
				if (!townConnections.get(t2).contains(t)) {
					System.out.println(t.toString() + t2);
				}
			}
		}
		
		System.out.println(shortestPath(TownName.USSELEN, TownName.RIVINIA));
		
		for(TownName t : TownName.values()) {
			for(TownName t2 : TownName.values()) {
				if(t.getAllRoads().containsKey(t2)) {
					if(!t2.getAllRoads().containsKey(t)) {
						System.out.println(t.toString() + t2);
					}
				}
			}
		}
		for(TownName t : TownName.values()) {
			for(TownName t2 : TownName.values()) {
				if(t.getAllRivers().containsKey(t2)) {
					if(!t2.getAllRivers().containsKey(t)) {
						System.out.println(t.toString() + t2);
					}
				}
			}
		}
		for (TownName t : TownName.values()) {
			for(TownName t2 : townConnections.get(t)) {
				if (t.getAllRivers().get(t2) != t2.getAllRivers().get(t)) {
					System.out.println(t.toString() + t2 + t.getAllRoads().containsKey(t2) + t.getAllRivers().containsKey(t2));
				}
			}
		}
		
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
	

		
}
