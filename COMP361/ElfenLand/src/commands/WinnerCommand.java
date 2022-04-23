package commands;

import gamelogic.Client;

public class WinnerCommand extends RemoteCommand{
	
	private String aPlayerName;
	
	public WinnerCommand(String pPlayerName) {
		aPlayerName = pPlayerName;
	}

	@Override
	public void execute() {
		Client.startWinnerGUI(aPlayerName);
	}

}
