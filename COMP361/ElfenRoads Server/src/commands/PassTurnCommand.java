package commands;

import gamelogic.Client;
import gamelogic.Server;

public class PassTurnCommand extends RemoteCommand {


	@Override
	public void execute() {
		
		System.out.println("IN PASS TURN COMMAND");
		Server.instance().getGame().passTurn();
	}

}