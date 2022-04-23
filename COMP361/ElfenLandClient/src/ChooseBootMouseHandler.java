import org.minueto.MinuetoEventQueue;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoFrame;

public class ChooseBootMouseHandler implements MinuetoMouseHandler{
	
	private int positionX;
	private int positionY;
	private MinuetoFrame currentWindow;
	private MinuetoEventQueue currentQueue;
	
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
			
			
			if(y>=300 && y<=500) {
				if(x>=200 && x<=325) {
					
					//beginning of implementation for testing purposes
					GUI_Game.boot = BootColor.RED;
					BootColor.RED.setTaken(true);
					// end
					
					//what would actually be called is ->
					//boolean moveSent = chooseBoot(BootColor.RED);
					//if (moveSent){
						//unregister window code
					//}
					//but i'm not sure what instance of clientGame it would be/where it would get it (i guess somewhere in GUI_client)
					
					//then once you choose a boot, it unregisters the mouse handler so you can't send more stuff
					currentWindow.unregisterMouseHandler(this, currentQueue);
					MouseHandler mouseHandler = new MouseHandler();
					currentWindow.registerMouseHandler(mouseHandler, currentQueue);
					//and same code for the other colors
				}
				if(x>=350 && x<=475) {
					GUI_Game.boot = BootColor.BLUE;
					currentWindow.unregisterMouseHandler(this, currentQueue);
					MouseHandler mouseHandler = new MouseHandler();
					currentWindow.registerMouseHandler(mouseHandler, currentQueue);
				}
				if(x>=500 && x<=625) {
					GUI_Game.boot = BootColor.BLACK;
					currentWindow.unregisterMouseHandler(this, currentQueue);
					MouseHandler mouseHandler = new MouseHandler();
					currentWindow.registerMouseHandler(mouseHandler, currentQueue);
				}
				if(x>=650 && x<=775) {
					GUI_Game.boot = BootColor.YELLOW;
					currentWindow.unregisterMouseHandler(this, currentQueue);
					MouseHandler mouseHandler = new MouseHandler();
					currentWindow.registerMouseHandler(mouseHandler, currentQueue);
				}
				if(x>=800 && x<=925) {
					GUI_Game.boot = BootColor.PURPLE;
					currentWindow.unregisterMouseHandler(this, currentQueue);
					MouseHandler mouseHandler = new MouseHandler();
					currentWindow.registerMouseHandler(mouseHandler, currentQueue);
				}
				if(x>=950 && x<=1075) {
					GUI_Game.boot = BootColor.GREEN;
					currentWindow.unregisterMouseHandler(this, currentQueue);
					MouseHandler mouseHandler = new MouseHandler();
					currentWindow.registerMouseHandler(mouseHandler, currentQueue);
				}
				
			}
        }
    }
    
    //this is to set the window and queue so u can unregister
    public void registerWindow(MinuetoFrame window, MinuetoEventQueue queue) {
    	currentWindow = window;
    	currentQueue = queue;
    	return;
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
    
    
    public void clear()
	{
		positionX = 0;
		positionY = 0;
	}

}
