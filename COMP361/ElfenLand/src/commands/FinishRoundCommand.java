package commands;

import gamelogic.Client;

public class FinishRoundCommand extends RemoteCommand {

	@Override
	public void execute() {
		Client.instance().getGame().finishRound();
		
	}

}
