package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.CounterKind;
import gamelogic.MoveValidator;
import gamelogic.Road;
import gamelogic.TravelCounter;

public class PlaceCounterOnRoadElfenGoldGUI extends ActionGUI
{
	
	protected Optional<Road> chosenRoad = Optional.empty();
//	protected Optional<Counter> chosenCounter = Optional.empty();
//	protected Optional<Counter> secondCounter = Optional.empty();
	protected List<Counter> chosenCounters = new ArrayList<>();
	protected HashMap<Road, Integer> chosenExchangeCounters = new HashMap<>();
	
	boolean isExchange = false;
	boolean showOption = false;


	public PlaceCounterOnRoadElfenGoldGUI(GameGUI gameGUI) 
	{
		super(gameGUI, "Place a counter on a road.", "is planning their travel");
	}
	
	@Override
	public void executeAction()
	{
		placeCounterOnRoad();
		done = false;
		chosenRoad = Optional.empty();
		chosenExchangeCounters = new HashMap<>();
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
	
	
	private HashMap<Road, List<Polygon>> getExchangeOptions()
	{
		
		HashMap<Road, List<Polygon>> roadTravelCounterPolygons = new HashMap<>();
		
		
		for (Road r: gameGUI.roadPolygons.keySet())
		{
			List<Polygon> transportationPolygons = new ArrayList<>();
			
			for(int i=0; i<r.getNumCounters(); i++)
			{
				if (r.getCounters().get(i).getCounterKind() == CounterKind.TRANSPORTATION)
				{
					transportationPolygons.add(gameGUI.roadPolygons.get(r).get(i));
				}
			}
			
			if (transportationPolygons.size() > 0)
			{
				roadTravelCounterPolygons.put(r, transportationPolygons);
			}
			
			
		}
		
		
		return roadTravelCounterPolygons;
	}
	
	
	void drawRoadOptions()
	{		
		// If the selected counter is an exchange counter, 
		// then the user must choose 2 counters that are already on roads
		if (isExchange)
		{
			List<Polygon> roadCounterPolygons = getExchangeOptions().values().stream()
					.flatMap(c -> c.stream()).collect(Collectors.toList());

			if (chosenExchangeCounters.size() > 0)
			{
				for (Road r: chosenExchangeCounters.keySet())
				{
					int i = chosenExchangeCounters.get(r);
					Polygon p = gameGUI.roadPolygons.get(r).get(i);
					roadCounterPolygons.remove(p);
					showOption(p, true);
					
				}
			}
			
			showOptions(roadCounterPolygons);
			
			return;
		}
		
		

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

		if (chosenCounters.size() > 0)
        {
			for (Counter c: chosenCounters)
			{
				System.out.println(c.getCounterKind().toString());
				int i = gameGUI.player.getCounters().indexOf(c);
	        	Polygon counterPolygon = gameGUI.countersInHandPolygons.get(i);
				showOption(counterPolygon, true);
				selectable.remove(counterPolygon);
			}
        	
        }
        
        showOptions(selectable);
	}
	
	
	
	
	
	
	
	@Override
	protected void sendSelection() 
	{
		
		if (exchangeSelected())
		{
			
			Road r1 = (Road) chosenExchangeCounters.keySet().toArray()[0];
			Road r2 = (Road) chosenExchangeCounters.keySet().toArray()[1];
			int c1 = chosenExchangeCounters.get(r1);
			int c2 = chosenExchangeCounters.get(r2);
			int exc = gameGUI.player.getCounters().indexOf(chosenCounters.get(0));
			
			Client.instance().executeExchangeCounterCommand(r1, r2, c1, c2, exc);
		}
		
		else if (doubleSelected())
		{
			Counter c1;
			Counter c2;
			
			c1 = chosenCounters.get(0);
			c2 = chosenCounters.get(1);
			
			Road r = chosenRoad.get();
			
			Client.instance().executeDoubleCounterCommand(r, gameGUI.player.getCounters().indexOf(c1), gameGUI.player.getCounters().indexOf(c2));

		}
		
		else
		{
			Counter c = chosenCounters.get(0);
			Road r = chosenRoad.get();
			
			Client.instance().executePlaceCounter(gameGUI.player.getCounters().indexOf(c), r);
		}
		
	}

	@Override
	protected void resetSelection() 
	{
		chosenCounters.clear();
		chosenRoad = Optional.empty();
		chosenExchangeCounters.clear();
		isExchange = false;
		showOption = false;
		drawSelection();
	}

	@Override
	protected boolean checkAllSelected() {
		
		if (exchangeSelected())
		{
			if (chosenExchangeCounters.size() != 2)
			{
				gameGUI.displayMessage("ERROR!\nYou must choose 2 counters to exchange. Try again.");
			}
			
			return chosenExchangeCounters.size() == 2;
			
		}
		
		else if (doubleSelected())
		{
			if (chosenCounters.size() != 2 && chosenRoad.isEmpty())
			{
				gameGUI.displayMessage("ERROR!\nYou must choose an additionnal counter and a road.\nTry again.");
			}
			
			else if (chosenCounters.size() != 2)
			{
				gameGUI.displayMessage("ERROR!\nYou must choose an additionnal counter. Try again.");
			}
			
			else if (chosenRoad.isEmpty())
			{
				gameGUI.displayMessage("ERROR!\nYou must choose a road. Try again.");
			}
			
			return chosenCounters.size() == 2 && chosenRoad.isPresent();
			
		}
		
		else
		{

			if (chosenRoad.isEmpty() && chosenCounters.size() != 1)
			{
				gameGUI.displayMessage("ERROR!\nYou must choose a road and a counter. Try again.");
			}
			
			else if (chosenCounters.size() != 1)
			{
				gameGUI.displayMessage("ERROR!\nYou must choose a counter. Try again.");
			}
			
			else if (chosenRoad.isEmpty())
			{
				gameGUI.displayMessage("ERROR!\nYou must choose a road. Try again.");
			}
			
			return chosenCounters.size() == 1 && chosenRoad.isPresent();
	}}

	
	private boolean doubleSelected() {
		
		boolean doubleSelected = false;

		for (Counter c: chosenCounters)
		{
			if (c.getCounterKind() == CounterKind.DOUBLE)
			{
					doubleSelected = true; break; 
			}
		}
		
		return doubleSelected;
	}
	
	private boolean exchangeSelected()
	{
		boolean exchangeSelected = false;

		for (Counter c: chosenCounters)
		{
			if (c.getCounterKind() == CounterKind.EXCHANGE)
			{
				exchangeSelected = true; break; 
			}
		}
		
		return exchangeSelected;
	}

	@Override
	protected boolean isSelectionValid() {

		boolean isValid = false;
		
		if (exchangeSelected())
		{
			
			Road r1 = (Road) chosenExchangeCounters.keySet().toArray()[0];
			Road r2 = (Road) chosenExchangeCounters.keySet().toArray()[1];
			Counter c1 = r1.getCounters().get(chosenExchangeCounters.get(r1));
			Counter c2 = r2.getCounters().get(chosenExchangeCounters.get(r2));

			isValid = MoveValidator.exchangeRoadTravelCounters(r1, r2, c1, c2);
			
		}
		
		else if (doubleSelected())
		{
			Counter doubleTransport;
			
			if (chosenCounters.get(0).getCounterKind() == CounterKind.TRANSPORTATION)
			{
				doubleTransport = chosenCounters.get(0);
			}
			
			else {doubleTransport = chosenCounters.get(1);}
			
			isValid = MoveValidator.placeDoubleCounter(doubleTransport, chosenRoad.get());
		}
		
		else
		{
			isValid = MoveValidator.placeCounter(chosenCounters.get(0), chosenRoad.get());
		}
		
		return isValid;
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
				{	System.out.println(chosenCounters.size());
					Counter c = gameGUI.player.getCounters().get(i);
									
					// hide the un-selected (if-any)
					if (chosenCounters.contains(c)) 
					{
						
						chosenCounters.remove(c);
						
						
						if (c.getCounterKind() == CounterKind.EXCHANGE)
						{
							isExchange = false;
							gameGUI.drawBackground();
						}
					}

					// If we click on an exchange
					else if (c.getCounterKind() == CounterKind.EXCHANGE)
					{
						isExchange = true;
						chosenCounters.clear();
						chosenCounters.add(c);
						chosenRoad = Optional.empty();
						chosenExchangeCounters.clear();
						gameGUI.drawBackground();
					}
					
					else
					{
						if (isExchange)
						{
							isExchange = false;
							chosenCounters.clear();
							chosenRoad = Optional.empty();
							chosenExchangeCounters.clear();
							gameGUI.drawBackground();
						}
						
						chosenCounters.add(c);
						
					}
					System.out.println(chosenCounters.size());
					drawSelection();
				}
			}
		}
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
			
			if (isExchange)
			{
				for (Road r: getExchangeOptions().keySet())
				{
					for (Polygon p: getExchangeOptions().get(r))
					{
						if (p.contains(x, y))
						{
							int i = gameGUI.roadPolygons.get(r).indexOf(p);
							
							if (chosenExchangeCounters.containsKey(r))
							{
								if (chosenExchangeCounters.get(r).equals(i))
								{
									chosenExchangeCounters.remove(r);
								}
							}
							
							else
							{
								chosenExchangeCounters.put(r, i);
							}
							
							
						}
					}

				}
			}
			
			else
			{
				// loop through the roadPolygons, which are the areas for counters
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
		
		drawRoadOptions();
			
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