package gamelogic;

import java.io.Serializable;
import java.util.HashMap;

public enum TownName implements Serializable {
	BEATA,
	ERGEREN,
	GRANGOR,
	JACCARANDA,
	JXARA,
	MAHDAVIKIA,
	STRYKHAVEN,
	TICHIH,
	USSELEN,
	WYLHIEN,
	VIRST,
	YTTAR,
	ALBARAN,
	KIHROMAH,
	DAGAMURA,
	PARUNDIA,
	FEODOR,
	THROTMANNI,
	RIVINIA,
	LAPPHALIA,
	ELVENHOLD;
	
	private int goldValue;
	final private HashMap<TownName, Road> connectedRoads = new HashMap<>();
	final private HashMap<TownName, Road> connectedRivers = new HashMap<>();
	
	static 
	{
		ALBARAN.assignGoldValue(7);
		KIHROMAH.assignGoldValue(6);
		GRANGOR.assignGoldValue(5);
		MAHDAVIKIA.assignGoldValue(5);
		JACCARANDA.assignGoldValue(5);
		ERGEREN.assignGoldValue(5);
		STRYKHAVEN.assignGoldValue(4);
		DAGAMURA.assignGoldValue(4);
		YTTAR.assignGoldValue(4);
		PARUNDIA.assignGoldValue(4);
		USSELEN.assignGoldValue(4);
		FEODOR.assignGoldValue(4);
		VIRST.assignGoldValue(3);
		JXARA.assignGoldValue(3);
		THROTMANNI.assignGoldValue(3);
		TICHIH.assignGoldValue(3);
		RIVINIA.assignGoldValue(3);
		WYLHIEN.assignGoldValue(3);
		BEATA.assignGoldValue(2);
		LAPPHALIA.assignGoldValue(2);
		ELVENHOLD.assignGoldValue(0);
		//elvenhold
		Road r1 = Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA);
		TownName.ELVENHOLD.addConnectedRoad(TownName.BEATA, r1);
		TownName.BEATA.addConnectedRoad(TownName.ELVENHOLD, r1);
		Road r2 = Road.get(RegionKind.RIVER, TownName.BEATA, TownName.ELVENHOLD);
		TownName.ELVENHOLD.addConnectedRiver(TownName.BEATA, r2);
		TownName.BEATA.addConnectedRiver(TownName.ELVENHOLD, r2);
		Road r3 = Road.get(RegionKind.LAKE, TownName.ELVENHOLD, TownName.STRYKHAVEN);
		TownName.ELVENHOLD.addConnectedRiver(TownName.STRYKHAVEN, r3);
		TownName.STRYKHAVEN.addConnectedRiver(TownName.ELVENHOLD, r3);
		Road r4 = Road.get(RegionKind.LAKE, TownName.ELVENHOLD, TownName.VIRST);
		TownName.ELVENHOLD.addConnectedRiver(TownName.VIRST, r4);
		TownName.VIRST.addConnectedRiver(TownName.ELVENHOLD, r4);
		Road r5 = Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.LAPPHALIA);
		TownName.ELVENHOLD.addConnectedRoad(TownName.LAPPHALIA, r5);
		TownName.LAPPHALIA.addConnectedRoad(TownName.ELVENHOLD, r5);
		Road r6 =  Road.get(RegionKind.RIVER, TownName.ELVENHOLD, TownName.RIVINIA);
		TownName.ELVENHOLD.addConnectedRiver(TownName.RIVINIA, r6);
		TownName.RIVINIA.addConnectedRiver(TownName.ELVENHOLD, r6);
		Road r7 = Road.get(RegionKind.WOODS, TownName.ELVENHOLD, TownName.ERGEREN);
		TownName.ELVENHOLD.addConnectedRoad(TownName.ERGEREN, r7);
		TownName.ERGEREN.addConnectedRoad(TownName.ELVENHOLD, r7);
			
		// beata
		Road r8 = Road.get(RegionKind.PLAINS, TownName.BEATA, TownName.STRYKHAVEN);
		TownName.BEATA.addConnectedRoad(TownName.STRYKHAVEN, r8);
		TownName.STRYKHAVEN.addConnectedRoad(TownName.BEATA, r8);
		
		// strykhaven
		Road r9 = Road.get(RegionKind.MOUNTAINS, TownName.STRYKHAVEN, TownName.VIRST);
		TownName.VIRST.addConnectedRoad(TownName.STRYKHAVEN, r9);
		TownName.STRYKHAVEN.addConnectedRoad(TownName.VIRST, r9);
		Road r10 = Road.get(RegionKind.LAKE, TownName.STRYKHAVEN, TownName.VIRST);
		TownName.STRYKHAVEN.addConnectedRiver(TownName.VIRST, r10);
		TownName.VIRST.addConnectedRiver(TownName.STRYKHAVEN, r10);

		// ergeren
		Road r11 = Road.get(RegionKind.WOODS, TownName.ERGEREN, TownName.TICHIH);
		TownName.ERGEREN.addConnectedRoad(TownName.TICHIH, r11);
		TownName.TICHIH.addConnectedRoad(TownName.ERGEREN, r11);
		
		// tichih
		Road r12 = Road.get(RegionKind.RIVER, TownName.RIVINIA, TownName.TICHIH);
		TownName.RIVINIA.addConnectedRiver(TownName.TICHIH, r12);
		TownName.TICHIH.addConnectedRiver(TownName.RIVINIA, r12);
		Road r13 = Road.get(RegionKind.PLAINS, TownName.TICHIH, TownName.THROTMANNI);
		TownName.THROTMANNI.addConnectedRoad(TownName.TICHIH, r13);
		TownName.TICHIH.addConnectedRoad(TownName.THROTMANNI, r13);
		Road r14 = Road.get(RegionKind.MOUNTAINS, TownName.TICHIH, TownName.JACCARANDA);
		TownName.JACCARANDA.addConnectedRoad(TownName.TICHIH, r14);
		TownName.TICHIH.addConnectedRoad(TownName.JACCARANDA, r14);

		// rivinia
		Road r15 = Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.LAPPHALIA);
		TownName.RIVINIA.addConnectedRoad(TownName.LAPPHALIA, r15);
		TownName.LAPPHALIA.addConnectedRoad(TownName.RIVINIA, r15);
		Road r16 = Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.FEODOR);
		TownName.RIVINIA.addConnectedRoad(TownName.FEODOR, r16);
		TownName.FEODOR.addConnectedRoad(TownName.RIVINIA, r16);
		Road r17 = Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.THROTMANNI);
		TownName.RIVINIA.addConnectedRoad(TownName.THROTMANNI, r17);
		TownName.THROTMANNI.addConnectedRoad(TownName.RIVINIA, r17);

		// virst
		Road r18 = Road.get(RegionKind.PLAINS, TownName.VIRST, TownName.LAPPHALIA);
		TownName.VIRST.addConnectedRoad(TownName.LAPPHALIA, r18);
		TownName.LAPPHALIA.addConnectedRoad(TownName.VIRST, r18);
		Road r19 = Road.get(RegionKind.PLAINS, TownName.VIRST, TownName.JXARA);
		TownName.VIRST.addConnectedRoad(TownName.JXARA, r19);
		TownName.JXARA.addConnectedRoad(TownName.VIRST, r19);
		Road r20 = Road.get(RegionKind.RIVER, TownName.VIRST, TownName.JXARA);
		TownName.VIRST.addConnectedRiver(TownName.JXARA, r20);
		TownName.JXARA.addConnectedRiver(TownName.VIRST, r20);
		
		// lapphalya
		Road r21 = Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.FEODOR);
		TownName.LAPPHALIA.addConnectedRoad(TownName.FEODOR, r21);
		TownName.FEODOR.addConnectedRoad(TownName.LAPPHALIA, r21);
		Road r22 = Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.DAGAMURA);
		TownName.LAPPHALIA.addConnectedRoad(TownName.DAGAMURA, r22);
		TownName.DAGAMURA.addConnectedRoad(TownName.LAPPHALIA, r22);
		Road r23 = Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.JXARA);
		TownName.LAPPHALIA.addConnectedRoad(TownName.JXARA, r23);
		TownName.JXARA.addConnectedRoad(TownName.LAPPHALIA, r23);
		
		// feodor
		Road r24 = Road.get(RegionKind.DESERT, TownName.FEODOR, TownName.THROTMANNI);
		TownName.FEODOR.addConnectedRoad(TownName.THROTMANNI, r24);
		TownName.THROTMANNI.addConnectedRoad(TownName.FEODOR, r24);
		Road r25 = Road.get(RegionKind.DESERT, TownName.FEODOR, TownName.DAGAMURA);
		TownName.FEODOR.addConnectedRoad(TownName.DAGAMURA, r25);
		TownName.DAGAMURA.addConnectedRoad(TownName.FEODOR, r25);
		Road r26 = Road.get(RegionKind.DESERT, TownName.FEODOR, TownName.ALBARAN);
		TownName.FEODOR.addConnectedRoad(TownName.ALBARAN, r26);
		TownName.ALBARAN.addConnectedRoad(TownName.FEODOR, r26);

		// throtmanni
		Road r27 = Road.get(RegionKind.DESERT, TownName.THROTMANNI, TownName.ALBARAN);
		TownName.THROTMANNI.addConnectedRoad(TownName.ALBARAN, r27);
		TownName.ALBARAN.addConnectedRoad(TownName.THROTMANNI, r27);
		Road r28 = Road.get(RegionKind.MOUNTAINS, TownName.THROTMANNI, TownName.JACCARANDA);
		TownName.THROTMANNI.addConnectedRoad(TownName.JACCARANDA, r28);
		TownName.JACCARANDA.addConnectedRoad(TownName.THROTMANNI, r28);

		// jaccamanda
		Road r48 = Road.get(RegionKind.MOUNTAINS, TownName.JACCARANDA, TownName.WYLHIEN);
		TownName.WYLHIEN.addConnectedRoad(TownName.JACCARANDA, r48);
		TownName.JACCARANDA.addConnectedRoad(TownName.WYLHIEN, r48);

		// albaran
		Road r29 = Road.get(RegionKind.DESERT, TownName.ALBARAN, TownName.DAGAMURA);
		TownName.ALBARAN.addConnectedRoad(TownName.DAGAMURA, r29);
		TownName.DAGAMURA.addConnectedRoad(TownName.ALBARAN, r29);
		Road r30 = Road.get(RegionKind.DESERT, TownName.ALBARAN, TownName.PARUNDIA);
		TownName.ALBARAN.addConnectedRoad(TownName.PARUNDIA, r30);
		TownName.PARUNDIA.addConnectedRoad(TownName.ALBARAN, r30);
		Road r31 = Road.get(RegionKind.DESERT, TownName.ALBARAN, TownName.WYLHIEN);
		TownName.WYLHIEN.addConnectedRoad(TownName.ALBARAN, r31);
		TownName.ALBARAN.addConnectedRoad(TownName.WYLHIEN, r31);
		
		// dagamura
		Road r32 = Road.get(RegionKind.WOODS, TownName.DAGAMURA, TownName.JXARA);
		TownName.DAGAMURA.addConnectedRoad(TownName.JXARA, r32);
		TownName.JXARA.addConnectedRoad(TownName.DAGAMURA, r32);
		Road r33 = Road.get(RegionKind.WOODS, TownName.DAGAMURA, TownName.KIHROMAH);
		TownName.DAGAMURA.addConnectedRoad(TownName.KIHROMAH, r33);
		TownName.KIHROMAH.addConnectedRoad(TownName.DAGAMURA, r33);
		Road r34 = Road.get(RegionKind.MOUNTAINS, TownName.DAGAMURA, TownName.MAHDAVIKIA);
		TownName.DAGAMURA.addConnectedRoad(TownName.MAHDAVIKIA, r34);
		TownName.MAHDAVIKIA.addConnectedRoad(TownName.DAGAMURA, r34);

		// jxara
		Road r35= Road.get(RegionKind.RIVER, TownName.JXARA, TownName.MAHDAVIKIA);
		TownName.JXARA.addConnectedRiver(TownName.MAHDAVIKIA, r35);
		TownName.MAHDAVIKIA.addConnectedRiver(TownName.JXARA, r35);
		Road r36 = Road.get(RegionKind.MOUNTAINS, TownName.JXARA, TownName.MAHDAVIKIA);
		TownName.JXARA.addConnectedRoad(TownName.MAHDAVIKIA, r36);
		TownName.MAHDAVIKIA.addConnectedRoad(TownName.JXARA, r36);

		// mahdavikia
		Road r37 =Road.get(RegionKind.RIVER, TownName.MAHDAVIKIA, TownName.GRANGOR);
		TownName.GRANGOR.addConnectedRiver(TownName.MAHDAVIKIA, r37);
		TownName.MAHDAVIKIA.addConnectedRiver(TownName.GRANGOR, r37);
		Road r38 = Road.get(RegionKind.MOUNTAINS, TownName.MAHDAVIKIA, TownName.GRANGOR);
		TownName.GRANGOR.addConnectedRoad(TownName.MAHDAVIKIA, r38);
		TownName.MAHDAVIKIA.addConnectedRoad(TownName.GRANGOR, r38);

		// grangor
		Road r39 = Road.get(RegionKind.MOUNTAINS, TownName.GRANGOR, TownName.YTTAR);
		TownName.GRANGOR.addConnectedRoad(TownName.YTTAR, r39);
		TownName.YTTAR.addConnectedRoad(TownName.GRANGOR, r39);
		Road r40 = Road.get(RegionKind.LAKE, TownName.GRANGOR, TownName.YTTAR);
		TownName.GRANGOR.addConnectedRiver(TownName.YTTAR, r40);
		TownName.YTTAR.addConnectedRiver(TownName.GRANGOR, r40);
		Road r41 = Road.get(RegionKind.LAKE, TownName.GRANGOR, TownName.PARUNDIA);
		TownName.GRANGOR.addConnectedRiver(TownName.PARUNDIA, r41);
		TownName.PARUNDIA.addConnectedRiver(TownName.GRANGOR, r41);

		// yttar
		Road r42 =Road.get(RegionKind.WOODS, TownName.YTTAR, TownName.USSELEN);
		TownName.USSELEN.addConnectedRoad(TownName.YTTAR, r42);
		TownName.YTTAR.addConnectedRoad(TownName.USSELEN, r42);
		Road r43= Road.get(RegionKind.LAKE, TownName.YTTAR, TownName.PARUNDIA);
		TownName.YTTAR.addConnectedRiver(TownName.PARUNDIA, r43);
		TownName.PARUNDIA.addConnectedRiver(TownName.YTTAR, r43);

		// parundia
		Road r44 = Road.get(RegionKind.WOODS, TownName.PARUNDIA, TownName.USSELEN);
		TownName.USSELEN.addConnectedRoad(TownName.PARUNDIA, r44);
		TownName.PARUNDIA.addConnectedRoad(TownName.USSELEN, r44);
		Road r45 = Road.get(RegionKind.PLAINS, TownName.PARUNDIA, TownName.WYLHIEN);
		TownName.PARUNDIA.addConnectedRoad(TownName.WYLHIEN, r45);
		TownName.WYLHIEN.addConnectedRoad(TownName.PARUNDIA, r45);
		
		// usselen
		Road r46 = Road.get(RegionKind.RIVER, TownName.WYLHIEN, TownName.USSELEN);
		TownName.WYLHIEN.addConnectedRiver(TownName.USSELEN, r46);
		TownName.USSELEN.addConnectedRiver(TownName.WYLHIEN, r46);
		Road r47 = Road.get(RegionKind.PLAINS, TownName.WYLHIEN, TownName.USSELEN);
		TownName.USSELEN.addConnectedRoad(TownName.WYLHIEN, r47);
		TownName.WYLHIEN.addConnectedRoad(TownName.USSELEN, r47);
	}
	
	public void addConnectedRoad(TownName t, Road r) {
		connectedRoads.put(t,r);
		return;
	}
	
	public void addConnectedRiver(TownName t, Road r) {
		connectedRivers.put(t,r);
		return;
	}
	
	public boolean hasRoad(TownName t) {
		return connectedRoads.containsKey(t);
	}
	
	public boolean hasRiver(TownName t) {
		return connectedRivers.containsKey(t);
	}
	
	public Road getRoad(TownName t) {
		return connectedRoads.get(t);
	}
	
	public Road getRiver(TownName t) {
		return connectedRivers.get(t);
	}
	
	public HashMap<TownName, Road> getAllRoads() {
		return new HashMap<>(connectedRoads);
	}
	
	public HashMap<TownName, Road> getAllRivers() {
		return new HashMap<>(connectedRivers);
	}
	
	public void assignGoldValue(int value) {
		this.goldValue = value;
	}
	
	public int getGoldValue() {
		return goldValue;
	}
	
}
