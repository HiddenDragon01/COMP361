package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.CounterKind;
import gamelogic.MoveValidator;
import gamelogic.Road;

public class PlaceCounterOnRoadGUI extends ActionGUI
{
	
	protected Optional<Road> chosenRoad = Optional.empty();
	protected Optional<Counter> chosenCounter = Optional.empty();
	protected Optional<Counter> secondCounter = Optional.empty();

	public PlaceCounterOnRoadGUI(GameGUI gameGUI) 
	{
		super(gameGUI, "Place a counter on a road.", "is planning their travel");
	}
	
	@Override
	public void executeAction()
	{
		placeCounterOnRoad();
		done = false;
		chosenRoad = Optional.empty();
		chosenCounter = Optional.empty();
	}	
	
	void placeCounterOnRoad()
	{
		gameGUI.setGuiObserver(this);
		MinuetoEventQueue queue = gameGUI.queue;
		MinuetoMouseHandler counterHandler = new counterSelectionHandler();
		MinuetoMouseHandler roadHandler = new roadSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		MinuetoMouseHandler passButton = new passButtonHandler();
		gameGUI.window.registerMouseHandler(counterHandler, queue);
		gameGUI.window.registerMouseHandler(roadHandler, queue);
		gameGUI.window.registerMouseHandler(doneButton, queue);
		gameGUI.window.registerMouseHandler(passButton, queue);
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
		
		gameGUI.removeGuiObserver(this);
		gameGUI.window.unregisterMouseHandler(doneButton, queue);
		gameGUI.window.unregisterMouseHandler(passButton, queue);
		gameGUI.window.unregisterMouseHandler(roadHandler, queue);
		gameGUI.window.unregisterMouseHandler(counterHandler, queue);

	}
	
	void drawRoadOptions()
	{		
		List<Polygon> emptyRoadPolygons = gameGUI.roadPolygons.entrySet()
		        .stream().filter(x -> x.getKey().getNumCounters() < x.getValue().size()).map(x -> x.getValue().get(x.getKey().getNumCounters()))
                .collect(Collectors.toList());

        if (chosenRoad.isPresent())
        {
        	Road r = chosenRoad.get();
			if (r.getNumCounters() < gameGUI.roadPolygons.get(r).size()) 
			{ 
				Polygon counterPolygon = gameGUI.roadPolygons.get(r).get(r.getNumCounters());
				showOption(counterPolygon, true);
				emptyRoadPolygons.remove(counterPolygon);
			}
        }
        
        showOptions(emptyRoadPolygons);
        
	}
	
	
	void drawCounterOptions()
	{		
		List<Polygon> selectable = new ArrayList<>(gameGUI.countersInHandPolygons.subList(0, gameGUI.player.getNumCounters()));
		
		if (chosenCounter.isPresent())
        {
        	Counter c = chosenCounter.get();
        	int i = gameGUI.player.getCounters().indexOf(c);
        	Polygon counterPolygon = gameGUI.countersInHandPolygons.get(i);
			showOption(counterPolygon, true);
			selectable.remove(counterPolygon);
        }
        
        showOptions(selectable);
	}
	
	
	
	class roadSelectionHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			// loop through the roadPolygons, which are the areas for counters (not obstacles)
			for(Road r: gameGUI.roadPolygons.keySet() )
			{	
				Polygon counterPolygon;
				
				// check if there no counter on that road
				if (r.getNumCounters() < gameGUI.roadPolygons.get(r).size()) { counterPolygon = gameGUI.roadPolygons.get(r).get(r.getNumCounters());}
				else {continue;}; 
				// if we clicked a valid polygon
				if (counterPolygon.contains(x, y) )
				{								
					boolean deselect = false;
						
					// hide the un-selected (if-any)
					if (chosenRoad.isPresent())
					{
						// get the polygon associated to the previous selection
						Road prevRoad = chosenRoad.get();
						Polygon previousPolygon = gameGUI.roadPolygons.get(prevRoad).get(prevRoad.getNumCounters());
						
						showOption(previousPolygon, false);

						// do not re-draw over un-selected
						if (r == chosenRoad.get()) {chosenRoad = Optional.empty(); deselect = true;}
							
					}
						
					if (!deselect)
					{
						chosenRoad = Optional.of(r);
						showOption(counterPolygon, true);
					}		
				}
			}
		}
	}
	
	
	
	@Override
	protected void sendSelection() 
	{
		assert chosenCounter.isPresent() && chosenRoad.isPresent();
		Client.instance().executePlaceCounter(gameGUI.player.getCounters().indexOf(chosenCounter.get()), chosenRoad.get());
	}

	@Override
	protected void resetSelection() 
	{
		chosenCounter = Optional.empty();
		secondCounter = Optional.empty(); 
		chosenRoad = Optional.empty();
		drawSelection();
	}

	@Override
	protected boolean checkAllSelected() {
		if (chosenRoad.isEmpty() && chosenCounter.isEmpty())
		{
			gameGUI.displayMessage("ERROR!\nYou must choose a road and a counter. Try again.");
		}
		
		else if (chosenCounter.isEmpty())
		{
			gameGUI.displayMessage("ERROR!\nYou must choose a counter. Try again.");
		}
		
		else if (chosenRoad.isEmpty())
		{
			gameGUI.displayMessage("ERROR!\nYou must choose a road. Try again.");
		}
		
		return chosenCounter.isPresent() && chosenRoad.isPresent();
	}

	@Override
	protected boolean isSelectionValid() {
		assert chosenCounter.isPresent() && chosenRoad.isPresent();
		return MoveValidator.placeCounter(chosenCounter.get(), chosenRoad.get());
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
				
				if (p.contains(x, y))
				{	
					Counter c = gameGUI.player.getCounters().get(i);
					boolean deselect = false;
					
					// hide the un-selected (if-any)
					if (chosenCounter.isPresent())
					{
						int iold = gameGUI.player.getCounters().indexOf(chosenCounter.get());
						showOption(gameGUI.countersInHandPolygons.get(iold), false);
						
						// do not re-draw over un-selected
						if (c == chosenCounter.get()) {chosenCounter = Optional.empty(); deselect = true;}
						
						
						//handling double
						
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
		drawRoadOptions();	
		drawCounterOptions();
		gameGUI.window.draw(donebuttonImg, 0,0);
		gameGUI.window.draw(passbuttonImg, 0,0);
	}	

}