package commands;

import gamelogic.Client;

public class PassTurnCommand extends RemoteCommand {


	@Override
	public void execute() {
		
		System.out.println("IN PASS TURN COMMAND");
		Client.instance().getGame().passTurn();
	}

}