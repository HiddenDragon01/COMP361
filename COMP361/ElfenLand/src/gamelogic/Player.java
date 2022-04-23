package gamelogic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
//import java.util.Optional;

public class Player implements Serializable {
	private String name = "";
	private String id = "";
	private BootColor boot = BootColor.UNDECIDED;
	private TownName location = TownName.ELVENHOLD;
	private TownName destination;
	private int goldCoins = 0;
	private boolean drewGoldCard = false;
	final private ArrayList<Card> ownedCards = new ArrayList<>();
	final private ArrayList<Counter> ownedCounters = new ArrayList<>();
	final private HashSet<TownName> visited = new HashSet<>(Arrays.asList(TownName.ELVENHOLD));
//	final private Optional<TownName> townCard = Optional.empty();
	
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setDestination(TownName dest) {
		this.destination = dest;
		return;
	}
	
	public TownName getDestination() {
		return this.destination;
	}
	
	public HashSet<TownName> getVisited(){
		return new HashSet<TownName>(visited);
	}
	public BootColor getBoot() {
		return boot;
	}
	
	public void setBoot(BootColor bc) {
		boot = bc;
		boot.setTaken(true);
	}
	
//	public boolean hasTownCard()
//	{
//		return townCard.isPresent();
//	}
//	
//	public TownName getTownCard()
//	{
//		return townCard.get();
//	}
	
	public boolean hasBoot()
	{
		return boot != BootColor.UNDECIDED;
	}
		
	
	public boolean hasElvenWitch()
	{
		for (Card c: ownedCards)
		{
			if (c.getCardKind() == CardKind.ELVENWITCH)
			{
				return true;
			}
		}
		
		return false;
			
	}

	
	public int getPoints()
	{
		return visited.size();
	}
	
	public void setLocation(TownName t) {
		this.location = t;
		this.visited.add(t);
		return;
	}
	
	public TownName getLocation() {
		return location;
	}
	
	public String getPlayerId() {
		return id;
	}
	
	public void addCounter(Counter c) {
		ownedCounters.add(c);
		//c.setOwner(this);
		return;
	}
	
	public ArrayList<Counter> getCounters(){
		ArrayList<Counter> returnList = new ArrayList<>(ownedCounters);
		return returnList;
	}
	
	public ArrayList<Card> getCards(){
		ArrayList<Card> returnList = new ArrayList<>(ownedCards);
		return returnList;
	}
	
	public Card removeCardByIndex(int i) {
		return ownedCards.remove(i);
	}
	
	public Counter removeCounterByIndex(int i) {
		return ownedCounters.remove(i);
	}
	
	public void removeCounter(Counter c) {
		ownedCounters.remove(c);
//		c.removeOwner();
		return;
	}
	
	public int getNumCounters() {
		return ownedCounters.size();
	}
	
	public int getNumCards() {
		return ownedCards.size();
	}
	
	public void receiveCard(Card c) 
	{
		ownedCards.add(c);
		//c.setOwner(this);
		return;
	}
	
	public void removeCard(Card c) {
		ownedCards.remove(c);
		c.removeOwner();
		return;
	}
	
	public int getNumVisited() {
		return visited.size();
	}
	
	public void addGoldCoins(int coins) {
		goldCoins += coins;
	}
	
	public void removeGoldCoins(int coins) {
		goldCoins -= coins;
	}
	
	public void setGoldCoins(int coins) {
		goldCoins = coins;
	}
	
	public int getGoldCoins() {
		return goldCoins;
	}
	
	protected void clearGold() {
		goldCoins = 0;
	}
	
	protected void setDrewGoldCard(boolean b) {
		drewGoldCard = b;
	}
	
	protected boolean getDrewGoldCard() {
		return drewGoldCard;
	}
}
