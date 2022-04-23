package gui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

import gamelogic.BootColor;
import gamelogic.Card;
import gamelogic.CardKind;
import gamelogic.Counter;
import gamelogic.CounterKind;
import gamelogic.ElfengoldGame;
import gamelogic.Game;
import gamelogic.GameVariant;
import gamelogic.Player;
import gamelogic.RegionKind;
import gamelogic.Road;
import gamelogic.TownName;
import gamelogic.TransportationKind;
import gamelogic.TravelCard;
import gamelogic.TravelCounter;

public class Tester {
	
	public static void main(String[] args) throws InterruptedException, NoSuchFieldException, SecurityException {
		
		System.out.println("here");
		
//		GUI_Login login = new GUI_Login();
//		login.setDaemon(true);
//		System.out.println("Starting login thread");
//		login.start();
//		login.join();
//		
//		GUI_Main mainGame = new GUI_Main();
//		mainGame.setDaemon(true);
//		System.out.println("Starting main game thread");
//		mainGame.start();
//		mainGame.join();	
		
		Player p = new Player();
		p.setDestination(TownName.GRANGOR);
		Player p2 = new Player();
		p2.setBoot(BootColor.BLACK);
		Player p3 = new Player();
		p3.setBoot(BootColor.RED);
		p.setBoot(BootColor.BLUE);
		Player p4 = new Player();
		p4.setBoot(BootColor.YELLOW);
		p2.setName("Bea");
		p3.setName("Jifang");
		p4.setName("Alex");
		p.setLocation(TownName.BEATA);
		//		p4.setName("Alex");
		LinkedHashMap<String, Player>  l = new LinkedHashMap<String, Player>();
		l.put("1", p);
		l.put("2", p2);
		l.put("3", p3);
//		l.put("4", p4);
//		Game g = new Game(GameVariant.TOWNCARDS, "hello");
		ElfengoldGame g = new ElfengoldGame(GameVariant.TOWNCARDS, "hello");
		g.setCurrentPlayer(p);
		g.addPlayer("11111", p);
		g.addPlayer("11", p2);
		g.addPlayer("111", p3);
		g.addPlayer("1111", p4);
		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.DRAGON, true));
		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.GIANTPIG, true));
		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.MAGICCLOUD, true));
		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.TROLLWAGON, true));
		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.UNICORN, true));
		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.DRAGON, true));
		p.addCounter((gamelogic.Counter) new Counter(CounterKind.EXCHANGE, true));
		p.addCounter((gamelogic.Counter) new Counter(CounterKind.DOUBLE, true));
		p.addCounter((gamelogic.Counter) new Counter(CounterKind.GOLD, true));
		p.addCounter((gamelogic.Counter) new Counter(CounterKind.SEAMONSTER, true));
		
		p2.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.GIANTPIG, true));
		p2.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.MAGICCLOUD, true));
		p2.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.TROLLWAGON, true));
		p2.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.UNICORN, true));
		p2.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.DRAGON, true));
		p2.addCounter((gamelogic.Counter) new Counter(CounterKind.EXCHANGE, true));
		p2.addCounter((gamelogic.Counter) new Counter(CounterKind.DOUBLE, false));
		p2.addCounter((gamelogic.Counter) new Counter(CounterKind.GOLD, true));
		p2.addCounter((gamelogic.Counter) new Counter(CounterKind.SEAMONSTER, true));
		
		p3.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.GIANTPIG, true));
		p3.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.MAGICCLOUD, true));
		p3.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.TROLLWAGON, true));
		p3.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.UNICORN, true));
		p3.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.DRAGON, true));
		p3.addCounter((gamelogic.Counter) new Counter(CounterKind.EXCHANGE, true));
		p3.addCounter((gamelogic.Counter) new Counter(CounterKind.DOUBLE, false));
		p3.addCounter((gamelogic.Counter) new Counter(CounterKind.GOLD, true));
		p3.addCounter((gamelogic.Counter) new Counter(CounterKind.SEAMONSTER, true));
		
		p4.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.GIANTPIG, true));
		p4.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.MAGICCLOUD, false));
		p4.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.TROLLWAGON, true));
		p4.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.UNICORN, true));
		p4.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.DRAGON, false));
		p4.addCounter((gamelogic.Counter) new Counter(CounterKind.EXCHANGE, true));
		p4.addCounter((gamelogic.Counter) new Counter(CounterKind.DOUBLE, false));
		p4.addCounter((gamelogic.Counter) new Counter(CounterKind.GOLD, true));
		p4.addCounter((gamelogic.Counter) new Counter(CounterKind.SEAMONSTER, false));
		
		
		p.receiveCard(new Card(CardKind.ELVENWITCH));
		p.receiveCard(new TravelCard(TransportationKind.GIANTPIG));
		p.receiveCard(new TravelCard(TransportationKind.MAGICCLOUD));
		p.receiveCard(new TravelCard(TransportationKind.ELFCYCLE));
		p.receiveCard(new TravelCard(TransportationKind.RAFT));
		p.receiveCard(new TravelCard(TransportationKind.UNICORN));
		p.receiveCard(new TravelCard(TransportationKind.TROLLWAGON));
		p.receiveCard(new TravelCard(TransportationKind.DRAGON));
		p.addCounter(new Counter(CounterKind.OBSTACLE, true));
		
		ArrayList<Card> faceupcards = new ArrayList<Card>() ;
		faceupcards.add(new TravelCard(TransportationKind.DRAGON));
		faceupcards.add(new TravelCard(TransportationKind.TROLLWAGON));
		faceupcards.add(new TravelCard(TransportationKind.UNICORN));
