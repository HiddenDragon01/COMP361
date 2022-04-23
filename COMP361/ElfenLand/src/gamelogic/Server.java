package gamelogic;

import networking.CommandChannel;

public class Server {
	
	// Field for storing the current Game instance
	private static Game currGame;

		
	// Method used by commands to access the game instance to be modified
	public static Game getGame()
	{
		return currGame;
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("Waiting for connections...");
		
//		Game game = new Game();
		
		CommandChannel serverChannel = new CommandChannel(4444);
		
		
	}

}
