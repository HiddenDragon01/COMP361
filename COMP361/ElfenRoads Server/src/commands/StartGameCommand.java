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
		// TODO Auto-generated method stub
		System.out.println("IN START GAME COMMAND");
		Client.createGame(aGameVariant);
	}

}
