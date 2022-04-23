package commands;

import gamelogic.Server;

import java.util.ArrayList;

import gamelogic.Counter;

public class ElfengoldCountersToKeepCommand extends RemoteCommand {
	
	ArrayList<Integer> aCounter;
	String aPlayerID;

	public ElfengoldCountersToKeepCommand(ArrayList<Integer> pCounter, String pPlayerID) {
		aCounter = pCounter;
		aPlayerID = pPlayerID;
	}

	@Override
	public void execute() {
		Server.instance().getGame().chooseCounterToKeep(aCounter, aPlayerID);
		
	}
	
	

}