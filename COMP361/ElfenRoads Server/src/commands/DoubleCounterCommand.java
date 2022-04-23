package commands;

import gamelogic.ElfengoldGame;
import gamelogic.RegionKind;
import gamelogic.Road;
import gamelogic.Server;
import gamelogic.TownName;

public class DoubleCounterCommand extends RemoteCommand{

	private int aC1;
	private int aC2;

	RegionKind aKind;
	TownName aSource;
	TownName aDest;
	
	public DoubleCounterCommand(RegionKind pKind, TownName pSource, TownName pDest, int pC1, int pC2) {
		
		aKind = pKind;
		aSource = pSource;
		aDest = pDest;
		
		aC1 = pC1;
		aC2 = pC2;
	}
	
	@Override
	public void execute() {

		((ElfengoldGame) Server.instance().getGame()).placeDouble(aKind, aSource, aDest, aC1, aC2);
		
	}

}
