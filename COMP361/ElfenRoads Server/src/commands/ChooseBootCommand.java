package commands;

import gamelogic.BootColor;
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
		
		System.out.println("SERVER: In choosebootcommand");
		
		Server.instance().getGame().chooseBoot(aPlayerID, aBootColor);
	}

}
