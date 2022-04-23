package gui;

import java.awt.Polygon;
import java.util.List;

import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoImage;

import gamelogic.Client;
import gamelogic.GamePhase;


public abstract class ActionGUI extends BasicGUI
{
	protected final Polygon donebuttonPolygon = GameGUI.createPolygon(130, 646, 99, 41);
	protected final Polygon passbuttonPolygon = GameGUI.createPolygon(15, 646, 99, 41);
	protected final MinuetoImage donebuttonImg = ImageLoader.get("Done-Button.png");
	protected final MinuetoImage passbuttonImg = ImageLoader.get("Pass-Turn-Button.png");
	protected final MinuetoImage selectBox = ImageLoader.getSelectionBox(false);
			
			
	public ActionGUI(GameGUI gameGUI, String instruction, String update)
	{
		super(gameGUI, instruction, update);
	}
	
	/**
	 * Sends a command to notify of the choice made
	 */
	protected abstract void sendSelection();
	
	/**
	 * Resets the appropriate fields and the window 
	 * to let the user try making a selection again.
	 */
	protected abstract void resetSelection();
	
	/**
	 * If not all items to select have been selected, display the appropriate messages.
	 * @return true if a selection has been made. 
	 */
	protected abstract boolean checkAllSelected();
	
	/**
	 * @return true if the selection is valid, false if not
	 */
	protected abstract boolean isSelectionValid();
	
	
	public void showOptions(List<Polygon> options)
	{
		for (Polygon option: options)
		{
			showOption(option, false);
		}
		
	}
	
	public void showOption(Polygon selection, boolean isSelected)
	{
		MinuetoImage selectBox = ImageLoader.getSelectionBox(isSelected);
		double Xscale = (double) 1 / ((double) selectBox.getWidth() / (selection.xpoints[1] - selection.xpoints[0]) ) ;
		double Yscale = (double) 1 / ((double) selectBox.getHeight() / (selection.ypoints[3] - selection.ypoints[0])) ;
		selectBox = selectBox.scale(Xscale, Yscale);
		gameGUI.window.draw(selectBox,selection.xpoints[0], selection.ypoints[0]);
	}
	
	
	class doneButtonHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			if ( donebuttonPolygon.contains(x, y) )
			{					
				if ( checkAllSelected())
				{
					if (! isSelectionValid())
					{
						gameGUI.displayMessage("ERROR!\nYour selection was not valid. Try again.");
						resetSelection();
//						gameGUI.window.draw(donebuttonImg, 0,0);
//						gameGUI.window.draw(passbuttonImg, 0,0);
					}
					
					else
					{
						done = true;
						gameGUI.displayMessage("Your selection was valid.");
						sendSelection();
					}
				}
				
				else
				{
					resetSelection();
//					gameGUI.window.draw(donebuttonImg, 0,0);
//					gameGUI.window.draw(passbuttonImg, 0,0);
				}
			}			
		}
	}


	class passButtonHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			if ( passbuttonPolygon.contains(x, y) )
			{
				done = true;
				gameGUI.displayMessage("You passed.");
				if(gameGUI.game.getCurrentPhase() == GamePhase.MOVEBOOT) {
					gameGUI.game.endTravel();
				}
				else {
					Client.instance().executePassTurn();
				}
				
			}
			
		}
	}
	


}
