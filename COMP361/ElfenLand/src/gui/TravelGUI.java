
package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.ElfengoldGame;
import gamelogic.MoveValidator;
import gamelogic.TownName;

public class TravelGUI extends ActionGUI {

	private Optional<TownName> chosenTown = Optional.empty();
	private ArrayList<Card> chosenCards = new ArrayList<Card>();
	
	
	public TravelGUI(GameGUI gameGUI) 
	{
		super(gameGUI, "Travel.", "is travelling");
	}
	
	
	private void chooseTravel()
	{
		MinuetoMouseHandler cardHandler = new cardSelectionHandler();
		MinuetoMouseHandler townHandler = new townSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		MinuetoMouseHandler passButton = new passButtonHandler();
		gameGUI.window.registerMouseHandler(cardHandler, gameGUI.queue);
		gameGUI.window.registerMouseHandler(townHandler, gameGUI.queue);
		gameGUI.window.registerMouseHandler(doneButton, gameGUI.queue);
		gameGUI.window.registerMouseHandler(passButton, gameGUI.queue);
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
		
		gameGUI.window.unregisterMouseHandler(cardHandler, gameGUI.queue);
		gameGUI.window.unregisterMouseHandler(townHandler, gameGUI.queue);
		gameGUI.window.unregisterMouseHandler(doneButton, gameGUI.queue);
		gameGUI.window.unregisterMouseHandler(passButton, gameGUI.queue);
		
	}
	
	
	private HashSet<TownName> getAdjacentTowns()
	{
		HashSet<TownName> adjacentTowns = new HashSet<TownName>();
		
		// If the player has an elvenwitch, then all towns can be travelled to
		if (gameGUI.player.hasElvenWitch())
		{
			adjacentTowns.addAll(Arrays.asList(TownName.values()));
			adjacentTowns.remove(gameGUI.player.getLocation());
		}
		
		// Else, the player can travel to adjacent towns only
		else
		{
			adjacentTowns.addAll(gameGUI.player.getLocation().getAllRivers().keySet());
			adjacentTowns.addAll(gameGUI.player.getLocation().getAllRoads().keySet());
		}
		
		return adjacentTowns;
	}
	
	
	private void drawTownOptions()
	{		
		HashSet<TownName> adjacentTowns = getAdjacentTowns();
		
		List<Polygon> adjTownPolygons = gameGUI.townPolygons.entrySet()
		        .stream().filter(x -> adjacentTowns.contains(x.getKey())).map(Map.Entry::getValue)
                .collect(Collectors.toList());

		
		if (chosenTown.isPresent())
		{
			Polygon p = gameGUI.townPolygons.get(chosenTown.get());
			adjTownPolygons.remove(p);
			showOption(p, true);
		}
		
		showOptions(adjTownPolygons);
		
	}
	
	
	private void drawCardOptions() {
		
		List<Polygon> cardsToSelect = new ArrayList<>(gameGUI.cardsInHandPolygons.subList(0, gameGUI.player.getNumCards()));
		
		if (chosenCards.size() > 0)
        {
			for (int i = 0; i <  gameGUI.player.getNumCards() ; i++)
			{	
				Card c = gameGUI.player.getCards().get(i);
			
				if (chosenCards.contains(c))
				{
					Polygon p = gameGUI.cardsInHandPolygons.get(i);
					showOption(p, true);
					cardsToSelect.remove(p);
				}
			}
        }
        
        showOptions(cardsToSelect);
		
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
			
			for (int i = 0; i <  gameGUI.player.getNumCards() ; i++)
			{	
				Polygon p = gameGUI.cardsInHandPolygons.get(i);
				
				if (p.contains(x, y))
				{	
					Card c = gameGUI.player.getCards().get(i);
					
					
					if (chosenCards.contains(c))
					{
						// hide the un-selected (if-any)
						chosenCards.remove(c);
						showOption(p, false);
					}
					
					
					else 
					{
						chosenCards.add(c);
						showOption(p, true);
					}
						
					}
				}
		}
	}
	
	
	class townSelectionHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			
			
			HashSet<TownName> adjacentTowns = getAdjacentTowns();
			
			
			for (TownName town: adjacentTowns) 
			{
				Polygon p = gameGUI.townPolygons.get(town);
				
				if (p.contains(x, y))
				{	
					boolean deselect = false;
					
					// hide the un-selected (if-any)
					if (chosenTown.isPresent())
					{
						showOption(gameGUI.townPolygons.get(chosenTown.get()), false);
					
						// do not re-draw over un-selected
						if (town == chosenTown.get()) {chosenTown = Optional.empty(); deselect = true;}
						
					}
					
					if (!deselect)
					{
						chosenTown = Optional.of(town);
						showOption(p, true);
					}
						
						
						
					}
				}
		}
	}
	

	
	@Override
	protected void executeAction() 
	{
		chooseTravel();
	}

	@Override
	protected void sendSelection() {
		ArrayList<Integer> cardIndices = new ArrayList<>();
		for (Card c : chosenCards) {
			cardIndices.add(gameGUI.player.getCards().indexOf(c));
		}
		Client.instance().executeTravelOnRoad(chosenTown.get(), cardIndices);
		
	}

	@Override
	protected void resetSelection() {
		chosenCards.clear(); chosenTown = Optional.empty();
		drawSelection();
		
	}

	@Override
	protected boolean checkAllSelected() {
		if (chosenCards.isEmpty() && chosenTown.isEmpty())
		{
			gameGUI.displayMessage("You must choose a town and cards.");
		}
		
		else if (chosenCards.isEmpty())
		{
			gameGUI.displayMessage("You must choose cards.");
		}
		
		else if (chosenTown.isEmpty())
		{
			gameGUI.displayMessage("You must choose a town.");
		}
		return !chosenCards.isEmpty() && chosenTown.isPresent();
	}

	@Override
	protected boolean isSelectionValid() {
		return  MoveValidator.travelOnRoad(gameGUI.player, chosenTown.get(), chosenCards);
	}


	@Override
	protected void drawSelection() {
		drawTownOptions();	
		drawCardOptions();
		gameGUI.window.draw(donebuttonImg, 0,0);
		gameGUI.window.draw(passbuttonImg, 0,0);
		
	}
	
	
}