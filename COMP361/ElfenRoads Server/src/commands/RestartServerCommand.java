package commands;

import gamelogic.Server;

public class RestartServerCommand extends RemoteCommand{

	@Override
	public void execute() {

		Server.instance().serverChannel.resetServer();
		
	}

}
