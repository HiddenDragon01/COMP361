package gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;

public class SavedGamesGUI implements MinuetoKeyboardHandler, MinuetoMouseHandler {
	
	
	private final MinuetoWindow window;
	private MinuetoImage background;
	private boolean toReturn;
	private String refreshToken;
	private ArrayList<String> gameServices;
	private ArrayList<Integer> numPlayers;
	private ArrayList<String> savedNames;
	private ArrayList<String> savedIDs;
	private String myUsername;
	private String sessionToCreate;
	private String mySavedID;
	private boolean loaded;
	private int numPlayer;
	
	
	public SavedGamesGUI() {
		
		refreshToken = "";
		window = new MinuetoFrame(1400, 700, true);
		gameServices = new ArrayList<>();
		numPlayers = new ArrayList<>();
		savedNames = new ArrayList<>();
		savedIDs = new ArrayList<>();
		myUsername = "";
		toReturn = false;
		sessionToCreate = "";
		mySavedID = "";
		loaded = false;
		numPlayer = 0;
	
		gameServices.add("ElfRegular");
		gameServices.add("ElfTownCards");
		gameServices.add("Elf4Rounds");
		gameServices.add("ElfgRegular");
		gameServices.add("ElfgTownCards");
		gameServices.add("Elfg4Rounds");
		gameServices.add("ElfgTownGold");
		gameServices.add("ElfgWitch");
	}

	
	public String start() {
    	
    	
    	MinuetoImage sumbitButton;
    	MinuetoImage smallerSumbitButton;
        MinuetoEventQueue queue;
        toReturn = false;
        queue = new MinuetoEventQueue();
        window.registerKeyboardHandler(this, queue);
        window.registerMouseHandler(this, queue);
        
        // Load images
        String imgDir = System.getProperty("user.dir") + "/GameAssets/";
        try {
            background = new MinuetoImageFile(imgDir + "saved_background.jpg");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return "";
        }
        
      
        
        try {
            sumbitButton = new MinuetoImageFile(imgDir + "LoginSubmit.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return "";
        }
        
        try {
            smallerSumbitButton = new MinuetoImageFile(imgDir + "smaller_submit.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return "";
        }
        
       
        // Set up text to draw
        MinuetoFont fontArial14 = new MinuetoFont("Arial", 40, false, false); 
        MinuetoFont fontArial15 = new MinuetoFont("Arial", 30, false, false); 
        MinuetoFont fontArial16 = new MinuetoFont("Arial", 25, false, false); 
        MinuetoFont fontArial17 = new MinuetoFont("Arial", 28, false, false);      
        MinuetoImage playText = new MinuetoText("Load", fontArial17,MinuetoColor.WHITE);
        MinuetoImage gameText = new MinuetoText("Game",fontArial14,MinuetoColor.BLACK);
        MinuetoImage savedText = new MinuetoText("Saved Game ID",fontArial14,MinuetoColor.BLACK);
        MinuetoImage playersText = new MinuetoText("Players",fontArial14,MinuetoColor.BLACK);
        MinuetoImage refreshText = new MinuetoText("Refresh",fontArial14,MinuetoColor.WHITE);
        MinuetoImage lobbyText = new MinuetoText("Lobby",fontArial14,MinuetoColor.WHITE);
        
        window.setVisible(true);
        refresh();
       
       
        // Retrieve information
        
       

        while(true) { 
        	
        	if (toReturn) {
        		window.close();
        		return sessionToCreate;
        	}
        	
        	window.draw(sumbitButton, 1095, 10);
        	window.draw(refreshText, 1120, 20);
        	
        	window.draw(sumbitButton, 5, 10);
        	window.draw(lobbyText, 40, 20);

         	window.draw(gameText, 200, 100);
         	window.draw(savedText, 500, 100);
         	window.draw(playersText, 850, 100);
         	
         	
	            
        	while(queue.hasNext()) {
        		
        		queue.handle();
   
        		
		            
        		
        		for (int i = 0; i < savedNames.size(); i++) {
        			
        			int drawLine = 150 + (i * 50);
        			
        			MinuetoImage savedName = new MinuetoText(String.valueOf(savedNames.get(i)) ,fontArial14,MinuetoColor.BLACK);
    	        	window.draw(savedName, 200, drawLine);
	        		MinuetoImage savedID = new MinuetoText(String.valueOf(savedIDs.get(i)) ,fontArial14,MinuetoColor.BLACK);
	        		window.draw(savedID, 500, drawLine);
	        		MinuetoImage numPlayer = new MinuetoText(String.valueOf(numPlayers.get(i)) ,fontArial14,MinuetoColor.BLACK);
	        		window.draw(numPlayer, 850, drawLine);
	        		
	        		window.draw(smallerSumbitButton, 1240, drawLine);
	        		
	        		window.draw(playText, 1257, drawLine);
	        		
	        		
	        		
        		}
        		
        		
        	}
        	
	
        		window.render();
	            Thread.yield();      
        } 
    }
	
	public String findUsername() {
    	
    	String username = "";
    	
    	try {
			
			
			URL url = new URL(IPAddress.IP + "/oauth/username?access_token=" + refreshMyToken());
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
			
			
			
		} catch(Exception e) {e.printStackTrace();}
    	
    	return username;
    	
    }
	
	
	
	@Override
	public void handleMouseMove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMousePress(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void refresh() {
		
		window.clear();
    	window.draw(background, 0, 0);
		savedNames.clear();
		savedIDs.clear();
		numPlayers.clear();
	
		
		for (String service: this.gameServices) {
			
			
			try {
				URL url = new URL(IPAddress.IP + "/api/gameservices/" + service + "/savegames?access_token=" + refreshMyToken());
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
				
	        
	        	JSONArray jsonArray = new JSONArray(content.toString());
	        	

			    for (int i = 0; i < jsonArray.length(); i++) {

			        JSONObject jsonObject = jsonArray.getJSONObject(i);

			        JSONArray names = jsonObject.getJSONArray("players");
			        String gameName = jsonObject.getString("gamename");
			        String saveID = jsonObject.getString("savegameid");
			        
			       
			        
			        savedNames.add(gameName);
			        savedIDs.add(saveID);
			        numPlayers.add(names.length());
			        
			    }
			} catch(Exception e) {e.printStackTrace();}
			
		}
		
		
		
	}

	@Override
	public void handleMouseRelease(int x, int y, int button) {
		
    	System.out.println("Clicked on (" + x + "," + y + ")");
		
		// Refresh button
		if ((x > 1097) && (x < 1275) && (y > 13) && (y < 83))
		{	
				refresh();

		}
		
		// Lobby button
		if ((x > 8) && (x < 183) && (y > 11) && (y < 80))
		{	
				toReturn = true;

		}
		
		
		if ((x > 1253) && (x < 1338))
		{	
			
			// Load button
			if (y > 155) {
				
				
    			int indexClicked = Math.round((y - 150)/50);
    			
    			if (indexClicked >= 0 && indexClicked < savedNames.size()) {
    				toReturn = true;
    				sessionToCreate = savedNames.get(indexClicked);
    				mySavedID = savedIDs.get(indexClicked);
    				loaded = true;
    				numPlayer = numPlayers.get(indexClicked);
    				
    			}
    			
    			
    			
			}
			
		}
		
	}
	public int numberPlayers() {
		return numPlayer;
	}
	
	public boolean loaded() {
		System.out.println("Game was loaded!");
		return loaded;
	}
	
	public String getID() {
		System.out.println("ID: " + mySavedID);
		return mySavedID;
	}
	
	

	@Override
	public void handleKeyPress(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyRelease(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyType(char arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String refreshMyToken() {
    	
    	String token = "";
    	
    	try {
    		
    		URL url = new URL(IPAddress.IP + "/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken);
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
	
	 public void setRefreshToken(String pRefreshToken) {
	    	refreshToken = pRefreshToken;
	    	myUsername = findUsername();
	 }
	 
	

}