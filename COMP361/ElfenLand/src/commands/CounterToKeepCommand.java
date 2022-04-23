package commands;

import gamelogic.Client;
import gamelogic.Counter;

public class CounterToKeepCommand extends RemoteCommand{

	int aCounter;
	String aPlayerID;

	public CounterToKeepCommand(int pCounter, String pPlayerID) {
		aCounter = pCounter;
		aPlayerID = pPlayerID;
	}

	@Override
	public void execute() {
		Client.instance().getGame().chooseCounterToKeep(aCounter, aPlayerID);
		
	}

}
