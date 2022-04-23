package commands;

import gamelogic.BootColor;
import gamelogic.Client;
import gamelogic.Player;
import gamelogic.Server;

public class ChooseBootCommand extends RemoteCommand{
	
	private String aPlayerID;
	private BootColor aBootColor;
	
	public ChooseBootCommand(String pPlayerID, BootColor pBootColor)
	{
		aPlayerID = pPlayerID;
		aBootColor = pBootColor;
	}

	@Override
	public void execute() {
		
		
		Client.instance().getGame().chooseBoot(aPlayerID, aBootColor);

		System.out.println("choose boot command!!! ");
		
		
	}

}
