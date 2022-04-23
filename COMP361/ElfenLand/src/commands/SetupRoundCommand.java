package commands;

import gamelogic.Client;

public class SetupRoundCommand extends RemoteCommand{

	@Override
	public void execute() {
		Client.instance().getGame().setupFirstRound();
		
	}

}
