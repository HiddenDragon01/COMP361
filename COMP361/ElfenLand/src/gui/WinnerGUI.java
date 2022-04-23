package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoStopWatch;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoText;

import gamelogic.BootColor;
import gamelogic.Client;
import gamelogic.GamePhase;
import gamelogic.Player;
import gui.ChooseBootGUI.bootSelectionHandler;

public class WinnerGUI extends Thread {

	private final GameGUI gameGUI;
	private MinuetoImage confetti;
	private String winner;
	private int window_height;
	
	public WinnerGUI(GameGUI gameGUI, String playerName) 
	{
		
		this.gameGUI = gameGUI;
		
		window_height = gameGUI.window.getHeight();
		int window_width = gameGUI.window.getWidth();
		confetti = ImageLoader.getConfetti();
		winner = playerName;

		
	}


	
	public void run()
	{
		gameGUI.unregisterGlobalMouseHandlers();
		gameGUI.run();
		showConfetti(winner + " won the game!");
		
	}
	
	
	protected void showConfetti(String txt)
	{
		assert txt != null;
		
		MinuetoMouseHandler returnToLobby = new returnToLobbyMouseHandler();
		gameGUI.window.registerMouseHandler(returnToLobby, gameGUI.queue);
		
		// Separate input string where newlines characters are
		String[] lines = txt.split("\\r?\\n");

		// Create the text font
		MinuetoFont font = new MinuetoFont("Serif", 40, false, false); 
        
		// Create a list that stores the images for every text line, 
		// and find the width of the longest line (for dialog box scaling purposes)
		ArrayList<MinuetoImage> minuetoTexts = new ArrayList<>();
        int max_txt_width = 0;
        for (String line: lines) 
        { 
        	MinuetoImage minuetoText = new MinuetoText(line, font, MinuetoColor.BLACK);
        	minuetoTexts.add(minuetoText);
        	int txt_width = minuetoText.getWidth();
        	if (txt_width > max_txt_width)
        	{
        		max_txt_width = txt_width;
        	}
        }
        
        // Set the number of pixels between every text line, and the number of pixels that frame the text
        int text_Yspacing = 10;
        int text_frame = 40;
        int textHeight = minuetoTexts.get(0).getHeight();
        
        // Load dialog box
        MinuetoImage dialog_box = ImageLoader.get("paper-dialog.png");
        
        // Scale the dialog box based on text measurements
        int box_height = minuetoTexts.get(0).getHeight() * minuetoTexts.size()  + (minuetoTexts.size() - 1) * text_Yspacing + text_frame * 2 ;
        int box_width = max_txt_width + 100 ;
        
        ScalingFactor box_scale = new ScalingFactor(dialog_box.getWidth(), dialog_box.getHeight(), box_width, box_height);
        MinuetoImage scaled_dialog_box = dialog_box.scale(box_scale.factorX, box_scale.factorY);
        
        // Initialize time
        MinuetoStopWatch watch = new MinuetoStopWatch();
        watch.start();
        
        //confetti coordinates
        int y = 0;
        
        MinuetoImage background = ImageLoader.get("new-background.png");
        
        
       
        // Display the message for x seconds
        while (y-500 < window_height)
        {
        	gameGUI.window.draw(background, 0, 0);
        	for (Player p: gameGUI.game.getParticipants())
    		{
    			if (p != gameGUI.player)
    			{
    				gameGUI.drawInfoBox(p);
    			}	
    			

    		}
        	gameGUI.window.draw(scaled_dialog_box, gameGUI.window.getWidth() / 2 - scaled_dialog_box.getWidth() / 2, 
        			gameGUI.window.getHeight() / 2 - scaled_dialog_box.getHeight() / 2);
        	
        	int lineNo = 0;
        	for (MinuetoImage minuetoText: minuetoTexts)
        	{
        		gameGUI.window.draw(minuetoText, gameGUI.window.getWidth() / 2 - minuetoText.getWidth() / 2, 
        				gameGUI.window.getHeight() / 2 - scaled_dialog_box.getHeight() / 2 + text_frame + lineNo*(text_Yspacing+textHeight));
        		lineNo ++;
        	}
        	gameGUI.window.draw(confetti, 0, y-500);
        	gameGUI.window.draw(confetti, 0, y-250);
        	gameGUI.window.draw(confetti, 0, y);
        	gameGUI.window.draw(confetti, 0, y+250);
        	gameGUI.window.draw(confetti, 300, y-500);
        	gameGUI.window.draw(confetti, 300, y-250);
        	gameGUI.window.draw(confetti, 300, y);
        	gameGUI.window.draw(confetti, 300, y+250);
        	gameGUI.window.draw(confetti, 600, y-500);
        	gameGUI.window.draw(confetti, 600, y-250);
        	gameGUI.window.draw(confetti, 600, y);
        	gameGUI.window.draw(confetti, 600, y+250);
        	gameGUI.window.draw(confetti, 950, y-500);
        	gameGUI.window.draw(confetti, 950, y-250);
        	gameGUI.window.draw(confetti, 950, y);
        	gameGUI.window.draw(confetti, 950, y+250);
        	gameGUI.window.draw(confetti, 1200, y-500);
        	gameGUI.window.draw(confetti, 1200, y-250);
        	gameGUI.window.draw(confetti, 1200, y);
        	gameGUI.window.draw(confetti, 1200, y+250);
        	
        	y += 4;
        	
//        	if(y >= window_height) {
//        		y = 0;
//        	}

        	
        	gameGUI.window.render();
            Thread.yield();
            
            
        }
        
        System.out.println("we're here");
        
        while(true) {
        	while (gameGUI.queue.hasNext()) 
    		{
    			gameGUI.queue.handle();
    			
    		}
        }
        
        
//        watch.reset();
        
	}
	
	class returnToLobbyMouseHandler implements MinuetoMouseHandler
	{
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int x, int y, int arg2) 
		{
			System.out.println(x);
			gameGUI.window.close(); // Closes the current window
			GUI_Lobby ls = new GUI_Lobby();
//			ls.setRefreshToken(ls.refreshMyToken()); // Ensure that the refresh token is set or else
			// we don’t know who is playing
			ls.start();

			
		}
	}
		
		
		
//		gameGUI.window.unregisterMouseHandler(handler, queue);
}
	
	
	
	
	
