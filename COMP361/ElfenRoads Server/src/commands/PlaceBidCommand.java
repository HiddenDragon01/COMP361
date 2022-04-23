package commands;

import gamelogic.ElfengoldGame;
import gamelogic.Server;

public class PlaceBidCommand extends RemoteCommand{
	
	int aBid;
	
	public PlaceBidCommand(int pBid) {
		aBid = pBid;
	}
	
	@Override
	public void execute() {
		
		ElfengoldGame game = (ElfengoldGame) Server.instance().getGame();
		
		game.placeBid(aBid);
		
	}

}
