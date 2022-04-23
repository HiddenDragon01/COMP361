package commands;

import gamelogic.Client;
import gamelogic.Player;

public class AddPlayerCommand extends RemoteCommand{
	
	private String aPlayerID;
//	private Player aPlayer;
	
	
	public AddPlayerCommand(String pPlayerID)
	{
		aPlayerID = pPlayerID;
	}

	@Override
	public void execute() {
		
		System.out.println("IN ADD PLAYER COMMAND CLIENT");
		Client.addPlayer(aPlayerID);
		
	}


}
