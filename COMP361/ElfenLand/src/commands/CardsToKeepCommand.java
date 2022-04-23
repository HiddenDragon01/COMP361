package commands;

import java.util.ArrayList;

import gamelogic.Client;

public class CardsToKeepCommand extends RemoteCommand{

	ArrayList<Integer> aCardIndexes = new ArrayList<>();
	
	public CardsToKeepCommand(ArrayList<Integer> pCardIndexes) {
		aCardIndexes = pCardIndexes;
	}
	@Override
	public void execute() {
		Client.instance().getGame().chooseCardsToKeep(aCardIndexes);
		
	}

}
