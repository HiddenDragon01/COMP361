package commands;

import gamelogic.Server;

public class SetGoldCommand extends RemoteCommand {

	String aPlayer;
	int aGoldAmount;
	
	
	public SetGoldCommand(String pPlayer, int pGoldAmount) {
		
		aPlayer = pPlayer;
		aGoldAmount = pGoldAmount;
		
	}
	
	@Override
	public void execute() {
		
		// ??? player ID = aPlayer
		Server.instance().getGame().getPlayer(aPlayer).setGoldCoins(aGoldAmount);
		
	}
}
