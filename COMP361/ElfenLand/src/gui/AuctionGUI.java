package gui;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.ElfengoldGame;
import gui.ActionGUI.doneButtonHandler;
import gui.ActionGUI.passButtonHandler;
import gui.PlaceCounterOnRoadGUI.counterSelectionHandler;
import gui.PlaceCounterOnRoadGUI.roadSelectionHandler;

public class AuctionGUI extends Thread
{
	private final GameGUI gameGUI;
	private final Polygon bidButton = GameGUI.createPolygon(687, 491, 117, 47);
	private final Polygon  passButton = GameGUI.createPolygon(520, 491, 117, 47);
	private String bid = "";
	private boolean done = false;
	
	public AuctionGUI(GameGUI pGameGUI) {
		gameGUI = pGameGUI;
	}

	@Override
	public void run()
	{
		gameGUI.run();
				
		if (gameGUI.game.getCurrentPlayer() == gameGUI.player)
		{			
			drawAuctionWindow();
			executeAuction();
		}
			
		else
		{
			drawWaitWindow();
			gameGUI.window.render();
		}	
	}

	

	private void executeAuction() {
		MinuetoEventQueue queue = new MinuetoEventQueue();
		MinuetoKeyboardHandler bidHandler  = new enterBid();
		MinuetoMouseHandler buttonsHanlder = new pressButton();
		gameGUI.window.registerKeyboardHandler(bidHandler, queue);
		gameGUI.window.registerMouseHandler(buttonsHanlder, queue);
		
		while( !done ) 
		{	
			
			while (queue.hasNext()) 
			{
				queue.handle();
			}
			
			gameGUI.window.render();
			Thread.yield();
		}
		
		gameGUI.window.unregisterMouseHandler(buttonsHanlder, queue);
		gameGUI.window.unregisterKeyboardHandler(bidHandler, queue);
		
	}
	
	private void drawWaitWindow() {
		MinuetoImage auctionWindow = ImageLoader.get("Auction-Chilling.png");
		gameGUI.window.draw(auctionWindow, 0, 0);
		drawAuctionInfo();
		MinuetoFont serifFont = new MinuetoFont("Serif", 20, false, false); 
		 MinuetoImage bidTxt = new MinuetoText(gameGUI.game.getCurrentPlayer().getName() + " is currently bidding.", 
				 serifFont, MinuetoColor.RED);
		 gameGUI.window.draw(bidTxt, 548, 437);
		

	}
	
	private void drawAuctionWindow()
	{
		MinuetoImage auctionWindow = ImageLoader.get("Auction-Your-Turn.png");
		gameGUI.window.draw(auctionWindow, 0, 0);
		drawAuctionInfo();
		drawBid();
	}
	
	private void drawBid()
	{
		 MinuetoFont serifFont = new MinuetoFont("Serif", 20, false, false); 
		 MinuetoImage bidTxt = new MinuetoText(bid, serifFont, MinuetoColor.BLACK);
		 gameGUI.window.draw(bidTxt, 642, 434);
	}
	
	
	private void drawAuctionInfo()
	{
		drawPastBids();
		drawCurrentToken();
		drawNextTokens();
	}

	private void drawNextTokens() {
		
		List<Polygon> nextCounters = new ArrayList<>(Arrays.asList(
				GameGUI.createPolygon(449, 317, 40, 40),
				GameGUI.createPolygon(506, 317, 40, 40),
				GameGUI.createPolygon(563, 317, 40, 40)));
		
		ArrayList<Counter> auctionPile = ((ElfengoldGame) gameGUI.game).getAuctionPile();
		Integer min = Math.min(auctionPile.size(), nextCounters.size());
		for (int i=0; i<min; i++)
		{
			Counter c = auctionPile.get(i);
			Polygon p = nextCounters.get(i);
			MinuetoImage counterImg = ImageLoader.getCounter(c);
			
			ScalingFactor scale = new ScalingFactor(counterImg.getWidth(), counterImg.getHeight(), p);
			counterImg = counterImg.scale(scale.factorX, scale.factorY);
			gameGUI.window.draw(counterImg, p.xpoints[0], p.ypoints[0]);
		}
	}

