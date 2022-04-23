package commands;

import gamelogic.TownName;

public class SendTownCardCommand extends RemoteCommand{
	
	private String aPlayerID;
	private TownName aTownName;
	
	public SendTownCardCommand(String pPlayerID, TownName pTownName)
	{
		aPlayerID = pPlayerID;
		aTownName = pTownName;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
