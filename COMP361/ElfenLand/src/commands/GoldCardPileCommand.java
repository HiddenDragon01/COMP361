package commands;

import java.util.ArrayList;

import gamelogic.Card;
import gamelogic.Client;
import gamelogic.ElfengoldGame;

public class GoldCardPileCommand extends RemoteCommand{
	
	int aGoldCards;
	
	public GoldCardPileCommand(int pGoldCards) {
		
		aGoldCards = pGoldCards;
		
	}
	
	@Override
	public void execute() {
		
		Client.instance().getGame().setGoldCardPile(aGoldCards);
		
	}

}