	private void drawCurrentToken() {
		Polygon  currCounter = GameGUI.createPolygon(484, 141, 81, 81);
		Counter c = ((ElfengoldGame) gameGUI.game).getCurrentAuctionCounter();
		MinuetoImage counterImg = ImageLoader.getCounter(c);
		ScalingFactor scale = new ScalingFactor(counterImg.getWidth(), counterImg.getHeight(), currCounter);
		counterImg = counterImg.scale(scale.factorX, scale.factorY);
		gameGUI.window.draw(counterImg, currCounter.xpoints[0], currCounter.ypoints[0]);
		
	}

	private void drawPastBids() {
		
		ArrayList<String> currentBidders = ((ElfengoldGame) gameGUI.game).getCurrentBidders();
		 ArrayList<Integer> currentBids = ((ElfengoldGame) gameGUI.game).getCurrentBids();
		 Integer min = Math.min(currentBidders.size(), currentBids.size());
		 min = Math.min(min, 6);
		 MinuetoFont serifFont = new MinuetoFont("Serif", 15, false, false); 

		 for (int i=0; i<min; i++)
		 {
			MinuetoImage bidderName = new MinuetoText(currentBidders.get(i), serifFont, MinuetoColor.BLACK);
			MinuetoImage bidderAmount = new MinuetoText(Integer.toString(currentBids.get(i)), serifFont, MinuetoColor.BLACK);
			System.out.println("so3");
			gameGUI.window.draw(bidderName, 660, 150 + 37*i);
			gameGUI.window.draw(bidderAmount, 845, 150 + 37*i);
		 }
		 
	}
	
	public static String removeLast(String s) {
        return (s == null || s.length() == 0)
          ? null 
          : (s.substring(0, s.length() - 1));
    }
	
	class enterBid implements MinuetoKeyboardHandler
	{
		private List<Integer> allowedFirstInputs = new ArrayList<>();
		private List<Integer> allowedInputs = new ArrayList<>();
		
		private enterBid()
		{
			allowedFirstInputs = new ArrayList<>(Arrays.asList(
				MinuetoKeyboard.KEY_1,
				MinuetoKeyboard.KEY_2,
				MinuetoKeyboard.KEY_3,
				MinuetoKeyboard.KEY_4,
				MinuetoKeyboard.KEY_5,
				MinuetoKeyboard.KEY_6,
				MinuetoKeyboard.KEY_7,
				MinuetoKeyboard.KEY_8,
				MinuetoKeyboard.KEY_9
				));
		
			allowedInputs = new ArrayList<>(allowedFirstInputs);
			allowedInputs.add(MinuetoKeyboard.KEY_0);
		}
		
		
		
		@Override
		public void handleKeyPress(int value) 
		{
			switch(value) {
	            case MinuetoKeyboard.KEY_DELETE:
	            	System.out.println("keydelete");
	            	// Enable the functionality to delete
	            	if (bid.length() != 0) {
	            		bid = removeLast(bid);
	            		drawAuctionWindow();

	            	}
	            	break;
	            default:
	            	if (bid == "" && allowedFirstInputs.contains((Integer) value))
	            	{
	            		bid = Integer.toString(value-48);
	            		drawAuctionWindow();

	            	}
	            	else if (allowedInputs.contains((Integer) value) && bid.length() < 2)
	            	{
	            		bid = bid.concat(Character.toString ((char) (value)));
	            		drawAuctionWindow();
	    	         }	
	            			
	         } 				
		}
		@Override
		public void handleKeyRelease(int arg0) {}

		@Override
		public void handleKeyType(char arg0) {}
		
	}
	
	class pressButton implements MinuetoMouseHandler
	{

		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int arg0, int arg1, int arg2) {
			Point p = new Point(arg0, arg1);
			if (bidButton.contains(p))
			{
				if (bid != "") 
				{
					Integer intBid = Integer.parseInt(bid);
					if (intBid > ((ElfengoldGame) gameGUI.game).getHighestBid())
					{
						// TODO: send command
						Client.instance().executePlaceBid(intBid);
						
						done = true;
					}
				}
				
			}
			
			else if (passButton.contains(p))
			{
				done = true;
				Client.instance().executePassTurn();
			}
			
		}
		
	}
	

}
