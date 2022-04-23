package gamelogic;

import java.util.ArrayList;

public class Trade {
	private Player p1;
	private String requestedItems1;
	private ArrayList<Card> offeredCards1 = new ArrayList<>();
	private ArrayList<Counter> offeredCounters1 = new ArrayList<>();
	private boolean p1locked = false;
	
	private Player p2;
	private String requestedItems2;
	private ArrayList<Card> offeredCards2 = new ArrayList<>();
	private ArrayList<Counter> offeredCounters2 = new ArrayList<>();
	private boolean p2locked = false;
	
	public void setP1(Player p) {
		p1 = p;
	}
	
	public void setP2(Player p) {
		p2 = p;
	}
	
	public Player getP1() {
		return p1;
	}
	
	public Player getP2() {
		return p2;
	}
	
	public void setRequestedItems1(String request) {
		requestedItems1 = request;
	}
	
	public void setRequestedItems2(String request) {
		requestedItems2 = request;
	}
	
	public void setOfferedCards1(ArrayList<Card> offeredCards) {
		offeredCards1 = new ArrayList<>(offeredCards);
	}
	
	public void setOfferedCards2(ArrayList<Card> offeredCards) {
		offeredCards2 = new ArrayList<>(offeredCards);
	}
	
	public void setOfferedCounters1(ArrayList<Counter> offeredCounters) {
		offeredCounters1 = new ArrayList<>(offeredCounters);
	}
	
	public void setOfferedCounters2(ArrayList<Counter> offeredCounters) {
		offeredCounters2 = new ArrayList<>(offeredCounters);
	}
	
	public String getRequestedItems1() {
		return requestedItems1;
	}
	
	public String getRequestedItems2() {
		return requestedItems2;
	}
	
	public ArrayList<Card> getOfferedCards1() {
		return new ArrayList<>(offeredCards1);
	}
	
	public ArrayList<Card> getOfferedCards2() {
		return new ArrayList<>(offeredCards2);
	}
	
	public ArrayList<Counter> getOfferedCounters1() {
		return new ArrayList<>(offeredCounters1);
	}
	
	public ArrayList<Counter> getOfferedCounters2() {
		return new ArrayList<>(offeredCounters2);
	}
	
	public void setP1locked(boolean b) {
		p1locked = b;
	}
	
	public void setP2locked(boolean b) {
		p2locked = b;
	}
	
	public boolean getP1locked() {
		return p1locked;
	}
	
	public boolean getP2locked() {
		return p2locked;
	}
	
	public void finalizeTrade() {
		for(Card card : offeredCards1) {
			p1.removeCard(card);
			p2.receiveCard(card);
		}
		for(Counter counter : offeredCounters1) {
			p1.removeCounter(counter);
			p2.addCounter(counter);
		}
		
		for(Card card : offeredCards2) {
			p2.removeCard(card);
			p1.receiveCard(card);
		}
		for(Counter counter : offeredCounters2) {
			p2.removeCounter(counter);
			p1.addCounter(counter);
		}
	}
	
	//my lil example code so i can explain how it works:)
	public static void main(String[] args) {
		//some example players
		Player examplePlayer1 = new Player();
		Player examplePlayer2 = new Player();
		
		//some example cards
		Card exampleCard1 = new TravelCard(TransportationKind.GIANTPIG);
		Card exampleCard2 = new TravelCard(TransportationKind.RAFT);
		Counter exampleCounter1 = new TravelCounter(TransportationKind.UNICORN, false);
		
		examplePlayer1.receiveCard(exampleCard1);
		examplePlayer1.addCounter(exampleCounter1);
		examplePlayer2.receiveCard(exampleCard2);
		
		System.out.println("Player 1 cards before trade:");
		for (Card c : examplePlayer1.getCards()) {
			System.out.println(((TravelCard) c).getTransportationKind());
		}
		
		System.out.println("Player 1 counters before trade:");
		for (Counter c : examplePlayer1.getCounters()) {
			System.out.println(((TravelCounter) c).getTransportationKind());
		}
		System.out.println("Player 2 cards before trade:");
		for (Card c : examplePlayer2.getCards()) {
			System.out.println(((TravelCard) c).getTransportationKind());
		}
		
		System.out.println("Player 2 counters before trade:");
		for (Counter c : examplePlayer2.getCounters()) {
			System.out.println(((TravelCounter) c).getTransportationKind());
		}
		
		//the first player is gonna propose a new trade
		Trade newTrade = new Trade();
		newTrade.setP1(examplePlayer1);
		
		//in a real scenario they have to request items but i'm just gonna have both players set their offer
		newTrade.setOfferedCards1(examplePlayer1.getCards());
		newTrade.setOfferedCounters1(examplePlayer1.getCounters());
		newTrade.setP1locked(true);
		
		//then player2 will put in their offer (as soon as an offer is put in, theyre locked. otherwise you can pass)
		newTrade.setOfferedCards2(examplePlayer2.getCards());
		newTrade.setP2locked(true);
		
		//then finalizing the trade
		if(newTrade.getP1locked() && newTrade.getP2locked() ) {
			for(Card card : newTrade.getOfferedCards1()) {
				examplePlayer1.removeCard(card);
				examplePlayer2.receiveCard(card);
			}
			for(Counter counter : newTrade.getOfferedCounters1()) {
				examplePlayer1.removeCounter(counter);
				examplePlayer2.addCounter(counter);
			}
			
			for(Card card : newTrade.getOfferedCards2()) {
				examplePlayer2.removeCard(card);
				examplePlayer1.receiveCard(card);
			}
			for(Counter counter : newTrade.getOfferedCounters2()) {
				examplePlayer2.removeCounter(counter);
				examplePlayer1.addCounter(counter);
			}
		}
		
		System.out.println("Player 1 cards after trade:");
		for (Card c : examplePlayer1.getCards()) {
			System.out.println(((TravelCard) c).getTransportationKind());
		}
		
		System.out.println("Player 1 counters after trade:");
		for (Counter c : examplePlayer1.getCounters()) {
			System.out.println(((TravelCounter) c).getTransportationKind());
		}
		System.out.println("Player 2 cards after trade:");
		for (Card c : examplePlayer2.getCards()) {
			System.out.println(((TravelCard) c).getTransportationKind());
		}
		
		System.out.println("Player 2 counters after trade:");
		for (Counter c : examplePlayer2.getCounters()) {
			System.out.println(((TravelCounter) c).getTransportationKind());
		}
	}

}
