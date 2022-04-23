package commands;

import gamelogic.Client;

public class StartDrawFaceUpCounterPhaseCommand extends RemoteCommand{

	@Override
	public void execute() {
//		Client.startChooseCounter();
//		
//		System.out.println("Sart choose counter phase command exectued!");
		
		System.out.println("starten choosen");
		
		Client.instance().getGame().startChooseCounterPhase();
		
	}

}
