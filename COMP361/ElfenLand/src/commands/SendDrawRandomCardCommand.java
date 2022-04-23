package commands;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class SendDrawRandomCardCommand extends RemoteCommand {

	Card aCard;
	
	
	
	public SendDrawRandomCardCommand(Card pCard) {
		
		aCard = pCard;
		
	}

	@Override
	public void execute() {
		
		Client.instance().getGame().drawCard(aCard);
		
	}

}
