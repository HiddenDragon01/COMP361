import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player 
{
	private final String username;
	private final String password;
	private final int id;
	private PlayerStatus status = PlayerStatus.OFFLINE;
	private BootColor bootColor;
	private Boot aBoot;
	private int positionX;
	private int positionY;
	private int points = 1;
	private int gold = 0;
	private TownName currentTown = TownName.ELVENHOLD;
	private List<TownName> visitedTowns = new ArrayList<>();
	
	public Player(String username, String password, int id, Boot pBoot)
	{
		this.username = username;
		this.password = password;
		this.id = id;
		visitedTowns.add(currentTown);
		bootColor = pBoot.getColor();
		aBoot = pBoot;
	}
	
	public String getName() {
		return this.username;
	}
	
	public void setBootColor(BootColor bootColor)
	{
		this.bootColor = bootColor;
	}
	
	public BootColor getBootColor()
	{
		return bootColor;
	}
	
	public void addPoint()
	{
		points++;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void addGold(int goldValue)
	{
		gold += goldValue;
	}
	
	public int getGold()
	{
		return gold;
	}
	
	public void setStatus(PlayerStatus status)
	{
		this.status = status;
	}
	
	public PlayerStatus getStatus()
	{
		return status;
	}
	
	
	public boolean hasVisited(TownName town)
	{
		return visitedTowns.contains(town);

	}
	
	public void visitTown(TownName town)
	{
		if(!hasVisited(town))
		{
			addPoint();
			visitedTowns.add(town);
			
		}
		setCurrentTown(town);
	}
	
	public void setCurrentTown(TownName town)
	{
		currentTown = town;
	}
	
	public TownName getCurrentTown()
	{
		return currentTown;
	}
	
	public void setCurrentXYPosition(int X, int Y)
	{
		positionX = X;
		positionY = Y;
	}
	
	public int getCurrentXPosition()
	{
		return positionX;
	}
	
	public int getCurrentYPosition()
	{
		return positionY;
	}
	
	public List<TownName> getVisitedTowns()
	{
		return Collections.unmodifiableList(visitedTowns);
	}

	
}
