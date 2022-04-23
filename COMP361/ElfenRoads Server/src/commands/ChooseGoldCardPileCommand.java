package commands;

import gamelogic.ElfengoldGame;
import gamelogic.Server;

public class ChooseGoldCardPileCommand extends RemoteCommand {

	@Override
	public void execute() {
		
		ElfengoldGame game = (ElfengoldGame) Server.instance().getGame();
		
		game.chooseGoldCardPile(); 
	}

		
}
