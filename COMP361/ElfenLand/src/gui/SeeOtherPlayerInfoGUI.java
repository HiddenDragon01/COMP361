package gui;

import java.awt.Polygon;
import java.util.Optional;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gui.DrawCounterGUI.counterSelectionHandler;

public class SeeOtherPlayerInfoGUI extends Thread 
{
	private final GameGUI gameGUI;
		
	public SeeOtherPlayerInfoGUI(GameGUI gameGUI)
	{
		this.gameGUI = gameGUI;
	}
	
	@Override
	public void run()
	{
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler hoverOverInfoBox = new hoverOverInfoBox();
		gameGUI.window.registerMouseHandler(hoverOverInfoBox, queue);

		while (true)
		{			
			while (queue.hasNext()) 
			{
				queue.handle();
			}
			
			gameGUI.window.render();
			Thread.yield();
		}

	}
	
	class hoverOverInfoBox implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) 
		{
			System.out.println("movement");
		}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) {}
	}
}
