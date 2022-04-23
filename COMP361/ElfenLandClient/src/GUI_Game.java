import java.util.ArrayList;
import java.util.Hashtable;


import org.minueto.*;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoFrame;

public class GUI_Game {
	//this is just cause i wanted to test the choosing a boot with no communication
	public static BootColor boot = BootColor.UNDECIDED;
	//
	//this is to look up boot images by the color
	//so you can be like for each player, get(player bootcolor) and draw at (player location)
	private static Hashtable<BootColor, MinuetoImage> bootImages = new Hashtable<BootColor, MinuetoImage>(); 
	//this i'm not sure, i was just thinking about how to implement the town coordinates
	//and i was thinking maybe by looking up their x and y coordinate
	private static Hashtable<TownName, Integer> townXCoords = new Hashtable<>();
	private static Hashtable<TownName, Integer>	townYCoords = new Hashtable<>();
	static {
		townXCoords.put(TownName.ELVENHOLD, 850);
		townYCoords.put(TownName.ELVENHOLD, 250);
	}
	
	
	public static void main(String[] args) {
		
		//background variables
		MinuetoFrame window = new MinuetoFrame(1400, 700, true);
		MinuetoImage background;
		
		//boot variables
		MinuetoImage redBootPic;
		MinuetoImage blueBootPic;
		MinuetoImage blackBootPic;
		MinuetoImage yellowBootPic;
		MinuetoImage purpleBootPic;
		MinuetoImage greenBootPic;
		
		//transportation grid that i want to put somewhere
		MinuetoImage transportationGrid;
		
		//font
        MinuetoFont fontArial14;
        
        //getting the directory
        String imgDir = System.getProperty("user.dir") + "/GameAssets/";
        
        //getting background image
		try {
			background = new MinuetoImageFile(imgDir + "Background.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		
		//getting the transportation grid 
		try {
			transportationGrid = new MinuetoImageFile(imgDir + "grid.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		transportationGrid = transportationGrid.scale(0.65,0.65);
		
		//getting all the boots and putting them in bootImages (to look up appropriate image by bootcolor)
		try {
			redBootPic = new MinuetoImageFile(imgDir + "Boots/boot-red.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		redBootPic = redBootPic.scale(0.125, 0.125);
		bootImages.put(BootColor.RED, redBootPic);
		try {
			blueBootPic = new MinuetoImageFile(imgDir + "Boots/boot-blue.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		blueBootPic = blueBootPic.scale(0.125, 0.125);
		bootImages.put(BootColor.BLUE, blueBootPic);
		try {
			blackBootPic = new MinuetoImageFile(imgDir + "Boots/boot-black.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		blackBootPic = blackBootPic.scale(0.125, 0.125);
		bootImages.put(BootColor.BLACK, blackBootPic);
		try {
			yellowBootPic = new MinuetoImageFile(imgDir + "Boots/boot-yellow.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		yellowBootPic = yellowBootPic.scale(0.125, 0.125);
		bootImages.put(BootColor.YELLOW, yellowBootPic);
		try {
			purpleBootPic = new MinuetoImageFile(imgDir + "Boots/boot-purple.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		purpleBootPic = purpleBootPic.scale(0.125, 0.125);
		bootImages.put(BootColor.PURPLE,purpleBootPic);
		try {
			greenBootPic = new MinuetoImageFile(imgDir + "Boots/boot-green.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}
		greenBootPic = greenBootPic.scale(0.125, 0.125);
		bootImages.put(BootColor.GREEN, greenBootPic);
		
		//setting up the mouse handler for choose boot
		MinuetoEventQueue queue;
	    queue = new MinuetoEventQueue();
	    ChooseBootMouseHandler chooseBootMouseHandler = new ChooseBootMouseHandler();
	    chooseBootMouseHandler.registerWindow(window, queue);
	    window.registerMouseHandler(chooseBootMouseHandler, queue); 
	    
	    //making a font if u need to write something
	    fontArial14 = new MinuetoFont("Arial",22,true, false); 
	     
	    
	     //setting the window to visible
	     window.setVisible(true);
	     
	     
	     while(true) { 
	    	 window.draw(background, 0, 0);
	    	 //window.draw(transportationGrid, 1100, 230); this is to include the transportation grid somewhere but not sure with background
	    	 
	    	 //this needs to be changed to if (gamephase == chooseboot)
	    	 //if it's taken, doesn't draw the boot anymore
	    	 if(boot == BootColor.UNDECIDED) {
	    		 if(!BootColor.RED.isTaken()) {
	    			 window.draw(redBootPic.scale(3,3), 200, 300);
	    		 }
	    		 if(!BootColor.BLUE.isTaken()) {
	    			 window.draw(blueBootPic.scale(3,3), 350, 300);
	    		 }
	    		 if(!BootColor.BLACK.isTaken()) {
	    			 window.draw(blackBootPic.scale(3,3), 500, 300);
	    		 }
	    		 if(!BootColor.YELLOW.isTaken()) {
	    			 window.draw(yellowBootPic.scale(3,3), 650, 300);
	    		 }
	    		 if(!BootColor.PURPLE.isTaken()) {
	    			 window.draw(purpleBootPic.scale(3,3), 800, 300);
	    		 }
	    		 if(!BootColor.GREEN.isTaken()) {
	    			 window.draw(greenBootPic.scale(3,3), 950, 300);
	    		 }
   	    	 }
	    	 else {
	    		 window.draw(bootImages.get(boot), townXCoords.get(TownName.ELVENHOLD), townYCoords.get(TownName.ELVENHOLD));
	    	 }
	    	 
	    	 
	    	 while(queue.hasNext()) {
	                queue.handle();
	               // MinuetoText text = new MinuetoText("boot color is "+ chooseBootMouseHandler.getX()+ chooseBootMouseHandler.getY(),fontArial14,MinuetoColor.WHITE);
            		//window.draw(text, chooseBootMouseHandler.getX(), chooseBootMouseHandler.getY());
	                chooseBootMouseHandler.clear();
	    	 }
	    	 window.render();
	         Thread.yield();
	         
	         window.clear();
	     }
	}
}