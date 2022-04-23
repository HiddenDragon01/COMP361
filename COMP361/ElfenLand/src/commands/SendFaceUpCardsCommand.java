package commands;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class SendFaceUpCardsCommand extends RemoteCommand {
	
	Card aCard;
	
	public SendFaceUpCardsCommand(Card pCard) {
		
		aCard = pCard;
	}
	
	@Override
	public void execute() {
		
		ElfengoldGame game = (ElfengoldGame) Client.instance().getGame();
		game.drawCard(aCard);
		
	}

}
