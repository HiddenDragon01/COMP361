package commands;

import gamelogic.Server;

public class SessionIDCommand extends RemoteCommand{
	
	private String aSessionID;
	
	public SessionIDCommand(String pSessionID)
	{
		aSessionID = pSessionID;
	}

	@Override
	public void execute() {
		
		System.out.println("In the sessionIDCommand");
	
		Server.instance().setSessionID(aSessionID);
		
	}

}