//		g.setFaceUpCards(faceupcards);
		
		MinuetoWindow window = new MinuetoFrame(1400, 700, true);
//		GameGUI gui = new GameGUI(g,  p);
		GameGUI gui = new GameGUI_Elfengold(g, p);
//		g.setGoldCardPile(0);
		System.out.println(Road.get(RegionKind.LAKE, TownName.BEATA, TownName.ELVENHOLD) == Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA));
		
		ArrayList<gamelogic.Counter> cs = new ArrayList<>();
		cs.add((gamelogic.Counter) new TravelCounter(TransportationKind.DRAGON, true));
		cs.add((gamelogic.Counter) new TravelCounter(TransportationKind.GIANTPIG, true));
		cs.add((gamelogic.Counter) new TravelCounter(TransportationKind.MAGICCLOUD, true));
		cs.add((gamelogic.Counter) new TravelCounter(TransportationKind.TROLLWAGON, true));
		cs.add((gamelogic.Counter) new TravelCounter(TransportationKind.UNICORN, true));

		g.setFaceUpCounters(cs);
		
		Road r = Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA);
		r.placeCounter(new TravelCounter(TransportationKind.GIANTPIG, true));
		r.placeCounter(new Counter(CounterKind.OBSTACLE, true));
		
		Road r4 = Road.get(RegionKind.MOUNTAINS, TownName.STRYKHAVEN, TownName.VIRST);
		r4.placeCounter(new TravelCounter(TransportationKind.MAGICCLOUD, true));
		
		Road r3 = Road.get(RegionKind.WOODS, TownName.USSELEN, TownName.PARUNDIA);
		r3.placeCounter(new TravelCounter(TransportationKind.GIANTPIG, true));
		
		Road r2 = Road.get(RegionKind.RIVER, TownName.ELVENHOLD, TownName.BEATA);
		r2.placeCounter( new Counter(CounterKind.SEAMONSTER, true));
			gui.start();
//		gui.displayMessage("ERROR!\nYou must choose a road and counter.\nPlease try again.");
//		
//		ChooseBootGUI boot = new ChooseBootGUI(gui);
//		boot.start();
//		boot.join();
//		g.nextRound();
		
			
		// TESTS!!!!!

