package gui;

import java.util.Optional;

import org.minueto.window.MinuetoWindow;

import gamelogic.Game;
import gamelogic.Player;

public class NewRoundGUI extends Thread
{

	private final GameGUI gameGUI;
	
	
	public NewRoundGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	
	@Override
	public void run()
	{
		gameGUI.run();
		gameGUI.displayMessage("New round");
		gameGUI.drawBackground();	
	}
	
}
