package gui;
import java.awt.Point;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Optional;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;
import org.yaml.snakeyaml.util.ArrayUtils;

import gamelogic.TransportationKind;
import gamelogic.TravelCard;
import gui.ChooseBootGUI.bootSelectionHandler;
import gamelogic.BootColor;
import gamelogic.Card;
import gamelogic.Client;


public class SelectCard extends Thread {
	private final GameGUI gameGUI;
	private final HashMap<TransportationKind, MinuetoImage> availableCards = new HashMap<>();
	private final HashMap<TransportationKind, Point> cardPositions = new HashMap<>();
	
	
	public SelectCard(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}
	
	public void run()
	{
		gameGUI.run();
		gameGUI.displayMessage("Select Card");
		gameGUI.drawBackground();
		clickCard();
		gameGUI.drawBackground();
		
	}
	
	public void clickCard() {
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler handler = new cardSelectionHandler();
		gameGUI.window.registerMouseHandler(handler, queue);
		boolean yo = true;
		
		while (yo) {
			while (queue.hasNext()) 
			{
				queue.handle();
				for (BootColor c: BootColor.values())
				{
					//System.out.print("sup");
					
					
				}
			}
			gameGUI.window.render();
			Thread.yield();
		}
		gameGUI.window.unregisterMouseHandler(handler, queue);
	}
	
	
	class cardSelectionHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {
			for (int i1 = 0; i1 <  gameGUI.player.getNumCards() ; i1++)
			{
				//MinuetoImage cImg = ImageLoader.getCard(gameGUI.player.getCards().get(i1)).scale(0.21, 0.21);
				Polygon p = gameGUI.cardsInHandPolygons.get(i1);
				int[] h = p.ypoints;
				int[] w = p.xpoints;
				if (p.contains(arg0,arg1)) {
					System.out.println("clicked2");
				}
			}
		}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			for (int i1 = 0; i1 <  gameGUI.player.getNumCards() ; i1++)
			{
				//MinuetoImage cImg = ImageLoader.getCard(gameGUI.player.getCards().get(i1)).scale(0.21, 0.21);
				Polygon p = gameGUI.cardsInHandPolygons.get(i1);
				TravelCard card = (TravelCard) (gameGUI.player.getCards().get(i1));
				//System.out.println(gameGUI.player.getLocation().getAllRoads().keySet().toString());
				System.out.println(gameGUI.player.getLocation().getAllRivers().get(i1));
				
				int[] h = p.ypoints;
				int[] w = p.xpoints;
				if (p.contains(x,y)) {
					System.out.println(card.getTransportationKind());
					//System.out.println("clicked");
				}
			}
			
		}
	}
}
