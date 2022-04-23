package gui;

import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

import gamelogic.BootColor;
import gamelogic.ElfengoldGame;
import gamelogic.Game;
import gamelogic.GamePhase;
import gamelogic.GameVariant;
import gamelogic.Player;
import gamelogic.TownName;
import gamelogic.TransportationKind;
import gamelogic.TravelCard;
import gamelogic.TravelCounter;

public class Tester2 {
	
	public static Game g = new ElfengoldGame(GameVariant.REGULAR, "hello"); 
	public static Player p = new Player();
	
	public static void main(String[] args) throws InterruptedException {
		
		p.setBoot(BootColor.PURPLE);
		p.setName("Shalee");
		Player p2 = new Player();
		p2.setName("Player 2");
		p2.setBoot(BootColor.BLACK);
//		Player p3 = new Player();
//		p3.setBoot(BootColor.BLUE);
//		p3.setName("Player 3");
//		Player p4 = new Player();
//		p4.setBoot(BootColor.RED);
//		p4.setName("Player 4");
//		Player p5 = new Player();
//		p5.setBoot(BootColor.YELLOW);
//		p5.setName("Player 5");
//		Player p6 = new Player();
//		p6.setBoot(BootColor.GREEN);
//		p6.setName("Player 6");
//		p.setLocation(TownName.ELVENHOLD);
//		p.setLocation(TownName.BEATA);
//		p2.setLocation(TownName.ELVENHOLD);
//		g.addPlayer("3", p3);
//		p3.setLocation(TownName.ELVENHOLD);
//		g.addPlayer("4", p4);
//		p4.setLocation(TownName.ELVENHOLD);
//		g.addPlayer("5", p5);
//		p5.setLocation(TownName.ELVENHOLD);
//		g.addPlayer("6", p6);
//		p6.setLocation(TownName.ELVENHOLD);
		
		g.myUsername = "1";
		g.addPlayer("1", p);
		g.addPlayer("2", p2);
		
		p.addCounter( new TravelCounter(TransportationKind.GIANTPIG, false));
		p.addCounter( new TravelCounter(TransportationKind.UNICORN, false));
		p.addCounter( new TravelCounter(TransportationKind.GIANTPIG, false));
		p.addCounter( new TravelCounter(TransportationKind.UNICORN, false));
		p.addCounter( new TravelCounter(TransportationKind.GIANTPIG, false));
		p.addCounter( new TravelCounter(TransportationKind.UNICORN, false));
		
		p.receiveCard( new TravelCard(TransportationKind.GIANTPIG));
		p.receiveCard( new TravelCard(TransportationKind.GIANTPIG));
		p.receiveCard( new TravelCard(TransportationKind.GIANTPIG));
		
		p2.addCounter( new TravelCounter(TransportationKind.ELFCYCLE, true));
		p2.receiveCard( new TravelCard(TransportationKind.UNICORN));
		p2.receiveCard( new TravelCard(TransportationKind.RAFT));
		
		MinuetoWindow window = new MinuetoFrame(1400, 700, true);
		GameGUI gui = new GameGUI(g, p);
		
		g.setCurrentPhase(GamePhase.AUCTION);
		
		p.getPlayerId();
		
		System.out.println(gui.player.getPlayerId());
		
		g.setCurrentPlayer(p);
		
		
		
		
//		window.setVisible(true);
		
		TradeGUI counterToKeep = new TradeGUI(gui);
		
		counterToKeep.start();
		try {
			counterToKeep.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(g.currentTrade.getRequestedItems1());
		
		gui = new GameGUI(g, p2);
		
		g.myUsername = "2";
		
		if(g.currentTrade.getP1().getName() != g.myUsername) {
			InitialTradeResponseGUI resp = new InitialTradeResponseGUI(gui, g.currentTrade);
			
			resp.start();
			try {
				resp.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(g.tradeResponses.get(0).getOfferedCounters2().size());
		
		gui = new GameGUI(g, p);
		
		g.myUsername = "1";
		
		if (g.respondedToTrade.size() == g.getParticipants().size()-1) {
			if(g.getPlayer(g.myUsername) == g.getCurrentPlayer()) {
				ChooseTradeResponseGUI resp = new ChooseTradeResponseGUI(gui);
				
				resp.start();
				try {
					resp.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(g.currentTrade.getP1locked() && g.currentTrade.getP2locked()) {
			g.currentTrade.finalizeTrade();
		}
		else {
			
		}
		
		
	}

}
