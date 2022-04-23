package commands;


import gamelogic.Client;
import gamelogic.Counter;

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
//		System.out.println("In draw face up counter");
//		
//		Client.instance().getGame().drawCounter(aCounter);
//		
//		
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

	}


}