//		TravelGUI t = new TravelGUI(gui);
//		t.start();
//		t.join();
//		
//			PlaceCounterOnRoadElfenGoldGUI placecounter = new PlaceCounterOnRoadElfenGoldGUI(gui);
//			placecounter.start();
//			placecounter.join();
			
		EndTravelOptionGUI endtra = new EndTravelOptionGUI(gui);
		endtra.start();
		endtra.join();
	
//		Field currentBidders = g.getClass()
//		        .getDeclaredField("currentBidders");
//		currentBidders.setAccessible(true);
//		try {
//			currentBidders.set(g, new ArrayList<String>(Arrays.asList("Shalee", "Bea", "Shalee2", "Bea 2", "H3", "jasdhbgjaldfb")));
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Field currentBids = g.getClass()
//		        .getDeclaredField("currentBids");
//		currentBids.setAccessible(true);
//		try {
//			currentBids.set(g, new ArrayList<Integer>(Arrays.asList(54,45,234,2,22,5,3,2)));
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Field currentAuctionCounter = g.getClass()
//		        .getDeclaredField("currentAuctionCounter");
//		currentAuctionCounter.setAccessible(true);
//		try {
//			currentAuctionCounter.set(g, new Counter(CounterKind.GOLD, true));
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Field auctionPile = g.getClass()
//		        .getDeclaredField("auctionPile");
//		auctionPile.setAccessible(true);
//		try {
//			auctionPile.set(g, new ArrayList<Counter>(Arrays.asList(
//					new Counter(CounterKind.SEAMONSTER, true),
//					new Counter(CounterKind.EXCHANGE, true),
//					new Counter(CounterKind.OBSTACLE, true),
//					new Counter(CounterKind.TRANSPORTATION, true)
//					
//					) ));
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		g.setCurrentPlayer(p2);
//		
//		AuctionGUI auction = new AuctionGUI(gui);
//		auction.start();
//		auction.join();
		
	
//		ChooseCounterToKeepElfengoldGUI de = new ChooseCounterToKeepElfengoldGUI(gui);
//		de.start();
//		de.join();
		
//		ChooseCounterToKeepGUI d = new ChooseCounterToKeepGUI(gui);
//		d.start();
//		d.join();
//		
//		PlaceCounterOnRoadGUI placeCounterOnRoad = new PlaceCounterOnRoadGUI(gui);
//		placeCounterOnRoad.start();
//		placeCounterOnRoad.join();
//		
		ChooseVisibleCounterGUI choosevis = new ChooseVisibleCounterGUI(gui);
		choosevis.start();
		choosevis.join();
		
//		EndTravelOptionGUI endtrav = new EndTravelOptionGUI(gui);
//		endtrav.start();
//		endtrav.join();	
			
//		ChooseCardsToKeepGUI keepCards = new ChooseCardsToKeepGUI(gui);
//		keepCards.start();
//		keepCards.join();
		
//		DrawCounterGUI draw = new DrawCounterGUI(gui);
//		draw.start();
//		draw.join();
//		
//		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.GIANTPIG, true));
//		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.MAGICCLOUD, true));
//		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.TROLLWAGON, true));
//		p.addCounter((gamelogic.Counter) new TravelCounter(TransportationKind.UNICORN, true));
//		
//		NewRoundGUI newRound = new NewRoundGUI(gui);
//		newRound.start();
//		newRound.join();
//		
//		g.nextRound();
//		NewRoundGUI newRound1 = new NewRoundGUI(gui);
//		newRound1.start();
//		newRound1.join();
//		
//		MoveBootGUI moveboot = new MoveBootGUI(gui, new Point(874, 300), new Point(701, 365));
//		moveboot.start();
//		moveboot.join();
		
		
//		DrawCardGUI drawcard = new DrawCardGUI(gui);
//		drawcard.start();
//		drawcard.join();
			
//		WinnerGUI win = new WinnerGUI(gui, "a");
//		win.start();
//		win.join();
	}
	
}
