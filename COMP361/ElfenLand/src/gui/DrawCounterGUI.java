package gui;

import java.awt.Point;
import java.awt.Polygon;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Client;

public class DrawCounterGUI extends BasicGUI 
{
	private final Polygon faceDownPilePolygon = GameGUI.createPolygon(1119, 6, 113, 76);

	
	public DrawCounterGUI(GameGUI gameGUI)
	{
		super(gameGUI, "Draw a counter.", "is drawing a counter");
	}
	
	
	private void drawCounter()
	{
//		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler handler = new counterSelectionHandler();
		gameGUI.window.registerMouseHandler(handler, gameGUI.queue);

		
		while (!done)
		{
			gameGUI.drawBackground();
			
			while (gameGUI.queue.hasNext()) 
			{
				gameGUI.queue.handle();
			}
			gameGUI.window.render();
			Thread.yield();
		}
		
		gameGUI.window.unregisterMouseHandler(handler, gameGUI.queue);
	}
	
	
	class counterSelectionHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{	

			Point pt = new Point(x, y);
			
			// A counter from the face down pile is selected
			if ( faceDownPilePolygon.contains(pt) ) 
			{		System.out.println("face down pile");
			
				Client.instance().executeDrawRandomCounter();
				done = true;
				return;
			}

			
			// A counter from the face up pile is chosen
			for (int i = 0; i < gameGUI.game.getFaceUpCounters().size(); i++)
			{
				Polygon p = gameGUI.faceUpCounterPolygons.get(i);
				if ( p.contains(pt) ) {

//					Counter c = gameGUI.game.getFaceUpCounters().get(i);
//					
//					Client.instance().executeDrawCounter(c);
					
					Client.instance().executeDrawCounter(i);
					
					done = true;
					return;
				}
			}
			
			
		}
		}


	@Override
	protected void executeAction() 
	{
		drawCounter();
	}

	
}
