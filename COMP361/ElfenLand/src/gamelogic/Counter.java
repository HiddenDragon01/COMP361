package gamelogic;
import java.io.Serializable;

public class Counter implements Serializable  {
	final private CounterKind counterKind;
	private boolean visibility;
	//private Optional<Player> owner;
	
	public Counter(CounterKind kind, boolean visible) {
		visibility = visible;
		counterKind = kind;
	}
	
	public void setVisibility(boolean b) {
		this.visibility = b;
		return;
	}
	
//	public void setOwner(Player p) {
//		this.owner = Optional.of(p);
//		return;
//	}
//	
//	public void removeOwner() {
//		this.owner = Optional.empty();
//		return;
//	}
	
	public CounterKind getCounterKind() {
		return counterKind;
	}
	
	public boolean isVisible()
	{
		return visibility;
	}
}
