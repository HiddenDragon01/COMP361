package commands;

import gamelogic.Counter;
import gamelogic.Server;

public class DrawFaceUpCardCommand extends RemoteCommand{
	
	
	int aCard;
	
	public DrawFaceUpCardCommand(int pCard)
	{
		aCard = pCard;
	}

	@Override
	public void execute() {
		
		System.out.println("In draw face up counter");
		
		Server.instance().getGame().drawCard(aCard);
		
	}

}
