
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.JSONObject;
import org.minueto.*;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*; 
import org.minueto.window.*; 

//import org.json.JSONObject;


public class Game {
	private int currRound;
	private GamePhase currGamePhase;
	private int numOfPlayers;
	private GameEdition gameEdition;
	private List<GameVariant> gameVariants;
	private GameBoard myGameBoard;
	private Player myCurrentPlayer;
	private Player mySecondPlayer;
	private Map<String, Player> players;
//	private Boot boot;
	private String myUsername;
	private boolean isCreator;
	private MinuetoWindow window;
	private MinuetoImage redBootPic;
    private MinuetoImage blueBootPic;
    private MinuetoImage background;
    
    // These two arrays store the boot x,y positions of the players. The 0th index stores
    // the x,y positions of the player with a red boot the 1st index stores
    // the x,y positions of the player with a blue boot, and the 3rd index stores
    // the x,y positions of the player with a green boot
    int[] playerXPositions;
    int[] playerYPositions;
    
    String myColor;
    int myPoints;
	
	
	public Game() {
		currRound = 0;
		gameVariants = new ArrayList<>();
		players =  new HashMap<>();
		playerXPositions = new int[6];
		playerYPositions = new int[6];
		String myColor = "";
		myPoints = 0;
	}
	
	
	public Player getPlayer(String name) {
		return players.get(name);
	}
	
	public void putPlayer(Player player) {
		players.put(player.getName(), player);
	}
	
