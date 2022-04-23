package commands;

import gamelogic.Server;

public class DrawRandomCounterCommand extends RemoteCommand{

	@Override
	public void execute() {
		
		System.out.println("SERVER: IN DRAW RANDOMCOUNTER COMMAND");

		Server.instance().getGame().drawRandomCounter();
		
	}

}
