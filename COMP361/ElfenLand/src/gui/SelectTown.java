package gui;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;
import org.yaml.snakeyaml.util.ArrayUtils;

import gamelogic.TransportationKind;
import gamelogic.TravelCard;
import gui.ChooseBootGUI.bootSelectionHandler;
import gamelogic.BootColor;
import gamelogic.TownName;
import gamelogic.Card;
import gamelogic.Client;


public class SelectTown extends Thread {
	private final GameGUI gameGUI;
	private final HashMap<TransportationKind, MinuetoImage> availableCards = new HashMap<>();
	private final HashMap<TransportationKind, Point> cardPositions = new HashMap<>();
	
	
	public SelectTown(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}
	
	public void run()
	{
		gameGUI.run();
		gameGUI.displayMessage("Select Town");
		gameGUI.drawBackground();
		clickTown();
		gameGUI.drawBackground();
		
	}
	
	public void clickTown() {
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler handler = new cardSelectionHandler();
		gameGUI.window.registerMouseHandler(handler, queue);
		boolean yo = true;
		
		while (yo) {
			while (queue.hasNext()) 
			{
				queue.handle();
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
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
				HashSet<TownName> adjacentTowns = new HashSet<TownName>();;
				adjacentTowns.addAll(gameGUI.player.getLocation().getAllRivers().keySet());
				adjacentTowns.addAll(gameGUI.player.getLocation().getAllRoads().keySet());
				
				
				for (TownName town: adjacentTowns) {
					Polygon p = gameGUI.townPolygons.get(town);
					if (p.contains(x,y)) {
						System.out.println(town);
					}
				}
				
			
		}
	}
}
