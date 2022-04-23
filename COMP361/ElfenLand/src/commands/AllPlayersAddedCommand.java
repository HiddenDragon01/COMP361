package commands;

import gamelogic.Client;

public class AllPlayersAddedCommand extends RemoteCommand{

	@Override
	public void execute() {
		
		System.out.println("In the all players added command!");

		Client.startChooseBoot();

		
	}


}
