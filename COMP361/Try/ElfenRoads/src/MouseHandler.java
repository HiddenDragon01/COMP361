
import org.minueto.handlers.MinuetoMouseHandler;

public class MouseHandler implements MinuetoMouseHandler{
	
	private int positionX;
	private int positionY;
	
	public void handleMousePress(int x, int y, int button) {
        //System.out.println("Mouse click on button " + button + " at " + x + "," + y);
    }

    public void handleMouseRelease(int x, int y, int button) {
        //System.out.println("Mouse release on button " + button + " at " + x + "," + y);
        // Only prepare for translation if left button was clicked 
    	// and if the region of interest is selected
    	if (button == 1)
        {
    		positionX = x;
			positionY = y;
        }
    }
    
    public int getX() {
    	return positionX;
    }
    
    public int getY() {
    	return positionY;
    }
    
    public void handleMouseMove(int x, int y) {
        // Not doing anything on this event.
    }
    
    public int computeY(int x1, int y1, int x)
	{
		// Compute y=ax+b
		float a = (float) (positionY-y1)/ (positionX-x1); // compute slope a
		float b = y1 - (float) a*x1; // compute intercept b
		
		return (int) ((int) ((float) a*x)+b);
	}
    
    public void clear()
	{
		positionX = 0;
		positionY = 0;
	}
	
	

}
