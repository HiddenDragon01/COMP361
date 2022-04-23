package commands;



import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.Server;

public class ChooseCounterCommand extends RemoteCommand {
	
	private Counter aCounter;
	private String playerID;
	
	public ChooseCounterCommand(Counter pCounter, String playerId) {
		aCounter = pCounter;
		this.playerID = playerID;
	}
	

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("In choose counter command");
		Server.instance().getGame().getPlayer(playerID).addCounter(aCounter);
		
	}

}