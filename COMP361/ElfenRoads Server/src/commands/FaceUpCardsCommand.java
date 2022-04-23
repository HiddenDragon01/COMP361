package commands;

import java.util.ArrayList;

import gamelogic.Card;

public class FaceUpCardsCommand extends RemoteCommand {
	
	ArrayList<Card> aFaceUpCards;
	
	public FaceUpCardsCommand(ArrayList<Card> pFaceUpCards) {
		
		aFaceUpCards = pFaceUpCards;
	}
	
	@Override
	public void execute() {
		
		
	}

}
