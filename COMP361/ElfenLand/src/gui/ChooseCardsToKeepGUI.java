package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.TravelCard;


public class ChooseCardsToKeepGUI extends ActionGUI
{
	private List<Card> chosenCards = new ArrayList<>();

	public ChooseCardsToKeepGUI(GameGUI gameGUI) {
		super(gameGUI, "Choose 4 cards to keep.", "is choosing 4 cards to keep");
	}

	@Override
	protected void executeAction() {
		chooseCards();
		done = false;
		chosenCards.clear();
		
	}
	
	@Override
	public void run()
	{
		gameGUI.run();		
		
		gameGUI.displayMessage(instruction);
		gameGUI.drawBackground();
		executeAction();

		gameGUI.drawBackground();
	}

	
	private void chooseCards()
	{
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler cardHandler = new cardSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		gameGUI.window.registerMouseHandler(cardHandler, queue);
		gameGUI.window.registerMouseHandler(doneButton, queue);
		
		drawCardOptions();
		gameGUI.window.draw(donebuttonImg, 0,0);
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
		gameGUI.window.unregisterMouseHandler(cardHandler, queue);
	}

	private void drawCardOptions() {
		showOptions(gameGUI.cardsInHandPolygons.subList(0, gameGUI.player.getNumCards()));
		
	}

	@Override
	protected void sendSelection() {
		// TODO send command to client
		ArrayList<Integer> cardIndices = new ArrayList<>();
		for (Card c: chosenCards)
		{
			System.out.println(c.getCardKind());
			if (c instanceof TravelCard)
			{
				System.out.println(((TravelCard) c).getTransportationKind());
			}
			
			cardIndices.add(gameGUI.player.getCards().indexOf(c));
		}
		
		Client.instance().executeChosenCardsToKeep(cardIndices);
		
	}

	@Override
	protected void resetSelection() {
		chosenCards.clear();
		gameGUI.window.draw(donebuttonImg, 0,0);
		drawCardOptions();
		
	}

	@Override
	protected boolean checkAllSelected() {
		if (chosenCards.size() != 4)
		{
			gameGUI.displayMessage("ERROR!\nYou must choose 4 cards. Try again.");
		}
		return chosenCards.size() == 4;
	}

	@Override
	protected boolean isSelectionValid() {
		// TODO: add move validator validation
		return chosenCards.size() == 4;
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
	
	
}