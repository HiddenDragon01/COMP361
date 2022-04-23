package gamelogic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class Road implements Serializable  {
	final private RegionKind traverses;
	final private TownName sourceTown;
	final private TownName destinationTown;
	final private ArrayList<Counter> counters;
	
	protected static final HashMap<Integer, Road>  ROADS = new HashMap<Integer, Road>();
	
	private Road(RegionKind rkind, TownName sourceTown, TownName destinationTown) {
		this.traverses = rkind;
		this.sourceTown = sourceTown;
		this.destinationTown= destinationTown;
		counters = new ArrayList<Counter>();
	}
	
	public static Road get(RegionKind rkind, TownName sourceTown, TownName destinationTown)
	{
		
		assert ROADS.containsKey(getKey(rkind, sourceTown, destinationTown));
		
		return ROADS.get(getKey(rkind, sourceTown, destinationTown));
	}
	
	private static int getKey(RegionKind rkind, TownName sourceTown, TownName destinationTown)
	{
		return Objects.hash(rkind) + Objects.hash(sourceTown) + Objects.hash(destinationTown);
	}
	
	public boolean exists(RegionKind rkind, TownName sourceTown, TownName destinationTown)
	{
		return ROADS.containsKey(getKey(rkind, sourceTown, destinationTown));
	}
	
	public ArrayList<Counter> getCounters(){
		ArrayList<Counter> returnList = new ArrayList<>(counters);
		return returnList;
	}
	
	public RegionKind getRegionKind() {
		return traverses;
	}
	
	public void removeCounter(Counter c) {
		counters.remove(c);
		return;
	}
	
	public void placeCounter(Counter c) {
		counters.add(c);
		return;
	}
	
	public void placeCounter(int index, Counter c) {
		counters.add(index, c);
		return;
	}
	
	public TownName getSourceTown() {
		return sourceTown;
	}
	
	public TownName getDestinationTown() {
		return destinationTown;
	}
	
	public int getNumCounters() {
		return counters.size();
	}
	
	public static ArrayList<Road> getRoads()
	{
		return new ArrayList<Road>(ROADS.values());
	}
		
	static 
	{
		ROADS.put(getKey(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA), new Road(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA));
		ROADS.put(getKey(RegionKind.RIVER, TownName.BEATA, TownName.ELVENHOLD), new Road(RegionKind.RIVER, TownName.BEATA, TownName.ELVENHOLD));
		ROADS.put(getKey(RegionKind.LAKE, TownName.ELVENHOLD, TownName.STRYKHAVEN), new Road(RegionKind.LAKE, TownName.ELVENHOLD, TownName.STRYKHAVEN));
		ROADS.put(getKey(RegionKind.LAKE, TownName.ELVENHOLD, TownName.VIRST), new Road(RegionKind.LAKE, TownName.ELVENHOLD, TownName.VIRST));
		ROADS.put(getKey(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.LAPPHALIA), new Road(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.LAPPHALIA));
		ROADS.put(getKey(RegionKind.RIVER, TownName.ELVENHOLD, TownName.RIVINIA), new Road(RegionKind.RIVER, TownName.ELVENHOLD, TownName.RIVINIA));
		ROADS.put(getKey(RegionKind.WOODS, TownName.ELVENHOLD, TownName.ERGEREN), new Road(RegionKind.WOODS, TownName.ELVENHOLD, TownName.ERGEREN));
			
		// beata
		ROADS.put(getKey(RegionKind.PLAINS, TownName.BEATA, TownName.STRYKHAVEN), new Road(RegionKind.PLAINS, TownName.BEATA, TownName.STRYKHAVEN));
		
		// strykhaven
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.STRYKHAVEN, TownName.VIRST), new Road(RegionKind.MOUNTAINS, TownName.STRYKHAVEN, TownName.VIRST));
		ROADS.put(getKey(RegionKind.LAKE, TownName.STRYKHAVEN, TownName.VIRST), new Road(RegionKind.LAKE, TownName.STRYKHAVEN, TownName.VIRST));

		// ergeren
		ROADS.put(getKey(RegionKind.WOODS, TownName.ERGEREN, TownName.TICHIH), new Road(RegionKind.WOODS, TownName.ERGEREN, TownName.TICHIH));
		
		// tichih
		ROADS.put(getKey(RegionKind.RIVER, TownName.RIVINIA, TownName.TICHIH), new Road(RegionKind.RIVER, TownName.RIVINIA, TownName.TICHIH));
		ROADS.put(getKey(RegionKind.PLAINS, TownName.TICHIH, TownName.THROTMANNI), new Road(RegionKind.PLAINS, TownName.TICHIH, TownName.THROTMANNI));
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.TICHIH, TownName.JACCARANDA), new Road(RegionKind.MOUNTAINS, TownName.TICHIH, TownName.JACCARANDA));

		// rivinia
		ROADS.put(getKey(RegionKind.WOODS, TownName.RIVINIA, TownName.LAPPHALIA), new Road(RegionKind.WOODS, TownName.RIVINIA, TownName.LAPPHALIA));
		ROADS.put(getKey(RegionKind.WOODS, TownName.RIVINIA, TownName.FEODOR), new Road(RegionKind.WOODS, TownName.RIVINIA, TownName.FEODOR));
		ROADS.put(getKey(RegionKind.WOODS, TownName.RIVINIA, TownName.THROTMANNI), new Road(RegionKind.WOODS, TownName.RIVINIA, TownName.THROTMANNI));

		// virst
		ROADS.put(getKey(RegionKind.PLAINS, TownName.VIRST, TownName.LAPPHALIA), new Road(RegionKind.PLAINS, TownName.VIRST, TownName.LAPPHALIA));
		ROADS.put(getKey(RegionKind.PLAINS, TownName.VIRST, TownName.JXARA), new Road(RegionKind.PLAINS, TownName.VIRST, TownName.JXARA));
		ROADS.put(getKey(RegionKind.RIVER, TownName.VIRST, TownName.JXARA), new Road(RegionKind.RIVER, TownName.VIRST, TownName.JXARA));
		
		// lapphalya
		ROADS.put(getKey(RegionKind.WOODS, TownName.LAPPHALIA, TownName.FEODOR), new Road(RegionKind.WOODS, TownName.LAPPHALIA, TownName.FEODOR));
		ROADS.put(getKey(RegionKind.WOODS, TownName.LAPPHALIA, TownName.DAGAMURA), new Road(RegionKind.WOODS, TownName.LAPPHALIA, TownName.DAGAMURA));
		ROADS.put(getKey(RegionKind.WOODS, TownName.LAPPHALIA, TownName.JXARA), new Road(RegionKind.WOODS, TownName.LAPPHALIA, TownName.JXARA));
		
		// feodor
		ROADS.put(getKey(RegionKind.DESERT, TownName.FEODOR, TownName.THROTMANNI), new Road(RegionKind.DESERT, TownName.FEODOR, TownName.THROTMANNI));
		ROADS.put(getKey(RegionKind.DESERT, TownName.FEODOR, TownName.DAGAMURA), new Road(RegionKind.DESERT, TownName.FEODOR, TownName.DAGAMURA));
		ROADS.put(getKey(RegionKind.DESERT, TownName.FEODOR, TownName.ALBARAN), new Road(RegionKind.DESERT, TownName.FEODOR, TownName.ALBARAN));

		// throtmanni
		ROADS.put(getKey(RegionKind.DESERT, TownName.THROTMANNI, TownName.ALBARAN), new Road(RegionKind.DESERT, TownName.THROTMANNI, TownName.ALBARAN));
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.THROTMANNI, TownName.JACCARANDA), new Road(RegionKind.MOUNTAINS, TownName.THROTMANNI, TownName.JACCARANDA));

		// jaccamanda
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.JACCARANDA, TownName.WYLHIEN), new Road(RegionKind.MOUNTAINS, TownName.JACCARANDA, TownName.WYLHIEN));

		// albaran
		ROADS.put(getKey(RegionKind.DESERT, TownName.ALBARAN, TownName.DAGAMURA), new Road(RegionKind.DESERT, TownName.ALBARAN, TownName.DAGAMURA));
		ROADS.put(getKey(RegionKind.DESERT, TownName.ALBARAN, TownName.PARUNDIA), new Road(RegionKind.DESERT, TownName.ALBARAN, TownName.PARUNDIA));
		ROADS.put(getKey(RegionKind.DESERT, TownName.ALBARAN, TownName.WYLHIEN), new Road(RegionKind.DESERT, TownName.ALBARAN, TownName.WYLHIEN));

		// dagamura
		ROADS.put(getKey(RegionKind.WOODS, TownName.DAGAMURA, TownName.JXARA), new Road(RegionKind.WOODS, TownName.DAGAMURA, TownName.JXARA));
		ROADS.put(getKey(RegionKind.WOODS, TownName.DAGAMURA, TownName.KIHROMAH), new Road(RegionKind.WOODS, TownName.DAGAMURA, TownName.KIHROMAH));
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.DAGAMURA, TownName.MAHDAVIKIA), new Road(RegionKind.MOUNTAINS, TownName.DAGAMURA, TownName.MAHDAVIKIA));

		// jxara
		ROADS.put(getKey(RegionKind.RIVER, TownName.JXARA, TownName.MAHDAVIKIA), new Road(RegionKind.RIVER, TownName.JXARA, TownName.MAHDAVIKIA));
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.JXARA, TownName.MAHDAVIKIA), new Road(RegionKind.MOUNTAINS, TownName.JXARA, TownName.MAHDAVIKIA));

		// mahdavikia
		ROADS.put(getKey(RegionKind.RIVER, TownName.MAHDAVIKIA, TownName.GRANGOR), new Road(RegionKind.RIVER, TownName.MAHDAVIKIA, TownName.GRANGOR));
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.MAHDAVIKIA, TownName.GRANGOR), new Road(RegionKind.MOUNTAINS, TownName.MAHDAVIKIA, TownName.GRANGOR));

		// grangor
		ROADS.put(getKey(RegionKind.MOUNTAINS, TownName.GRANGOR, TownName.YTTAR), new Road(RegionKind.MOUNTAINS, TownName.GRANGOR, TownName.YTTAR));
		ROADS.put(getKey(RegionKind.LAKE, TownName.GRANGOR, TownName.YTTAR), new Road(RegionKind.LAKE, TownName.GRANGOR, TownName.YTTAR));
		ROADS.put(getKey(RegionKind.LAKE, TownName.GRANGOR, TownName.PARUNDIA), new Road(RegionKind.LAKE, TownName.GRANGOR, TownName.PARUNDIA));

		// yttar
		ROADS.put(getKey(RegionKind.WOODS, TownName.YTTAR, TownName.USSELEN), new Road(RegionKind.WOODS, TownName.YTTAR, TownName.USSELEN));
		ROADS.put(getKey(RegionKind.LAKE, TownName.YTTAR, TownName.PARUNDIA), new Road(RegionKind.LAKE, TownName.YTTAR, TownName.PARUNDIA));

		// parundia
		ROADS.put(getKey(RegionKind.WOODS, TownName.PARUNDIA, TownName.USSELEN), new Road(RegionKind.WOODS, TownName.PARUNDIA, TownName.USSELEN));
		ROADS.put(getKey(RegionKind.PLAINS, TownName.PARUNDIA, TownName.WYLHIEN), new Road(RegionKind.PLAINS, TownName.PARUNDIA, TownName.WYLHIEN));
		
		// usselen
		ROADS.put(getKey(RegionKind.RIVER, TownName.WYLHIEN, TownName.USSELEN), new Road(RegionKind.RIVER, TownName.WYLHIEN, TownName.USSELEN));
		ROADS.put(getKey(RegionKind.PLAINS, TownName.WYLHIEN, TownName.USSELEN), new Road(RegionKind.PLAINS, TownName.WYLHIEN, TownName.USSELEN));
	}
}
