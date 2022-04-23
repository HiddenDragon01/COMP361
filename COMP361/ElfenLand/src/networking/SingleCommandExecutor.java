package networking;

import commands.RemoteCommand;

public class SingleCommandExecutor implements Runnable {
	
	protected RemoteCommand command;
	
	public SingleCommandExecutor(RemoteCommand cmd)
	{
		command = cmd;
	}

	@Override
	public void run() {
		
		System.out.println("Single Command Executor up and running! Gonna execute now");
		
		command.execute();
		
		System.out.println("Single command executor command finished executing");
	}

}
