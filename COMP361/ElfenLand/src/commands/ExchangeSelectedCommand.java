package commands;

import gamelogic.Client;
import gamelogic.ElfengoldGame;
import gamelogic.RegionKind;
import gamelogic.Road;
import gamelogic.TownName;

public class ExchangeSelectedCommand extends RemoteCommand{
	
	RegionKind aKind1;
	TownName aSource1;
	TownName aDest1;
	
	RegionKind aKind2;
	TownName aSource2;
	TownName aDest2;

	
	private int aCounter1;
	private int aCounter2;
	private int aExc;
	
	public ExchangeSelectedCommand(RegionKind pKind1, TownName pSource1, TownName pDest1, RegionKind pKind2, TownName pSource2, TownName pDest2, int pCounter1, int pCounter2, int pExc) {
		aKind1 = pKind1;
		aSource1 = pSource1;
		aDest1 = pDest1;
		aKind2 = pKind2;
		aSource2 = pSource2;
		aDest2 = pDest2;
		aCounter1 = pCounter1;
		aCounter2 = pCounter2;
		aExc= pExc;
		
	}

	@Override
	public void execute() {

		((ElfengoldGame) Client.instance().getGame()).placeExchange(aKind1, aSource1, aDest1, aKind2, aSource2, aDest2, aCounter1, aCounter2, aExc);
		
	}

}
