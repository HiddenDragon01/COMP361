package commands;

import gamelogic.Client;

public class StartAuctionPhaseCommand extends RemoteCommand{

	@Override
	public void execute() {
//		Client.startChooseCounter();
//		
//		System.out.println("Sart choose counter phase command exectued!");
		
		Client.instance().restartAuctionGUI();
		
	}

}
