package commands;


import gamelogic.ElfengoldGame;
import gamelogic.EndTravelOption;
import gamelogic.Server;

public class PassTravelCommand extends RemoteCommand {
	
	EndTravelOption aChoice;
	
	public PassTravelCommand(EndTravelOption pChoice) {
		
		aChoice = pChoice;
		
	}

	@Override
	public void execute() {
		
		ElfengoldGame game = (ElfengoldGame) Server.instance().getGame();
		
		game.passTravel(aChoice);
		
	}

}
