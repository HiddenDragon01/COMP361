package commands;

import java.util.ArrayList;

import gamelogic.Card;

public class GoldCardPileCommand extends RemoteCommand{

	int aGoldCards;
	
	public GoldCardPileCommand(int pGoldCards) {
		
		aGoldCards = pGoldCards;
		
	}
	
	@Override
	public void execute() {
		
		
	}
	
}
