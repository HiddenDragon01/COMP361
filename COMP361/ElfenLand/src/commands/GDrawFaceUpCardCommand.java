package commands;



public class GDrawFaceUpCardCommand extends RemoteCommand{

	String aplayerName;
	int aindex;
	
	public GDrawFaceUpCardCommand(String pplayerName,int pindex)
	{
		aplayerName = pplayerName;
		aindex = pindex;
	}

	@Override
	public void execute() {
		
		
	}


}
