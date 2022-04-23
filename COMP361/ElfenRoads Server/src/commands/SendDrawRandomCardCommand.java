package commands;

import gamelogic.Card;

public class SendDrawRandomCardCommand extends RemoteCommand{
	
	Card aCard;
	
	public SendDrawRandomCardCommand(Card pCard) {
		
		aCard = pCard;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
		
	}
	
	

}
