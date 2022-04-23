package gui;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.CounterKind;
import gamelogic.Trade;
import gamelogic.TravelCard;
import gui.ActionGUI.doneButtonHandler;
import gui.ActionGUI.passButtonHandler;
import gui.ChooseCounterToKeepElfengoldGUI.counterSelectionHandler;


public class ChooseTradeResponseGUI extends ActionGUI
{
	private ArrayList<Card> chosenCards = new ArrayList<>();
	private ArrayList<Counter> chosenCounters = new ArrayList<>();
//	private Trade trade;
	private Optional<Trade> selectedTrade = Optional.empty();
	private String requestDescription = "";
	protected final ArrayList<Polygon> tradeOptions = new ArrayList<>();

	public ChooseTradeResponseGUI(GameGUI gameGUI) {
		super(gameGUI, "Respond to trade", "is contemplating a new trade");
//		createTradeOptionPolygons();
	}

	@Override
	protected void executeAction() {
		proposeOffer();
		done = false;
		chosenCards.clear();
		chosenCounters.clear();
		selectedTrade = Optional.empty();
		
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

	private void drawTradeStuff() {
		Trade og = gameGUI.game.currentTrade;
		if(og.getP1locked()) {
			for (Counter c : og.getOfferedCounters1())
			{
				MinuetoImage conterImg = ImageLoader.getCounter(c);
				conterImg = conterImg.scale(0.125,0.125);
				gameGUI.window.draw(conterImg, 325+og.getOfferedCounters1().indexOf(c)*30, 125 );
			}
			for (Card c : og.getOfferedCards1())
			{
				MinuetoImage cardImg = ImageLoader.getCard(c);
				cardImg = cardImg.scale(0.125, 0.125);
				gameGUI.window.draw(cardImg, 325+og.getOfferedCards1().indexOf(c)*75, 25);
			}
		}
		
		int gap = 150;
		
		for(Trade t : gameGUI.game.tradeResponses) {
			for (Counter c : t.getOfferedCounters2())
			{
				MinuetoImage conterImg = ImageLoader.getCounter(c);
				conterImg = conterImg.scale(0.125,0.125);
				gameGUI.window.draw(conterImg, 325+t.getOfferedCounters1().indexOf(c)*30, 125+gap );
			}
			for (Card c : t.getOfferedCards2())
			{
				MinuetoImage cardImg = ImageLoader.getCard(c);
				cardImg = cardImg.scale(0.125, 0.125);
				gameGUI.window.draw(cardImg, 325+t.getOfferedCards1().indexOf(c)*75, 25+gap);
			}
			
			gap += 150;
		}
		
	}
	
	private void proposeOffer()
	{
		MinuetoEventQueue queue = new MinuetoEventQueue();
		
		MinuetoMouseHandler tradeOptionHandler = new tradeSelectionHandler();
		MinuetoMouseHandler cardHandler = new ownedItemSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		MinuetoMouseHandler passButton = new passButtonHandler();
		boolean registered = false;
		if(!gameGUI.game.currentTrade.getP1locked()) {
			gameGUI.window.registerMouseHandler(cardHandler, queue);
			registered = true;
		}
		gameGUI.window.registerMouseHandler(doneButton, queue);
		gameGUI.window.registerMouseHandler(passButton, queue);
		gameGUI.window.registerMouseHandler(tradeOptionHandler, queue);
		MinuetoFont fontArial14 = new MinuetoFont("Arial",14,false, false);
		
		drawTradeStuff();
		createTradeOptionPolygons();
		drawTradeOptions();
		if(!gameGUI.game.currentTrade.getP1locked()) {
			drawCardOptions();
			drawCounterOptions();
		}
		gameGUI.window.draw(donebuttonImg, 0,0);
		gameGUI.window.draw(passbuttonImg, 0,0);
		
		while( !done ) 
		{	
			
			while (queue.hasNext()) 
			{
				queue.handle();
				
			}
			MinuetoImage update_description;
    		update_description = new MinuetoText(String.valueOf(requestDescription) ,fontArial14,MinuetoColor.BLACK); 
    		gameGUI.window.draw(update_description, 435, 310);
			
			gameGUI.window.render();
			Thread.yield();
		}
		
		if(registered) {
			gameGUI.window.unregisterMouseHandler(cardHandler, queue);
		}
		gameGUI.window.unregisterMouseHandler(tradeOptionHandler, queue);
		gameGUI.window.unregisterMouseHandler(doneButton, queue);
		gameGUI.window.unregisterMouseHandler(passButton, queue);
		
		
	}

	private void drawCardOptions() {
		showOptions(gameGUI.cardsInHandPolygons.subList(0, gameGUI.player.getNumCards()));
		
	}
	
	private void drawTradeOptions() {
		showTradeOptions(tradeOptions.subList(0, gameGUI.game.tradeResponses.size()));
		
	}
	
	private void createTradeOptionPolygons() {
		// Create polygons for cards in hand
		int init_row = 160; int col = 225;
		int opt_width = 900; int opt_height = 150; int spaceBtwCard = 10;
		
		for (int i1 = 0; i1 < gameGUI.game.tradeResponses.size(); i1++)
		{
			int row = init_row + (spaceBtwCard + opt_height) * i1;
			tradeOptions.add(GameGUI.createPolygon(col, row, opt_width, opt_height));
	
		}
	}
	
	public void showTradeOptions(List<Polygon> options)
	{
		for (Polygon option: options)
		{
			System.out.println("Trade numberwhatever" );
			showTradeOption(option, false);
		}
		
	}
	
	public void showTradeOption(Polygon selection, boolean isSelected)
	{
		MinuetoRectangle selectBox;
		System.out.println(selection.xpoints[0]);
		System.out.println(selection.ypoints[0]);
		
		if(isSelected) {
			selectBox = new MinuetoRectangle(900, 150,MinuetoColor.GREEN, false);
		}
		else {
			selectBox = new MinuetoRectangle(900, 150,MinuetoColor.WHITE, false);
		}
		
		gameGUI.window.draw(selectBox,selection.xpoints[0], selection.ypoints[0]);
	}
	
	
	private void drawCounterOptions()
	{	
		// Get polygons for counters in hand, and reject the obstacle in hand (if any) from the solution
		showOptions(gameGUI.countersInHandPolygons.subList(0, gameGUI.player.getNumCounters()));
	}
	

	@Override
	protected void sendSelection() {
		// TODO send command to client
		Trade t = selectedTrade.get();
		gameGUI.game.currentTrade = t;
		gameGUI.drawBackground();
		
		
	}

	@Override
	protected void resetSelection() {
		chosenCards.clear();
		chosenCounters.clear();
		requestDescription = "";
		done = false;
		proposeOffer();
		
	}

	@Override
	protected boolean checkAllSelected() {
		
		if(selectedTrade == null) {
			gameGUI.displayMessage("ERROR!\nYou must choose a trade. Try again.");
		}
		return selectedTrade != null;
	}

	@Override
	protected boolean isSelectionValid() {
		// TODO: add move validator validation
		return selectedTrade != null;
	}
	
	class ownedItemSelectionHandler implements MinuetoMouseHandler
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
			
			for (int i = 0; i <  gameGUI.player.getNumCounters() ; i++)
			{	
				Polygon p1 = gameGUI.countersInHandPolygons.get(i);
				Counter c = gameGUI.player.getCounters().get(i);
				
				if (p1.contains(x, y))
				{	
					if (chosenCounters.contains(c))
					{
						// hide the un-selected (if-any)
						chosenCounters.remove(c);
						showOption(p1, false);
					}
					
					
					else 
					{
						chosenCounters.add(c);
						showOption(p1, true);
					}
						
				}
			}
		}
		
	}
	
	class TradeKeyboardHandler implements MinuetoKeyboardHandler
	{
		private boolean upper;

		public void handleKeyPress(int value) {
	    	
	        switch(value) {
	        	
	        	case MinuetoKeyboard.KEY_ENTER:
					
					break;
	        	
	        	
	          	case MinuetoKeyboard.KEY_SHIFT:
	          		
	          		upper = true;
	          		break;
	            
	            
	            case MinuetoKeyboard.KEY_DELETE:
	            	// Enable functionality to delete from description
	            	gameGUI.drawBackground();
	            	drawCardOptions();
	        		drawCounterOptions();
	        		drawTradeStuff();
	        		gameGUI.window.draw(donebuttonImg, 0,0);
	            	gameGUI.window.draw(passbuttonImg, 0,0);
	            	
	            	
	            	
	                if (requestDescription.length() > 0) {
	            		requestDescription = removeLast(requestDescription);
	            	} 
	            	
	            	break;
	            default:
	            	// If did not enter enter or delete, then add new letter 
	            	if (requestDescription.length() < 76) {
	            		if (upper) {
	            			requestDescription = requestDescription.concat(Character.toString ((char)value));
	            		} else {
	            			requestDescription = requestDescription.concat(Character.toString ((char)value).toLowerCase());
	            		}
	            		
	            	}
	            	
	       
	            	break;
	        }
	    }

	    public void handleKeyRelease(int value) {
	    	
	    	switch (value) {
	    	
	    		
	    	
		    	case MinuetoKeyboard.KEY_SHIFT:
		  		
			  		upper = false;
			  		break;
			  		
	    	}
		    
	  
	    }

	    public void handleKeyType(char key) {
	   // 	System.out.println("You just typed a key!");
	    }
	    
	    public String removeLast(String s) {
	        return (s == null || s.length() == 0)
	          ? null 
	          : (s.substring(0, s.length() - 1));
	    }

		
	}
	
	class tradeSelectionHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			
			for (int i = 0; i <  gameGUI.game.tradeResponses.size() ; i++)
			{	
				Polygon p = tradeOptions.get(i);
				Trade t = gameGUI.game.tradeResponses.get(i);
				if (p.contains(x, y))
				{	
					
					boolean deselect = false;
					
					// hide the un-selected (if-any)
					if (selectedTrade.isPresent())
					{
						int iold = gameGUI.game.tradeResponses.indexOf(selectedTrade.get());
						showTradeOption(tradeOptions.get(iold), false);
						
						// do not re-draw over un-selected
						if (t == selectedTrade.get()) {selectedTrade = Optional.empty(); deselect = true;}
						
					}
					
					if (!deselect)
					{
						selectedTrade = Optional.of(t);
						showTradeOption(p, true);

					}
						
					}
				}
			
		}
	}
	

	
	
	
}
