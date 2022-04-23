package gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Optional;

import org.minueto.image.MinuetoImage;
import org.minueto.window.MinuetoWindow;

import gamelogic.Game;
import gamelogic.Player;

public class MoveBootGUI extends Thread
{
	private Point startingPos;
	private Point endingPos;
	private final GameGUI gameGUI;
	
	public MoveBootGUI(GameGUI gameGUI, Point startingPos, Point endingPos) 
	{
		this.gameGUI = gameGUI;
		this.startingPos = startingPos;
		this.endingPos = endingPos;
		
	}
	
	@Override
	public void run()
	{
		gameGUI.run();
		
		for (Point pt: getPositionIncrements())
		{
			gameGUI.drawBackground();
			MinuetoImage boot = ImageLoader.getBoot(gameGUI.game.getCurrentPlayer().getBoot()).scale(0.1, 0.1);
			gameGUI.window.draw(boot, pt.x, pt.y);
		
			gameGUI.window.render();
			Thread.yield();
			
		}
	}
	
	private ArrayList<Point> getPositionIncrements()
	{
		int dx = endingPos.x - startingPos.x;
		int dy = endingPos.y - startingPos.y;
		
		double l = Math.sqrt( Math.pow(dx, 2) + Math.pow(dy, 2));
		
		double y_incr = dy / l;
		double x_incr = dx / l;
		
		
		ArrayList<Point> positionIncrements = new ArrayList<>();
		
		for (int i = 0; i < (int) l; i = i + 40)
		{
			positionIncrements.add(new Point(
					(int) (startingPos.x + i * x_incr), 
					(int) (startingPos.y + i * y_incr) - 40));
		}
		
		return positionIncrements;
	}
	
}
