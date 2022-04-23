import java.awt.Color;
import java.awt.Window;

import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;


public class MovingMonkey implements MinuetoKeyboardHandler, MinuetoMouseHandler
{
	private int aFinalPositionX = 0;
	private int aFinalPositionY = 0;
	
	public static void main(String[] args)
	{
		int monkeyPositionX;
		int monkeyPositionY;
		
		MinuetoWindow window; 
        MinuetoEventQueue queue;
        MinuetoImage monkey;
        MinuetoImage background;
        MinuetoImage square;

        window = new MinuetoFrame(640, 480, true); 
        queue = new MinuetoEventQueue();
        MovingMonkey mt = new MovingMonkey();
        window.registerKeyboardHandler(new MovingMonkey(), queue);
        window.registerMouseHandler(mt, queue);
        
        String imgDir = System.getProperty("user.dir") + "/MovingMokeyImages/";
        
        try {
            monkey = new MinuetoImageFile(imgDir + "cuteMonkey.jpg");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }
        
        try {
        	background = new MinuetoImageFile(imgDir + "forest.jpg");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }
        
        try {
        	square = new MinuetoImageFile(imgDir+ "square.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }
        
        monkeyPositionX = 10;
        monkeyPositionY = 10;
        int monkeyHalfWidth = monkey.getWidth() / 2;
        int monkeyHalfHeight = monkey.getHeight() / 2;

        window.setVisible(true);
        window.draw(background, 300, 300);
        window.draw(square, 100, 100);
        window.draw(monkey, monkeyPositionX, monkeyPositionY);
        
          
        while(true) { 
        	
        	
            while(queue.hasNext()) {
                queue.handle();
                if(mt.getFinalPositionX() != 0)
                {
          
                	// On mouse click, translate image (images's center)
                	// to location of mouse release
                	int x = monkeyPositionX;
                	while(x != mt.getFinalPositionX())
                	{
                		window.clear();
	                    window.draw(background, 300, 300);
	                    window.draw(square, 100, 100);
	                    window.draw(monkey, 
	                			x - monkeyHalfWidth, 
	                			mt.computeY(monkeyPositionX, monkeyPositionY, x) - monkeyHalfHeight);
	                    window.render();
	                    Thread.yield();
	                    if(mt.getFinalPositionX() > monkeyPositionX)
	                    {
	                    	x++;
	                    }
	                    else
	                    {
	                    	x--;
	                    }
	                   
                	}
                	
                	monkeyPositionX = mt.getFinalPositionX();
                    monkeyPositionY = mt.getFinalPositionY();
                	
                    
                    
                	mt.clear();
                }
                

            }
            window.render();
            Thread.yield();
        }
        
        
        
	}
	
	/**
	 * @param x1 Starting x position 
	 * @param y1 Starting y position
	 * @param x x-wise translation
	 * @return
	 */
	public int computeY(int x1, int y1, int x)
	{
		// Compute y=ax+b
		float a = (float) (aFinalPositionY-y1)/ (aFinalPositionX-x1); // compute slope a
		float b = y1 - (float) a*x1; // compute intercept b
		
		return (int) ((int) ((float) a*x)+b);
	}
	/**
	 * Resets the final position in x and y to zero, used when translation of object is complete
	 */
	public void clear()
	{
		aFinalPositionX = 0;
		aFinalPositionY = 0;
	}
	
	public int getFinalPositionX()
	{
		return aFinalPositionX;
	}
	
	public int getFinalPositionY()
	{
		return aFinalPositionY;
	}
	
	public void handleKeyPress(int value) {
        switch(value) {
            case MinuetoKeyboard.KEY_Q:
                System.exit(0);
                break;
            default:
                // Ignore all other keys
        }
    }

    public void handleKeyRelease(int value) {
        // Do nothing on key release
    }

    public void handleKeyType(char key) {
        // Do nothing on key type
    }
    
    public void handleMousePress(int x, int y, int button) {
        //System.out.println("Mouse click on button " + button + " at " + x + "," + y);
    }

    public void handleMouseRelease(int x, int y, int button) {
        //System.out.println("Mouse release on button " + button + " at " + x + "," + y);
        // Only prepare for translation if left button was clicked 
    	// and if the region of interest is selected
    	if (button == 1)
        {
    		if ((x > 100) && (x < 400) && (y > 100) && (y < 400))
    		{
    			aFinalPositionX = x;
    			aFinalPositionY = y;
    		}
        }
    }
    
    public void handleMouseMove(int x, int y) {
        // Not doing anything on this event.
    }
}
	
