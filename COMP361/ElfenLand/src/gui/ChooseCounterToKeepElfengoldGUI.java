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

public class ChooseCounterToKeepElfengoldGUI extends ActionGUI{
	
	private List<Counter> chosenCounters = new ArrayList<>();
	
	public ChooseCounterToKeepElfengoldGUI(GameGUI gameGUI) 
	{
		super(gameGUI, "Choose 2 counters to keep.", "is choosing 2 counters to keep");
	}
	
	@Override
	public void executeAction()
	{
		chooseCounter();
		done = false;
		chosenCounters.clear();
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
		
		MinuetoMouseHandler counterHandler = new counterSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		gameGUI.window.registerMouseHandler(counterHandler, gameGUI.queue);
		gameGUI.window.registerMouseHandler(doneButton, gameGUI.queue);
		
		drawSelection();
		while( !done ) 
		{	
			
			while (gameGUI.queue.hasNext()) 
			{
				gameGUI.queue.handle();
				
			}
			
			gameGUI.window.render();
			Thread.yield();
		}
		
		
		gameGUI.window.unregisterMouseHandler(doneButton, gameGUI.queue);
		gameGUI.window.unregisterMouseHandler(counterHandler, gameGUI.queue);

	}
	
	
	private void drawCounterOptions()
	{	
		// Get polygons for counters in hand, and reject the obstacle in hand (if any) from the solution		
		List<Polygon> selectable = new ArrayList<>(gameGUI.countersInHandPolygons.subList(0, gameGUI.player.getNumCounters()));
		
		if (chosenCounters.size() > 0)
        {
			for (int i = 0; i <  gameGUI.player.getNumCounters() ; i++)
			{	
				Counter c = gameGUI.player.getCounters().get(i);
			
				if (chosenCounters.contains(c))
				{
					Polygon p = gameGUI.countersInHandPolygons.get(i);
					showOption(p, true);
					selectable.remove(p);
				}
			}
        }
        
        showOptions(selectable);
	
	
	}
	
	@Override
	protected void sendSelection() 
	{
		ArrayList<Integer> counterIndices = new ArrayList<>();
		for(Counter counter : chosenCounters) {
			counterIndices.add(gameGUI.player.getCounters().indexOf(counter));
		}
		Client.instance().executeElfengoldCountersToKeep(counterIndices);
	}

	@Override
	protected void resetSelection() 
	{
		chosenCounters.clear();
		drawSelection();
	}
	
	@Override
	protected boolean checkAllSelected() {
				
		if (chosenCounters.size() != 2)
		{
			gameGUI.displayMessage("ERROR!\nYou must choose 2 counters.");
		}
		

		return chosenCounters.size() == 2;
	}

	@Override
	protected boolean isSelectionValid() {
		
		return chosenCounters.size() == 2;
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
			
			for (int i = 0; i <  gameGUI.player.getNumCounters() ; i++)
			{	
				Polygon p = gameGUI.countersInHandPolygons.get(i);
				Counter c = gameGUI.player.getCounters().get(i);
				
				if (p.contains(x, y))
				{	
					if (chosenCounters.contains(c))
					{
						// hide the un-selected (if-any)
						chosenCounters.remove(c);
						showOption(p, false);
					}
					
					
					else 
					{
						chosenCounters.add(c);
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


