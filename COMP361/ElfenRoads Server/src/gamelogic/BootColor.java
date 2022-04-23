package gamelogic;

import java.io.Serializable;

public enum BootColor implements Serializable {
	BLACK,
	BLUE,
	RED,
	YELLOW,
	PURPLE,
	GREEN,
	UNDECIDED;
	
	private boolean taken = false;
	
	public void setTaken(boolean b) {
		taken = b;
	}
	
	public boolean isTaken() {
		return taken;
	}
}