	public void start(boolean isCreator, String refreshToken, String color) {
		
		// TODO: Add a 3rd player with a green boot. Change game logic correspondingly
		
		myUsername = refreshToken;
		
		this.isCreator = isCreator;
		
		// When refreshToken is passed, myUsername is initialized
		/*
		this.isCreator = isCreator;
		
		refreshToken = refreshMyToken(refreshToken);
		myUsername = findUsername(refreshToken);
		
		*/
		

		myColor = color;
		
        MinuetoImage background;
        MinuetoFont fontArial14;
        MinuetoImage redBootPic;
        MinuetoImage blueBootPic;
        MinuetoImage updatedPoints;
        MinuetoImage updatedPointsBlue;
		
		String imgDir = System.getProperty("user.dir") + "/GameAssets/";
	        
		
		try {
			background = new MinuetoImageFile(imgDir + "Background.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}

		try {
			redBootPic = new MinuetoImageFile(imgDir + "Boots/boot-red.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}

		try {
			blueBootPic = new MinuetoImageFile(imgDir + "Boots/boot-blue.png");
		} catch (MinuetoFileException e) {
			System.out.println("Could not load image file");
			return;
		}

		redBootPic = redBootPic.scale(0.125, 0.125);
		Boot redBoot = new Boot(redBootPic, Town.getTownsMap().get(TownName.ELVENHOLD), BootColor.RED);
		Player p1 = new Player("biladou", "flower12", 12, redBoot);
		p1.setCurrentXYPosition(835, 255);
		
		// Make sure to initialize the red boot position appropriately at the start
		playerXPositions[0] = 835;
		playerYPositions[0] = 255;

		blueBootPic = blueBootPic.scale(0.125, 0.125);
		Boot blueBoot = new Boot(blueBootPic, Town.getTownsMap().get(TownName.ELVENHOLD), BootColor.BLUE);
		Player p2 = new Player("filou2000", "allo1213", 13, blueBoot);
		p2.setCurrentXYPosition(875, 255);
		
		// Make sure to initialize the blue boot position appropriately at the start
		playerXPositions[1] = 875;
		playerYPositions[1] = 255;
		
		// Create a player at a specific starting position depending on whether the boot is
		// red or blue
		if (myColor.equals("red")) {
			postInfo(835, 255, 0);
		} else if (myColor.equals("blue")) {
			postInfo(875, 255, 0);
		}
	        
		
		players.put("p1", p1);
		players.put("p2", p2);
		
		myCurrentPlayer = players.get("p1");
		Player mySecondPlayer = players.get("p2");
		
		
        window = new MinuetoFrame(1400, 700, true); 
    	Town.initializeTowns();
        
    	
        /*
         * Handles mouse clicks
         */
    	ChooseBootMouseHandler chooseBootHandler = new ChooseBootMouseHandler();
    	MouseHandler moveBootHandler = new MouseHandler();
        MinuetoEventQueue clickQueue = new MinuetoEventQueue();
        window.registerMouseHandler(moveBootHandler, clickQueue);
        
       
		
        fontArial14 = new MinuetoFont("Arial",22,true, false); 
//        name1 = new MinuetoText("Ryan" ,fontArial14, MinuetoColor.YELLOW); 
//        name2 = new MinuetoText("Shalee" ,fontArial14, MinuetoColor.YELLOW); 
//        name3 = new MinuetoText("Beatrice" ,fontArial14, MinuetoColor.YELLOW); 
		
        Roads.initializeRoads(); // Creates all the predetermined roads (2 for now)

        /*
         * Creating the images to visualize the roads
         * and images to check if the travel function is recognizing the roads
         */
//        MinuetoImage road1 = new MinuetoImage(130, 100);
//        road1.drawPolygon(MinuetoColor.YELLOW, Roads.getPoints(0));
//        MinuetoImage road2 = new MinuetoImage(200, 200);
//        road2.drawPolygon(MinuetoColor.YELLOW, Roads.getPoints(1));
        
        MinuetoImage town1 = new MinuetoImage(100,100);
        town1.drawRectangle(MinuetoColor.YELLOW, 0, 0, 50, 45);
        
        updatedPoints = new MinuetoText("x" + String.valueOf(myCurrentPlayer.getPoints()) ,fontArial14,MinuetoColor.WHITE);
		
        window.setVisible(true);
        window.draw(background, 0, 0);
        window.draw(updatedPoints, 1210, 655);
//    	window.draw(name1, 150, 34);
//    	window.draw(name2, 150, 164);
//    	window.draw(name3, 150, 292);
    	
    	window.draw(redBootPic, 835,255);
    	window.draw(blueBootPic, 875, 255);
//    	window.draw(road1, 795, 330); //these two lines just visualize the road polygons - will have to be moved on other people's screens probably
//    	window.draw(road2, 850, 355);

//    	window.draw(town1, 675, 230);
    	for (TownName town : Town.getTownsMap().keySet()) 
    	{	
    		if(!myCurrentPlayer.hasVisited(town))
    		{
    			Town t =  Town.getTownsMap().get(town);
    			window.draw(t.getRedTownpiece(), t.getRedTownpieceX(), t.getRedTownpieceY());
    		
    		}
    		
    		if(!mySecondPlayer.hasVisited(town))
    		{
    			Town t =  Town.getTownsMap().get(town);
    			window.draw(t.getBlueTownpiece(), t.getBlueTownpieceX(), t.getBlueTownpieceY());
    		
    		}
    	}
    	
    	
    	int redBootHalfWidth = redBootPic.getWidth() / 2;
        int redBootHalfHeight = redBootPic.getHeight() / 2;
        
        int blueBootHalfWidth = blueBootPic.getWidth() / 2;
        int blueBootHalfHeight = blueBootPic.getHeight() / 2;
       
    	

        while(true) { 
        	
            /*window.draw(demoImage, 0, 0);
        	window.draw(name1, 150, 34);
        	window.draw(name2, 150, 164);
        	window.draw(name3, 150, 292);
            window.render();
            Thread.yield();*/
        	
        	/* used for finding the locations of towns on the map
        	MinuetoImage rectangle3 = new MinuetoRectangle(50,50,MinuetoColor.BLUE,true);
            window.draw(rectangle3, 295, 190); 
            */
        	
        //DO SAME AS LOOP BELOW but get blue boot x, y and use that rather than the mousehandler xy and my second player rather than first
        
        /*THE CODE THAT ESSENTIALLY NEEDS TO BE CHANGED TO GET X AND Y
         * String jsonString = RestApiClient.getBootData(BootColor.BLUE); //should probably send initial boot data up when were initializing stuff
			JSONObject jsonObject = new JSONObject(jsonString);

			int x_blue = jsonObject.getInt("x-coordinate");
			int y_blue = jsonObject.getInt("y-coordinate");
			
			NEEDS AN IMPORT LINE THAT DOESN'T WORK (COMMENTED OUT UP TOP)
			
			Optional<TownName> destTownBlue = jsonObject.getString("town");
                if(destTownBlue.isPresent())
                {
                	int xBlueTemp = mySecondPlayer.getCurrentXPosition();
                	mySecondPlayer.visitTown(destTownBlue.get());
                	
                	
                	while(xBlueTemp != x_blue)
                	{
                		window.clear();
                		window.draw(background, 0, 0);
                		for (TownName town : Town.getTownsMap().keySet()) 
                    	{	if(!myCurrentPlayer.hasVisited(town))
                    		{
                    			Town t =  Town.getTownsMap().get(town);
                    			window.draw(t.getRedTownpiece(), t.getRedTownpieceX(), t.getRedTownpieceY());
                    		}
                    		
                    		if(!mySecondPlayer.hasVisited(town))
                    		{
                    			Town t =  Town.getTownsMap().get(town);
                    			window.draw(t.getBlueTownpiece(), t.getBlueTownpieceX(), t.getBlueTownpieceY());
                		
                    		}
                    	}
                		
                		
                		updatedPoints = new MinuetoText("x"+String.valueOf(myCurrentPlayer.getPoints()) ,fontArial14,MinuetoColor.WHITE);
                		window.draw(updatedPoints, 1210, 655);
                		
                		updatedPointsBlue = new MinuetoText("x"+String.valueOf(mySecondPlayer.getPoints()) ,fontArial14,MinuetoColor.WHITE);
                		window.draw(updatedPoints, 200, 200);
                		
                		window.draw(redBootPic, myCurrentPlayer.getCurrentXPosition(), myCurrentPlayer.getCurrentYPosition());
	                    window.draw(blueBootPic, 
	                			x - redBootHalfWidth, 
	                			moveBootHandler.computeY(myCurrentPlayer.getCurrentXPosition(), myCurrentPlayer.getCurrentYPosition(), x) - redBootHalfHeight);
	                    window.render();
	                    Thread.yield();
	                    if(x_blue > mySecondPlayer.getCurrentXPosition())
	                    {
	                    	x++;
	                    }
	                    else
	                    {
	                    	x--;
	                    }
	                   
                	}
                	//blueBoot.setXY(x_blue, y_blue);
                	mySecondPlayer.setCurrentXYPosition(x_blue, y_blue);
			
         */
        
        
        	
		
        	while(clickQueue.hasNext()) {
                clickQueue.handle();

                
               
                Optional<TownName> destTown = Town.getDestinationTown(moveBootHandler.getX(), moveBootHandler.getY(), myCurrentPlayer.getCurrentTown());
                //if(Town.travel(clickHandler.getX(), clickHandler.getY(), redBoot)){
                
                // Player clicked on a town
                if(destTown.isPresent())
                {
                	
                	// Update the Rest-API with the new X and Y positions
                	updatePoints(moveBootHandler.getX(), moveBootHandler.getY());
                	
                	int x = myCurrentPlayer.getCurrentXPosition();
                	myCurrentPlayer.visitTown(destTown.get());
                	
                	
                	while(x != moveBootHandler.getX())
                	{
         
                		for (TownName town : Town.getTownsMap().keySet()) 
                    	{	if(!myCurrentPlayer.hasVisited(town))
                    		{
                    			Town t =  Town.getTownsMap().get(town);
                    			window.draw(t.getRedTownpiece(), t.getRedTownpieceX(), t.getRedTownpieceY());
                    		}
                    		
                    		if(!mySecondPlayer.hasVisited(town))
                    		{
                    			Town t =  Town.getTownsMap().get(town);
                    			window.draw(t.getBlueTownpiece(), t.getBlueTownpieceX(), t.getBlueTownpieceY());
                		
                    		}
                    	}
                		
                		
                		// TODO: Make updatedPoints a global variable and display its value 
                		// in the refresh() function
                		
                		updatedPoints = new MinuetoText("x"+String.valueOf(myCurrentPlayer.getPoints()) ,fontArial14,MinuetoColor.WHITE);
             //   		window.draw(updatedPoints, 1210, 655);
                		updatedPointsBlue = new MinuetoText("x"+String.valueOf(mySecondPlayer.getPoints()) ,fontArial14,MinuetoColor.WHITE);
                //		window.draw(updatedPointsBlue, 0, 0);
                //		window.draw(blueBootPic, 875, 255);
	              //      window.draw(redBootPic, 
	                //			x - redBootHalfWidth, 
	                //			moveBootHandler.computeY(myCurrentPlayer.getCurrentXPosition(), myCurrentPlayer.getCurrentYPosition(), x) - redBootHalfHeight);
	                 //   window.render();
	                    Thread.yield();
	                    if(moveBootHandler.getX() > myCurrentPlayer.getCurrentXPosition())
	                    {
	                    	x++;
	                    }
	                    else
	                    {
	                    	x--;
	                    }
	                   
                	}
                	
                	//redBoot.setXY(clickHandler.getX(), clickHandler.getY());
                	myCurrentPlayer.setCurrentXYPosition(moveBootHandler.getX(), moveBootHandler.getY());
                    
                	moveBootHandler.clear();
                }
                
                /*else {
                	String coords = "this is " + clickHandler.getX() + "," + clickHandler.getY();
                	MinuetoImage coordStr = new MinuetoText(coords,fontArial14,MinuetoColor.RED);
                	window.draw(coordStr, clickHandler.getX(), clickHandler.getY());
                	
                	//window.draw(noTown, clickHandler.getX(), clickHandler.getY());
                }*/
                 

            }
       //     window.render();
       //     Thread.yield();
           
        	// After making a move, make sure the screen is up to date
            refresh();
            
            // This function will continue looping until it is your turn. It will
            // update the positions of the other players while waiting.
            checkData();
        }
    }
	
	

	// Update the points for the player by using a "PUT" call to the REST-API
	// Automatically switches turn to next player in line.
	public void updatePoints(int locationX, int locationY) {
		
		
		try {
			URL url = new URL("http://localhost:8080/updatePlayer");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("content-type", "application/json");

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("  \"name\": \"" + myUsername + "\",\n");
			out.writeBytes("  \"locationX\": " + locationX + ",\n");
			out.writeBytes("  \"locationY\": " + locationY + ",\n");
			out.writeBytes("  \"points\": " + myPoints + ",\n");
			out.writeBytes("  \"turn\": false,\n");
			out.writeBytes("  \"color\": \"" + myColor + "\"\n");
			out.writeBytes("}");
			out.flush();
			out.close();

			int status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
	    
		} catch(Exception e) {e.printStackTrace();};
		
	}
	
	// Loops until it is your turn. Updates the board when other players make moves
	public void checkData() {
		
		boolean myTurn = false;
		
		while (!myTurn) {
			try {
				URL url = new URL("http://localhost:8080/game");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
	
				int status = con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while((inputLine = in.readLine()) != null) {
					content.append(inputLine);
				}
				in.close();
				con.disconnect();
			
			
	        
	        
				JSONArray jsonArray = new JSONArray(content.toString());
	
			    for (int i = 0; i < jsonArray.length(); i++) {
	
			        JSONObject jsonObject = jsonArray.getJSONObject(i);
	
			        String name = jsonObject.getString("name");
			        int x = jsonObject.getInt("locationX");
			        int y = jsonObject.getInt("locationY");
			        int points = jsonObject.getInt("points");
			        boolean turn = jsonObject.getBoolean("turn");
			        String color = jsonObject.getString("color");
			        
			        // If it is your turn, return (no need to update)
			        if (name.equals(myUsername) && turn) {
			        	myTurn = true;
			        } 
			        // Update the information for the other player
			        else {
			        	
			        	
			        	if (color.equals("blue")) {
			        		
			        		// Update the x, y positions at "blue" index if it has changed
			        		if (playerXPositions[1] != x || playerYPositions[1] != y) {
				        		playerXPositions[1] = x;
				        		playerYPositions[1] = y;
				        		refresh();
				        		
				        		// TODO: We know the color of boot of the player who moved
				        		// as well as the location. Remove the town piece at this 
				        		// location with this color.
				        		
				        	}
			        		
			        		

			        	} else if (color.equals("red")) {
			        		
			        		// Update the x, y positions at "red" index if it has changed
			        		if (playerXPositions[0] != x || playerYPositions[0] != y) {
				        		playerXPositions[0] = x;
				        		playerYPositions[0] = y;
				        		refresh();
				        		
				        		
				        		// TODO: We know the color of boot of the player who moved
				        		// as well as the location. Remove the town piece at this 
				        		// location with this color.
				        	}
			        		
			        	}
			        	
			        }
			        
			        
	
			    }
			    
		    
			} catch(Exception e) {e.printStackTrace();};
			
			 
		}
	}
	
	// Refresh the board with the information from the REST-API
	public void refresh() {
		
		
		String imgDir = System.getProperty("user.dir") + "/GameAssets/";
		
		try {
            blueBootPic = new MinuetoImageFile(imgDir + "Boots/boot-blue.png");
        } catch(MinuetoFileException e) {
           System.out.println("Could not load image file");
           return;
        }
		blueBootPic = blueBootPic.scale(0.125,0.125);
		
		try {
            redBootPic = new MinuetoImageFile(imgDir + "Boots/boot-red.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }
		
		redBootPic = redBootPic.scale(0.125,0.125);
		
		try {
            background = new MinuetoImageFile(imgDir + "Background.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }
		
		window.clear();
		window.draw(background, 0, 0);
		
		// Draw town pieces
		// TODO: Keep track of which town pieces have been taken and only display the 
		// right ones. Right now, it removes the town pieces the player has taken, 
		// but does not remove the town pieces other players have taken. Keep track
		// in the function checkData()
		for (TownName town : Town.getTownsMap().keySet()) 
    	{	if(!myCurrentPlayer.hasVisited(town))
    		{
    			Town t =  Town.getTownsMap().get(town);
    			window.draw(t.getRedTownpiece(), t.getRedTownpieceX(), t.getRedTownpieceY());
    		}
    		
    		
    	}
		
		window.draw(blueBootPic, playerXPositions[1], playerYPositions[1]);
		window.draw(redBootPic, playerXPositions[0], playerYPositions[0]);
		window.render();
	}
	
	// Add an entree into the REST-API with the relevant information. This is 
	// only done at the beginning to initialize a player.
	public void postInfo(int locationX, int locationY, int points) {
		
		try {
			
			URL url = new URL("http://localhost:8080/addPlayer");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("content-type", "application/json");

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("    \"name\": \"" + myUsername + "\",\n");
			out.writeBytes("    \"locationX\": " + locationX +  ",\n");
			out.writeBytes("    \"locationY\": " + locationY + ",\n");
			out.writeBytes("    \"points\" : " + points + ",\n");
			out.writeBytes("    \"turn\": " + isCreator + ",\n");
			out.writeBytes("  \"color\": \"" + myColor + "\"\n");
			out.writeBytes("  }");
			out.flush();
			out.close();

			int status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
			
		} catch(Exception e) {}
		

	}
	
	// Finds the username for a given token
	public String findUsername(String token) {
    	
    	String username = "";
    	
    	try {
			
			
			URL url = new URL("http://127.0.0.1:4242/oauth/username?access_token=" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int status = con.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
			username = content.toString();
			
			
			
		} catch(Exception e) {}
    	
    	return username;
    	
    }
	
	  
	  public String refreshMyToken(String myToken) {
	    	
		  String token = "";
	    	
	    	try {
	    		
	    		URL url = new URL("http://172.28.9.138:4242/oauth/token?grant_type=refresh_token&refresh_token=" + myToken);
	    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    		con.setRequestMethod("POST");

	    		/* Payload support */
	    		con.setDoOutput(true);
	    		DataOutputStream out = new DataOutputStream(con.getOutputStream());
	    		out.writeBytes("user_oauth_approval=true&_csrf=19beb2db-3807-4dd5-9f64-6c733462281b&authorize=true");
	    		out.flush();
	    		out.close();

	    		int status = con.getResponseCode();
	    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    		String inputLine;
	    		StringBuffer content = new StringBuffer();
	    		while((inputLine = in.readLine()) != null) {
	    			content.append(inputLine);
	    		}
	    		in.close();
	    		con.disconnect();
	    		
	    		JSONObject obj = new JSONObject(content.toString());
	    		token = obj.getString("access_token");
	    		token = token.replace("+", "%2B");
		    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    	return token;
	    	
	  }


}
