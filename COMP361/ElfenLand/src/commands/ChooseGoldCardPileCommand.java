package commands;

import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class ChooseGoldCardPileCommand extends RemoteCommand {

	@Override
	public void execute() {
		
		Client.instance().getGame().chooseGoldCardPile(); 
		
	}
	
	

}
