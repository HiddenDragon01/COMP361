package restAPI;



public class RestAPIState {
	
	private String name;
	private int locationX;
	private int locationY;
	private int points;
	private boolean turn;
	private String color;

	

	public void setId(String name) {
		this.name = name;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}
	
	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}
	
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public void setColor(String color) {
		this.color = color;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void changeTurn() {
		turn = !turn;
	}

	public String getName() {
		return name;
	}

	public int getLocationX() {
		return locationX;
	}
	
	public int getLocationY() {
		return locationY;
	}
	
	public int getPoints() {
		return points;
	}
	
	public boolean getTurn() {
		return turn;
	}
	
	public String getColor() {
		return color;
	}
	
	
	
	

}
