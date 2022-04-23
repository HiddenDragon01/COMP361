package commands;

import gamelogic.Card;

public class SendFaceUpCardsCommand extends RemoteCommand{

	
	Card aCard;
	
	public SendFaceUpCardsCommand(Card pCard) {
		
		aCard = pCard;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
