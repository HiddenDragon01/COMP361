package commands;

import gamelogic.TownName;

public class TownValueCommand extends RemoteCommand{
	
	TownName tn;
	int num;
	
	public TownValueCommand(TownName pTn, int pNum) {
		
		tn = pTn;
		num = pNum;
	}
	
	@Override
	public void execute() {
		
		
	}

}
