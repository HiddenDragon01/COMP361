package commands;

import gamelogic.Client;

public class StartGameCommand extends RemoteCommand{
	
	private String aGameVariant;
	
	public StartGameCommand(String pGameVariant)
	{
		aGameVariant = pGameVariant;
	}

	@Override
	public void execute() {

		System.out.println("CLIENT: IN START GAME COMMAND");
		
		Client.createGame(aGameVariant);
		
	}

}
