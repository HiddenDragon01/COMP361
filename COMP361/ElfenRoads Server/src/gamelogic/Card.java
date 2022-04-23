package gamelogic;
import java.io.Serializable;
import java.util.Optional;

public class Card implements Serializable  {
	private final CardKind cardKind;
	private Optional<Player> owner;
	
	public Card(CardKind kind) {
		cardKind = kind;
	}
	
	public CardKind getCardKind() {
		return cardKind;
	}
	
	public void setOwner(Player p) {
//		this.owner = Optional.of(p);
	}
	
	public void removeOwner() {
//		this.owner = Optional.empty();
	}
	
}
