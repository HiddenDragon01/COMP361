package commands;

import gamelogic.Client;

public class SetGlodCommand extends RemoteCommand {
	String aPlayer;
	int aGoldAmount;
	
	
	public SetGlodCommand(String pPlayer, int pGoldAmount) {
		
		aPlayer = pPlayer;
		aGoldAmount = pGoldAmount;
		
	}
	
	@Override
	public void execute() {
		
		// ??? player ID = aPlayer
		Client.instance().getGame().getPlayer(aPlayer).setGoldCoins(aGoldAmount);
		
	}
}
