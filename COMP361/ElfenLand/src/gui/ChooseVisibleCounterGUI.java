package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.CounterKind;
import gamelogic.MoveValidator;
import gamelogic.TravelCard;
import gamelogic.TravelCounter;


public class ChooseVisibleCounterGUI extends ActionGUI
{	

	private Optional<Counter> chosenCounter = Optional.empty();
	
	public ChooseVisibleCounterGUI(GameGUI gameGUI) 
	{
		// TODO: change the input text based on the game
		super(gameGUI, "Choose 1 counter to set as visible.", "is choosing 1 counter to keep");
	}
	
	@Override
	public void executeAction()
	{
		chooseCounter();
		done = false;
		chosenCounter = Optional.empty();
	}
	
	
	@Override
	public void run()
	{
		gameGUI.setGuiObserver(this);
		gameGUI.run();
		gameGUI.displayMessage(instruction);
		gameGUI.drawBackground();
		executeAction();
		gameGUI.drawBackground();
		gameGUI.removeGuiObserver(this);
	}

	
	
	
	private void chooseCounter()
	{
		MinuetoEventQueue queue = gameGUI.queue;
		MinuetoMouseHandler counterHandler = new counterSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		gameGUI.window.registerMouseHandler(counterHandler, queue);
		gameGUI.window.registerMouseHandler(doneButton, queue);
		
		drawSelection();
		while( !done ) 
		{	
			
			while (queue.hasNext()) 
			{
				queue.handle();
				
			}
			
			gameGUI.window.render();
			Thread.yield();
		}
		
		gameGUI.window.unregisterMouseHandler(doneButton, queue);
		gameGUI.window.unregisterMouseHandler(counterHandler, queue);

	}
	
	
	private void drawCounterOptions()
	{	
		// Get polygons for counters in hand, and reject the obstacle in hand (if any) from the solution
		List<Polygon> options =  new ArrayList<>(gameGUI.countersInHandPolygons.subList(gameGUI.player.getNumCounters()-2, gameGUI.player.getNumCounters()));
//		options = options.subList(0, gameGUI.player.getNumCounters());
		
		if (chosenCounter.isPresent()) {
			int i = gameGUI.player.getCounters().indexOf(chosenCounter.get());
			Polygon p = gameGUI.countersInHandPolygons.get(i);
			showOption(p, true);
			options.remove(p);
		}
		
		showOptions(options);
	}
	
	@Override
	protected void sendSelection() 
	{
		Client.instance().executeChooseVisibleCounter(gameGUI.player.getCounters().indexOf(chosenCounter.get()));
	}

	@Override
	protected void resetSelection() 
	{
		chosenCounter = Optional.empty();
		drawSelection();
	}
	
	@Override
	protected boolean checkAllSelected() {
				
		if (chosenCounter.isEmpty())
		{
			gameGUI.displayMessage("ERROR!\nYou must choose 1 counter.");
		}

		return chosenCounter.isPresent();
	}

	@Override
	protected boolean isSelectionValid() {
		
		return true;
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
			
			for (int i = gameGUI.player.getNumCounters() -2; i <  gameGUI.player.getNumCounters() ; i++)
			{	
				Polygon p = gameGUI.countersInHandPolygons.get(i);
				Counter c = gameGUI.player.getCounters().get(i);
				if (p.contains(x, y))
				{	
					
					boolean deselect = false;
					
					// hide the un-selected (if-any)
					if (chosenCounter.isPresent())
					{
						int iold = gameGUI.player.getCounters().indexOf(chosenCounter.get());
						showOption(gameGUI.countersInHandPolygons.get(iold), false);
						
						// do not re-draw over un-selected
						if (c == chosenCounter.get()) {chosenCounter = Optional.empty(); deselect = true;}
						
					}
					
					if (!deselect)
					{
						chosenCounter = Optional.of(c);
						showOption(p, true);

					}
						
					}
				}
			
		}
	}

	@Override
	protected void drawSelection() {
		drawCounterOptions();
		gameGUI.window.draw(donebuttonImg, 0,0);
		
	}
	
	
	
	
}
