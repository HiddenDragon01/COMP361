package commands;

import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class StartChooseVisibleCounterCommand extends RemoteCommand{

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		Client.instance().getGame().startChooseVisibleCounterPhase();
	}

}
