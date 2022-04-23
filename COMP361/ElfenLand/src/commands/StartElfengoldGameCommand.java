package commands;

import gamelogic.Client;

public class StartElfengoldGameCommand extends RemoteCommand{

private String aGameVariant;
	
	public StartElfengoldGameCommand(String pGameVariant)
	{
		aGameVariant = pGameVariant;
	}

	@Override
	public void execute() {
		
		System.out.println("IN START elfengold GAME COMMAND");
		Client.createElfengoldGame(aGameVariant);
	}
}
