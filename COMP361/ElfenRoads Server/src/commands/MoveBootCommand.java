package commands;

import gamelogic.TownName;

import java.util.ArrayList;



import gamelogic.Card;
import gamelogic.Client;
import gamelogic.Server;

public class MoveBootCommand extends RemoteCommand {
	
	TownName aTownName;
	ArrayList<Integer> aCardsUsed;

	public MoveBootCommand(TownName pTownName, ArrayList<Integer> pCardsUsed) {
		aTownName = pTownName;
		aCardsUsed = pCardsUsed;
		
	}

	@Override
	public void execute() {
		Server.instance().getGame().travelOnRoad(aTownName, aCardsUsed);
		
	}
	
	

}