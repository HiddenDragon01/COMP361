package commands;

import gamelogic.Client;
import gamelogic.ElfengoldGame;
import gamelogic.EndTravelOption;

public class PassTravelCommand extends RemoteCommand{
	
	EndTravelOption aChoice;
	
	public PassTravelCommand(EndTravelOption pChoice) {
		
		aChoice = pChoice;
		
	}

	@Override
	public void execute() {
		
		ElfengoldGame game = (ElfengoldGame) Client.instance().getGame();
		
		game.passTravel(aChoice);
		
	}
	

}
