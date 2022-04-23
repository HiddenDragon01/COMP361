package commands;

import gamelogic.Counter;
import gamelogic.Server;

public class DrawCounterCommand extends RemoteCommand{
	
//	Counter aCounter;
//	
//	public DrawCounterCommand(Counter pCounter)
//	{
//		aCounter = pCounter;
//	}
//
//	@Override
//	public void execute() {
//		
//		Server.instance().getGame().drawCounter(aCounter);
//		
//	}
	
int aCounter;
	
	public DrawCounterCommand(int pCounter)
	{
		aCounter = pCounter;
	}

	@Override
	public void execute() {
		
		System.out.println("In draw face up counter");
		
		Server.instance().getGame().drawCounter(aCounter);
		
	}

}
