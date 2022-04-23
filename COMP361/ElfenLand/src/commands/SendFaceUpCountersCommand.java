package commands;

import java.util.ArrayList;

import gamelogic.Client;
import gamelogic.Counter;

public class SendFaceUpCountersCommand extends RemoteCommand{
	
	private ArrayList<Counter> aFaceUpCounters;
	
	public SendFaceUpCountersCommand(ArrayList<Counter> pFaceUpCounters)
	{
		aFaceUpCounters = pFaceUpCounters;
	}
	
//	private Counter a;
//	
//	public SendFaceUpCountersCommand(Counter c)
//	{
//		a = c;
//	}

	@Override
	public void execute() {
		System.out.println("CLIENT: in send face up counters command");
		Client.instance().getGame().setFaceUpCounters(aFaceUpCounters);
	}

}
