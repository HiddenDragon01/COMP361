package gui;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Optional;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;

import gamelogic.BootColor;
import gamelogic.Client;


public class ChooseBootGUI extends Thread {

	private final GameGUI gameGUI;
	private final HashMap<BootColor, Polygon> bootPolygons = new HashMap<>();
	private final HashMap<BootColor, MinuetoImage> availableBootImages = new HashMap<>();
	private final HashMap<BootColor, MinuetoImage> takenBootImages = new HashMap<>();
	
	public ChooseBootGUI(GameGUI gameGUI) 
	{
		
		this.gameGUI = gameGUI;
		
		int window_height = gameGUI.window.getHeight();
		int window_width = gameGUI.window.getWidth();
		
		for (BootColor c: BootColor.values())
		{
			if (c != BootColor.UNDECIDED)
			{
				availableBootImages.put(c, ImageLoader.getBoot(c).scale(0.5, 0.5));
				takenBootImages.put(c, ImageLoader.getTakenBoot(c).scale(0.5, 0.5));
			}
			
		}
		
		int num_boots = BootColor.values().length;
		int boot_height = availableBootImages.get(BootColor.BLACK).getHeight();
		int boot_width = availableBootImages.get(BootColor.BLACK).getWidth();

		int x = (window_width - (num_boots * boot_width)) / 2 ;
		int y = window_height / 2 - boot_height / 2 ;
		int dx = 0;
		
		for (BootColor c: BootColor.values())
		{
			if (c != BootColor.UNDECIDED)
			{
				bootPolygons.put(c, GameGUI.createPolygon(x+dx, y, boot_width, boot_height));
				dx += boot_width ;
			}
		}
		
	}


	
	public void run()
	{
		gameGUI.unregisterGlobalMouseHandlers();
		gameGUI.run();
		gameGUI.displayMessage("Choose your boot");
		gameGUI.drawBackground();
		showAvailableBoots();
		gameGUI.drawBackground();
		gameGUI.registerGlobalMouseHandlers();
	}
	
	
	private void showAvailableBoots()
	{

		
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler handler = new bootSelectionHandler();
		gameGUI.window.registerMouseHandler(handler, queue);

		for (BootColor c: BootColor.values())
		{
			if (c != BootColor.UNDECIDED)
			{
				int x = bootPolygons.get(c).xpoints[0];
				int y = bootPolygons.get(c).ypoints[0];
				
				if (c.isTaken()) { gameGUI.window.draw(takenBootImages.get(c), x, y); }
				
				else {gameGUI.window.draw(availableBootImages.get(c), x, y); }
			
			}
		}
		
		while( ! gameGUI.player.hasBoot() ) 
		{	
			while (queue.hasNext()) 
			{
				queue.handle();
				for (BootColor c: BootColor.values())
				{
					if (c != BootColor.UNDECIDED)
					{
						int x = bootPolygons.get(c).xpoints[0];
						int y = bootPolygons.get(c).ypoints[0];
						
						if (c.isTaken()) { gameGUI.window.draw(takenBootImages.get(c), x, y); }
						
						else {gameGUI.window.draw(availableBootImages.get(c), x, y); }
					}
					
					
				}
			}
			
			gameGUI.window.render();
			Thread.yield();
		}
		
		gameGUI.window.unregisterMouseHandler(handler, queue);
	}
	
	
	class bootSelectionHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			for (BootColor c : bootPolygons.keySet()) 
			{
			    if (c != BootColor.UNDECIDED && !c.isTaken())
			    {
			    	Polygon p = bootPolygons.get(c);
			
			    	
			    	if (p.contains(x, y))
			    	{
//			    		gameGUI.player.setBoot(c);
//			    		c.setTaken(true);
			    		Client.instance().executeChooseBoot(c);
			    					    		
			    	}
			    }
			}
		}
	}


	
}