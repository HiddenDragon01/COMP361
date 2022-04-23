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
import gamelogic.Trade;
import gamelogic.TravelCard;
import gui.ActionGUI.doneButtonHandler;
import gui.ActionGUI.passButtonHandler;
import gui.ChooseCounterToKeepElfengoldGUI.counterSelectionHandler;


public class InitialTradeResponseGUI extends ActionGUI
{
	private ArrayList<Card> chosenCards = new ArrayList<>();
	private ArrayList<Counter> chosenCounters = new ArrayList<>();
	private Trade trade;
	private String requestDescription = "";

	public InitialTradeResponseGUI(GameGUI gameGUI, Trade t) {
		super(gameGUI, "Respond to trade", "is contemplating a new trade");
		trade = t;
	}

	@Override
	protected void executeAction() {
		proposeOffer();
		done = false;
		chosenCards.clear();
		chosenCounters.clear();
		
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
		MinuetoRectangle rectangle1 = new MinuetoRectangle(500,40,MinuetoColor.WHITE,true);
		gameGUI.window.draw(rectangle1, 425, 200);
		MinuetoFont fontArial14 = new MinuetoFont("Arial",14,false, false);
		MinuetoText initial_description = new MinuetoText(String.valueOf(trade.getRequestedItems1()) ,fontArial14,MinuetoColor.BLACK); 
		gameGUI.window.draw(initial_description, 435, 210);
		
		if(trade.getP1locked()) {
			drawOfferedCounters();
			drawOfferedCards();
		}
		else {
			MinuetoRectangle rectangle = new MinuetoRectangle(500,40,MinuetoColor.WHITE,true);
			gameGUI.window.draw(rectangle, 425, 300);
		}
		
	}
	
	private void proposeOffer()
	{
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoMouseHandler cardHandler = new ownedItemSelectionHandler();
		MinuetoMouseHandler doneButton = new doneButtonHandler();
		MinuetoKeyboardHandler keyboardHandler = new TradeKeyboardHandler();
		
		boolean registered = false;
		if(!trade.getP1locked()) {
			gameGUI.window.registerKeyboardHandler(keyboardHandler, queue);
			registered = true;
		}
		
		MinuetoMouseHandler passButton = new passButtonHandler();
		gameGUI.window.registerMouseHandler(cardHandler, queue);
		gameGUI.window.registerMouseHandler(doneButton, queue);
		gameGUI.window.registerMouseHandler(passButton, queue);
		MinuetoFont fontArial14 = new MinuetoFont("Arial",14,false, false);
		
		drawTradeStuff();
		drawCardOptions();
		drawCounterOptions();
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
		
		gameGUI.window.unregisterMouseHandler(doneButton, queue);
		gameGUI.window.unregisterMouseHandler(cardHandler, queue);
		gameGUI.window.unregisterMouseHandler(passButton, queue);
		if(registered) {
			gameGUI.window.unregisterKeyboardHandler(keyboardHandler, queue);
		}
		
	}

	private void drawCardOptions() {
		showOptions(gameGUI.cardsInHandPolygons.subList(0, gameGUI.player.getNumCards()));
		
	}
	
	
	private void drawCounterOptions()
	{	
		// Get polygons for counters in hand, and reject the obstacle in hand (if any) from the solution
		showOptions(gameGUI.countersInHandPolygons.subList(0, gameGUI.player.getNumCounters()));
	}
	
	private void drawOfferedCounters() {
		for (Counter c : trade.getOfferedCounters1())
		{
			MinuetoImage conterImg = ImageLoader.getCounter(c);
			conterImg = conterImg.scale(0.25,0.25);
			gameGUI.window.draw(conterImg, 325+trade.getOfferedCounters1().indexOf(c)*50, 450 );
		}
	}


	private void drawOfferedCards() {
		for (Card c : trade.getOfferedCards1())
		{
			MinuetoImage cardImg = ImageLoader.getCard(c);
			cardImg = cardImg.scale(0.20, 0.20);
			gameGUI.window.draw(cardImg, 325+trade.getOfferedCards1().indexOf(c)*100, 300);
		}
	}

	@Override
	protected void sendSelection() {
		// TODO send command to client
		trade.setP2(gameGUI.game.getPlayer(gameGUI.game.myUsername));
		trade.setOfferedCards2(chosenCards);
		trade.setOfferedCounters2(chosenCounters);
		trade.setP2locked(true);
		trade.setRequestedItems2(requestDescription);
		
		gameGUI.game.initialTradeResponse(trade);
		
		
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
		if (chosenCards.isEmpty() && chosenCounters.isEmpty())
		{
			gameGUI.displayMessage("ERROR!\nYou must propose an offer. Try again.");
			return !chosenCards.isEmpty() || !chosenCounters.isEmpty();
		}
		
		if(!trade.getP1locked() && requestDescription.length() == 0) {
			gameGUI.displayMessage("ERROR!\nYou must describe your requested items. Try again.");
		}
		return trade.getP1locked() || requestDescription.length() > 0;
	}

	@Override
	protected boolean isSelectionValid() {
		// TODO: add move validator validation
		return (!chosenCards.isEmpty() || !chosenCounters.isEmpty()) && (trade.getP1locked() || requestDescription.length() > 0);
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
	        		drawTradeStuff();
	        		
	            	
	            	
	            	
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
	

	
	
	
}
