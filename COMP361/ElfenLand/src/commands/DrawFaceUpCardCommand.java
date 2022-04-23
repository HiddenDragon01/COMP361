package commands;


import gamelogic.Client;
import gamelogic.Counter;

public class DrawFaceUpCardCommand extends RemoteCommand{
	
	
	int aCard;
	
	public DrawFaceUpCardCommand(int pCard)
	{
		aCard = pCard;
	}

	@Override
	public void execute() {
		
		System.out.println("In draw face up counter");

	}


}