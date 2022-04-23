package gui;

import java.awt.Point;
import java.awt.Polygon;

import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class DrawCardGUI extends BasicGUI
{

	public DrawCardGUI(GameGUI gameGUI) {
		super(gameGUI, "Draw a card.", "is drawing a card");
	}

	private void drawCard()
	{
		MinuetoEventQueue queue = gameGUI.queue;
		MinuetoMouseHandler handler = new cardSelectionHandler();
		gameGUI.window.registerMouseHandler(handler, queue);

		
		while (!done)
		{
			gameGUI.drawBackground();
			
			while (queue.hasNext()) 
			{
				queue.handle();
			}
			
			gameGUI.window.render();
			Thread.yield();
		}
		
		gameGUI.window.unregisterMouseHandler(handler, queue);
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
			assert gameGUI instanceof GameGUI_Elfengold && gameGUI.game instanceof ElfengoldGame;
			Point pt = new Point(x, y);
			
			// A card from the face down pile is selected
			if ( ((GameGUI_Elfengold) gameGUI).cardPile.contains(pt) ) 
			{		System.out.println("face down pile");
			
				// TODO: send command for drawing from face down pile of cards
				Client.instance().executeDrawRandomCard();
				done = true;
				return;
			}

			// Gold pile is selected
			boolean clickedGoldPile = ((GameGUI_Elfengold) gameGUI).goldCardPilePolygon.contains(pt);
			boolean goldPileIsPresent = ((ElfengoldGame) gameGUI.game).getGoldCardPile() > 0;
			if (clickedGoldPile && goldPileIsPresent)
				
			{		System.out.println("gold pile");
			
				// TODO: send command for drawing from gold card pile
				Client.instance().executeChooseGoldCardPile();
				done = true;
				return;
			}
			
			// A face up card is selected
			for (int i = 0; i < ((ElfengoldGame) gameGUI.game).getFaceUpCards().size(); i++)
			{
				Polygon p = ((GameGUI_Elfengold) gameGUI).faceUpCardsPolygons.get(i);
				if ( p.contains(pt) ) {
					
					Card selectedCard = ((ElfengoldGame) gameGUI.game).getFaceUpCards().get(i);
					System.out.println(selectedCard.getCardKind());
					
					//TODO: send command for drawing face up card
					Client.instance().executeDrawFaceUpCardCommand(i);
					
					done = true;
					return;
				}
			}
			
			
		}
	}
	
	@Override
	protected void executeAction() {
		drawCard();	
	}


}
