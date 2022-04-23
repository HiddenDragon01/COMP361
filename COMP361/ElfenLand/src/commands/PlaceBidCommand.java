package commands;

import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class PlaceBidCommand extends RemoteCommand {

	int aBid;
	
	public PlaceBidCommand(int pBid) {
		aBid = pBid;
	}
	
	@Override
	public void execute() {
		
		System.out.println("In the place bid command on client");
		
		System.out.println(Client.instance().getGame().getClass().toString());
		
		Client.instance().getGame().placeBid(aBid);
		
	}
	
	

}
