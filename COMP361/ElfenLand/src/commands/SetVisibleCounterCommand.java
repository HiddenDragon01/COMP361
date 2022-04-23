package commands;

import gamelogic.Client;
import gamelogic.Counter;

public class SetVisibleCounterCommand extends RemoteCommand{

	int aCounter;
	String aPlayerID;

	public SetVisibleCounterCommand(int pCounter, String pPlayerID) {
		aCounter = pCounter;
		aPlayerID = pPlayerID;
	}

	@Override
	public void execute() {
		Client.instance().getGame().chooseFaceUpCounter(aCounter, aPlayerID);
		
	}

}
