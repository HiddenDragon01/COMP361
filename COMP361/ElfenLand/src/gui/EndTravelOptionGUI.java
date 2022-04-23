package gui;


import java.awt.Polygon;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

import gamelogic.Client;
import gamelogic.ElfengoldGame;
import gamelogic.EndTravelOption;


public class EndTravelOptionGUI extends BasicGUI
{
	private EndTravelOption chosenOption;

	public EndTravelOptionGUI(GameGUI gameGUI) {
		super(gameGUI, "Choose an end of travel option.", "is ending their travel");
	}

	@Override
	protected void executeAction() {
		chooseOption();
		done = false;
		chosenOption = null;
		
	}
	
	@Override
	public void run()
	{
		System.out.println("RUnning");
		gameGUI.run();
		
		if (gameGUI.game.getCurrentPlayer() == gameGUI.player)
		{
			gameGUI.setGuiObserver(this);			
			gameGUI.drawBackground();
			executeAction();
		}
		else
		{
			gameGUI.displayMessage(gameGUI.game.getCurrentPlayer().getName() + " " + update + ".");
		}
		
		gameGUI.drawBackground();
	}

	
	private void chooseOption()
	{
//		MinuetoEventQueue queue = new MinuetoEventQueue();
		System.out.println("giving the options to choose");
		MinuetoMouseHandler optionHandler = new optionSelectionHandler();
		gameGUI.window.registerMouseHandler(optionHandler, gameGUI.queue);
		
		drawSelection();
		
		while( !done ) 
		{	
			System.out.println("while loop");
			
			while (gameGUI.queue.hasNext()) 
			{
				gameGUI.queue.handle();
			}
			System.out.println("exited queue has next");
			gameGUI.window.render();
			Thread.yield();
		}
		
		System.out.println("Exited !done while loop");
		
		gameGUI.window.unregisterMouseHandler(optionHandler, gameGUI.queue);
	}

	
	class optionSelectionHandler implements MinuetoMouseHandler
	{
		Polygon goldButton = GameGUI.createPolygon(498, 319, 112, 39);
		Polygon drawButton = GameGUI.createPolygon(720, 319, 112, 39);
		
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			
			if(goldButton.contains(x,y)) {
				chosenOption = EndTravelOption.TAKEGOLD;
				System.out.println(chosenOption);
				done = true;
				Client.instance().executePassTravel(chosenOption);
			}
			
			if(drawButton.contains(x,y)) {
				chosenOption = EndTravelOption.TAKECARDS;
				System.out.println(chosenOption);
				done = true;
				Client.instance().executePassTravel(chosenOption);
			}
		
		}
	}

	@Override
	protected void drawSelection() {
		gameGUI.window.draw(ImageLoader.get("End-Travel-Option.png"), 0, 0);
		int gold = ((ElfengoldGame) gameGUI.game).getAccumulatedGold();
		MinuetoFont font = new MinuetoFont("Serif", 12, false, false); 
		MinuetoImage goldAmassed = new MinuetoText("x"+Integer.toString(gold), font, MinuetoColor.WHITE);
		gameGUI.window.draw(goldAmassed, 549, 285);
	}

	
}
