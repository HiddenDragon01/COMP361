package commands;

import java.util.ArrayList;

import gamelogic.Client;

public class SetPlayerIndexCommand extends RemoteCommand{
	
	private ArrayList<String> aPlayerArray;
	
	public SetPlayerIndexCommand(ArrayList<String> pPlayerArray)
	{
		aPlayerArray = pPlayerArray;
	}

	@Override
	public void execute() {
		Client.instance().getGame().setPlayerIndex(aPlayerArray);
		
	}

}
