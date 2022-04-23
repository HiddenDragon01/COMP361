import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;

public class Boot {
	private MinuetoImage image;
	private String player; // string for now but should be its own class
	private String town; // string for now but should be its own class
	private String bootColor;
	private int positionX;
	private int positionY;
	private int points = 1;


	public Boot(String color, int x, int y, String pTown, int points_initial) {
		town = pTown;
		bootColor = color;
		positionX = x;
		positionY = y;
		points = points_initial;
		return;
	}

	public String getTown() {
		return town;
	}

	public MinuetoImage getImage() {
		return image;
	}

	public void setTown(String pTown) {
		town = pTown;
	}

	public int getX() {
		return positionX;
	}

	public int getY() {
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

	public void setBootColor(String color) {
		bootColor = color;
	}

	public String getBootColor() {
		return bootColor;
	}

}
