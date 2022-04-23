package commands;

import java.util.ArrayList;

import gamelogic.Client;
import gamelogic.Counter;

public class ElfengoldCountersToKeepCommand extends RemoteCommand{

	ArrayList<Integer> aCounter;
	String aPlayerID;

	public ElfengoldCountersToKeepCommand(ArrayList<Integer> pCounter, String pPlayerID) {
		aCounter = pCounter;
		aPlayerID = pPlayerID;
	}

	@Override
	public void execute() {
		Client.instance().getGame().chooseCounterToKeep(aCounter, aPlayerID);
		
	}

}
