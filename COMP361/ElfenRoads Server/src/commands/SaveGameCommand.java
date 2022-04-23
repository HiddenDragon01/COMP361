package commands;

import gamelogic.Server;

public class SaveGameCommand extends RemoteCommand{

	@Override
	public void execute() {

		Server.instance().saveGame();
		
	}

}
