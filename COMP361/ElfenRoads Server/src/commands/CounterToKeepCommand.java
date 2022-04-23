package commands;

import gamelogic.Server;
import gamelogic.Counter;

public class CounterToKeepCommand extends RemoteCommand {
	
	int aCounter;
	String aPlayerID;

	public CounterToKeepCommand(int pCounter, String pPlayerID) {
		aCounter = pCounter;
		aPlayerID = pPlayerID;
	}

	@Override
	public void execute() {
		Server.instance().getGame().chooseCounterToKeep(aCounter, aPlayerID);
		
	}
	
	

}