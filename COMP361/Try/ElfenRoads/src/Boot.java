import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*; 

public class Boot {
	private MinuetoImage image;
	private String player; //string for now but should be its own class
	private Town town; //string for now but should be its own class
	private int positionX;
	private int positionY;
	private int points = 1;
	private BootColor bootColor;
	

	public Boot(MinuetoImage pImage, Town pTown, BootColor pBootColor) {
		image = pImage;
		town = pTown;
		bootColor = pBootColor;
	}
	
	public Town getTown() {
		return town;
	}
	
	public MinuetoImage getImage() {
		return image;
	}
	
	public void setTown(Town pTown) {
		town = pTown;
	}
	
	public int getX(){
		return positionX;
	}
	
	public int getY(){
		return positionY;
	}
	
	public void setXY(int pX, int pY) {
		positionX = pX;
		positionY = pY;
		return;
	}
	
	public void increasePoints() {
		points++;
		return;
	}
	
	public int getPoints() {
		return points;
	}
	
	public BootColor getColor() {
		return bootColor;
	}
	
	
}

