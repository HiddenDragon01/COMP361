package gui;

public abstract class BasicGUI extends Thread
{		
	protected String instruction;
	protected String update;
	
	boolean done = false;
	
	protected final GameGUI gameGUI;
		
	public BasicGUI(GameGUI gameGUI, String instruction, String update)
	{
		this.gameGUI = gameGUI;
		this.instruction = instruction;
		this.update = update;
	}
	
	/**
	 * Executes the action GUI that lets the player perform an action
	 */
	protected abstract void executeAction();
	protected void drawSelection() {}
	
	@Override
	public void run()
	{
		gameGUI.run();
				
		if (gameGUI.game.getCurrentPlayer() == gameGUI.player)
		{
			gameGUI.setGuiObserver(this);
			gameGUI.displayMessage(instruction);
			gameGUI.drawBackground();
			executeAction();
			
		}
			
		else
		{
			gameGUI.displayMessage(gameGUI.game.getCurrentPlayer().getName() + " " + update + ".");
			
			// This is the code that causes flickering for now until it is fixed.
			
//			while (true)
//			{
//				gameGUI.drawBackground();
//				
//				while (gameGUI.queue.hasNext()) 
//				{
//					gameGUI.queue.handle();
//				}
//				
//				gameGUI.drawBackground();
//				gameGUI.window.render();
//				Thread.yield();
//			}
		}

		
		gameGUI.drawBackground();
	}
	
	


}

