package commands;

import java.util.ArrayList;

import gamelogic.Counter;

public class SendAuctionCountersCommand extends RemoteCommand{
	
	private ArrayList<Counter> aAuctionCounters;
	
	public SendAuctionCountersCommand(ArrayList<Counter> pAuctionCounters)
	{
		aAuctionCounters = pAuctionCounters;
	}

	@Override
	public void execute() {
		
		
	}

}
