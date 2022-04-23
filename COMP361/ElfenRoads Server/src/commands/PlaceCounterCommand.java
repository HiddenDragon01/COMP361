package commands;

import gamelogic.Counter;
import gamelogic.RegionKind;
import gamelogic.Server;
import gamelogic.TownName;

public class PlaceCounterCommand extends RemoteCommand {
	
	int aCounter;
	RegionKind aKind;
	TownName aSource;
	TownName aDest;
	

	public PlaceCounterCommand(int pCounter, RegionKind pKind, TownName pSource, TownName pDest) {
		aCounter = pCounter;
		aKind = pKind;
		aSource = pSource;
		aDest = pDest;
	}
	
	public void execute() {
		
		System.out.println("IN THE PLACE COUNTER COMMAND NOW!!");
		
		Server.instance().getGame().placeCounter(aCounter, aKind, aSource, aDest);
	}
	
	

}