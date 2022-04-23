package commands;

import gamelogic.ElfengoldGame;
import gamelogic.Server;

public class GDrawFaceUpCardCommand extends RemoteCommand {
	
	String aplayerName;
	int aindex;
	
	public GDrawFaceUpCardCommand(String pplayerName,int pindex)
	{
		aplayerName = pplayerName;
		aindex = pindex;
	}

	@Override
	public void execute() {
		
		ElfengoldGame game = (ElfengoldGame) Server.instance().getGame();
		
		game.drawCard(aindex);
	}

}
