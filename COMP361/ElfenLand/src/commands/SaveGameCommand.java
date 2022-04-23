package commands;

import gamelogic.Client;

public class SaveGameCommand extends RemoteCommand{

	@Override
	public void execute() {

		Client.instance().startGameSavedGUI();
		
	}

}
