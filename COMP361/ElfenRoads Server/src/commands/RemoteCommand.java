package commands;

import java.io.Serializable;

public abstract class RemoteCommand implements Serializable
{
	public abstract void execute();

}
