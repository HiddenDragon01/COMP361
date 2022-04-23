package commands;

import gamelogic.Client;
import gamelogic.Counter;


public class SendDrawRandomCounterCommand extends RemoteCommand{

	Counter aCounter;
	
	public SendDrawRandomCounterCommand(Counter pCounter)
	{
		aCounter = pCounter;
	}
	
	@Override
	public void execute() {

		Client.instance().getGame().drawCounter(aCounter);
		
	}


}
