package commands;

import gamelogic.TownName;

import java.util.ArrayList;

import gamelogic.Card;
import gamelogic.Client;

public class MoveBootCommand extends RemoteCommand {
	
	TownName aTownName;
	ArrayList<Integer> aCardsUsed;

	public MoveBootCommand(TownName pTownName, ArrayList<Integer> pCardsUsed) {
		aTownName = pTownName;
		aCardsUsed = pCardsUsed;
		
	}

	@Override
	public void execute() {
		Client.instance().getGame().travelOnRoad(aTownName, aCardsUsed);
		
	}
	
	

}