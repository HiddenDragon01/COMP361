package gui;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*;

import commands.RestartServerCommand;
import commands.SessionIDCommand;
import gamelogic.Client;
import networking.CommandChannel;


public class GUI_Lobby  implements MinuetoKeyboardHandler, MinuetoMouseHandler {
	
	private String sessionName;
	private final MinuetoWindow window;
	private MinuetoImage background;
	private ArrayList<String> sessionNames;
	private int[] numPlayers;
	private ArrayList<String> creators;
	private ArrayList<String> sessionIds;
	private String refreshToken;
	private int[] sessionsJoined;
	private int[] sessionsCreated;
	private int[] sessionsLaunched;
	private int[] minPlayers;
	private int[] maxPlayers;
	private String myUsername;
	private boolean toReturn;
	private int indexPlaying;
	private HashMap<Integer, String> mySessionIds;
	private int indexLaunched;
	private int lastLength;
	private boolean loaded;
	private String mySavedID;


	
	
    /**
     * Displays the lobby screen.
     */
	
	public GUI_Lobby() {
		
		window = new MinuetoFrame(1400, 700, true);
		sessionNames = new ArrayList<>();
		numPlayers = new int[10];
		creators = new ArrayList<>();
		sessionName = "";
		sessionIds = new ArrayList<>();
		refreshToken = "";
		sessionsJoined = new int[10];
		sessionsCreated = new int[10];
		sessionsLaunched = new int[10];
		minPlayers = new int[10];
		maxPlayers = new int[10];
		myUsername = "";
		indexPlaying = 0;
		lastLength = 0;
		mySessionIds = new HashMap<>();
		mySavedID = "";
		loaded = false;
		
		
		
	}
	
	
	
	
    public boolean start() {
    	
    	
    	MinuetoImage submitButton;
    	MinuetoImage smallerSumbitButton;
    	MinuetoImage loadingGame;
        MinuetoEventQueue queue;
        queue = new MinuetoEventQueue();
        window.registerKeyboardHandler(this, queue);
        window.registerMouseHandler(this, queue);
        
        // Load images
        String imgDir = System.getProperty("user.dir") + "/GameAssets/";
        try {
            background = new MinuetoImageFile(imgDir + "LobbyScreenBackground.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return false;
        }
        
     
        
        try {
            submitButton = new MinuetoImageFile(imgDir + "LoginSubmit.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return false;
        }
        
        try {
            smallerSumbitButton = new MinuetoImageFile(imgDir + "smaller_submit.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return false;
        }
        
        try {
        	loadingGame = new MinuetoImageFile(imgDir + "loading-screen.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return false;
        }

        
        // Set up text to draw
        MinuetoFont fontArial10 = new MinuetoFont("Arial", 20, false, false); 
        MinuetoFont fontArial14 = new MinuetoFont("Arial", 40, false, false); 
        MinuetoFont fontArial15 = new MinuetoFont("Arial", 30, false, false); 
        MinuetoFont fontArial16 = new MinuetoFont("Arial", 25, false, false); 
        MinuetoFont fontArial17 = new MinuetoFont("Arial", 28, false, false);  
        MinuetoFont fontArial18 = new MinuetoFont("Arial", 32, false, false); 
        MinuetoImage launchText = new MinuetoText("Launch", fontArial17,MinuetoColor.WHITE);
        MinuetoImage deleteText = new MinuetoText("Delete", fontArial15,MinuetoColor.WHITE);
        MinuetoImage joinText = new MinuetoText("Join", fontArial15,MinuetoColor.WHITE);
        MinuetoImage leaveText = new MinuetoText("Leave", fontArial15,MinuetoColor.WHITE);
        MinuetoImage playText = new MinuetoText("Play", fontArial15,MinuetoColor.WHITE);
        MinuetoImage watchText = new MinuetoText("Watch", fontArial15,MinuetoColor.WHITE);

        MinuetoImage loadGameText = new MinuetoText("Load",fontArial15,MinuetoColor.WHITE);
        MinuetoImage serverText = new MinuetoText("Restart Server",fontArial16,MinuetoColor.WHITE);
        
        window.setVisible(true);
        window.draw(background, 0, 0);
        
        findGameSessions();

        while(true) { 
        	
        	if (toReturn) {
        		if (creators.get(indexPlaying).equals(myUsername)) {
        			
        			window.draw(loadingGame, 0, 0);
        			window.render();
    	            Thread.yield();    
        			return true;
        		}
        		
        		return false;
        	}
        	
        	// Draw shapes
        	//window.draw(players, 0, 0);	
        	//window.draw(loginTitle, 670, 20);
        	
        	
        	
        	window.draw(submitButton, 505, 60);
        	window.draw(serverText, 513, 80);
        	
        	window.draw(smallerSumbitButton, 1275, 70);
        	window.draw(loadGameText, 1285, 70);
        	refresh();
         	
	            
        	while(queue.hasNext()) {
        		
        		queue.handle();
		            
        		for (int i = 0; i < sessionNames.size(); i++) {
        			
        			int drawLine = 170 + (i * 50);
        			
        			MinuetoImage SessionName;
        			SessionName = new MinuetoText(String.valueOf(sessionNames.get(i)) ,fontArial10,MinuetoColor.BLACK);
    	        	window.draw(SessionName, 550, drawLine);
	        		MinuetoImage creatorDisplay;
	        		creatorDisplay = new MinuetoText(String.valueOf(creators.get(i)) ,fontArial10,MinuetoColor.BLACK);
	        		
	        		boolean created = false;
	        		if (creators.get(i).equals(myUsername)) {
	        			created = true;
	        		}
	        		
	        		window.draw(creatorDisplay, 800, drawLine);
	        		MinuetoImage numPlayersDisplay;
	        		numPlayersDisplay = new MinuetoText(String.valueOf(numPlayers[i]) ,fontArial10,MinuetoColor.BLACK);
            		window.draw(numPlayersDisplay, 1050, drawLine);
            		
            		
            		if ((sessionsLaunched[i] == 1 && sessionsJoined[i] == 1) || 
            				(sessionsLaunched[i] == 1 && created)) {
            			window.draw(smallerSumbitButton, 1275, drawLine);
            			window.draw(playText, 1292, drawLine);
            		}
            		
            		else if (sessionsLaunched[i] == 1 && sessionsJoined[i] == 0) {
            			window.draw(smallerSumbitButton, 1275, drawLine);
            			window.draw(watchText, 1283, drawLine);
            		}
            		
            		else if (created) {
            			
            			window.draw(smallerSumbitButton, 1140, drawLine);
            			window.draw(deleteText, 1145, drawLine);
            			
            			if (numPlayers[i] >= minPlayers[i]) {
            			
	        				window.draw(smallerSumbitButton, 1275, drawLine);
	            			window.draw(launchText, 1277, drawLine);
            			}
            		}
            		
            		else if (sessionsJoined[i] == 0 && numPlayers[i] <= maxPlayers[i]) {
	            		window.draw(smallerSumbitButton, 1275, drawLine);
	            		window.draw(joinText, 1290, drawLine);
            		} else {
            			window.draw(smallerSumbitButton, 1275, drawLine);
	            		window.draw(leaveText, 1285, drawLine);
            		}
            		
            		
            	
        		}
        		
//        		while(queue.hasNext()) {
//            		
//            		queue.handle();
        		
        		
        		
        	}
        		
        	
	
        		window.render();
	            Thread.yield();      
        } 
    }
   
 
    /**
     * Removes the last character of the String s and returns that String.
     */
    public static String removeLast(String s) {
        return (s == null || s.length() == 0)
          ? null 
          : (s.substring(0, s.length() - 1));
    }
    
    @Override
    public void handleMouseMove(int arg0, int arg1) {}

    @Override
	public void handleMousePress(int arg0, int arg1, int arg2) {}
    
    public boolean loaded() {
		return loaded;
	}
	
	public String getID() {
		return mySavedID;
	}
 
	
    @Override
	public void handleMouseRelease(int x, int y, int button) {
    	
    	System.out.println("Clicked on (" + x + "," + y + ")");
    
		
		if (button == 1)
        {
			// Load button
			if ((x > 1275) && (x < 1371) && ((y > 73) && (y < 107))) {
				SavedGamesGUI savegui = new SavedGamesGUI();
				savegui.setRefreshToken(refreshToken);
				sessionName = savegui.start();
				
				if (!sessionName.equals("")) {
					createSession();
				}
				
				loaded = savegui.loaded();
				mySavedID = savegui.getID();
				
				
			}
				
			// Restart Server button clicked
			if ((x > 510) && (x < 683) && ((y > 67) && (y < 127))) {
				System.out.println("Server button clicked");
				
				if(true) {
					RestartServerCommand restartservercmd = new RestartServerCommand();
					
					Client.instance().clientChannel.send(Client.instance().hostIP, restartservercmd);
				}
				
				
			}
				
	
			// Create button clicked
			if ((x > 121) && (x < 234) && ((y > 637) && (y < 677))) {
				if (!sessionName.equals("")) {
					createSession();
					sessionName = "";
					window.clear();
					window.draw(background, 0, 0);
					// Connect to the server after creating the session
	    			Client.clientChannel = new CommandChannel(Client.hostIP, 4444); 
					
				} 
				
			}
    				
    				
    				
    				
    			
    			
    			
   		    	
   		    	
    		// Game selected
    		if ((x > 35) && (x < 323)) {
    			
    			
    			if (!sessionName.equals("")) {
    				window.clear();
					window.draw(background, 0, 0);
    			}
    			
    			MinuetoImage circle = new MinuetoCircle(10,MinuetoColor.WHITE,true);
    			if ((y > 102) && (y < 143)) {
    				window.draw(circle, 48, 113);
    				sessionName = "ElfRegular";
    			} else if ((y > 160) && (y < 199)) {
    				System.out.println("Clicked on ElfenLand TownCards");
    				window.draw(circle, 48, 168);
    				sessionName = "ElfTownCards";
    			} else if ((y > 213) && (y < 254)) {
    				System.out.println("Clicked on ElfenLand 4 Rounds");
    				window.draw(circle, 48, 225);
    				sessionName = "Elf4Rounds";
    			} else if ((y > 344) && (y < 383)) {
    				System.out.println("Clicked on ElfenGold Regular");
    				window.draw(circle, 48, 353);
    				sessionName = "ElfgRegular";
    			} else if ((y > 397) && (y < 439)) {
    				System.out.println("Clicked on ElfenGold TownCards");
    				window.draw(circle, 48, 408);
    				sessionName = "ElfgTownCards";
    			} else if ((y > 456) && (y < 495)) {
    				System.out.println("Clicked on ElfenGold 4 Rounds");
    				window.draw(circle, 48, 465);
    				sessionName = "Elfg4Rounds";
    			} else if ((y > 512) && (y < 550)) {
    				System.out.println("Clicked on ElfenGold TownGold");
    				window.draw(circle, 48, 520);
    				sessionName = "ElfgTownGold";
    			} else if ((y > 568) && (y < 608)) {
    				System.out.println("Clicked on ElfenGold Witch");
    				window.draw(circle, 48, 577);
    				sessionName = "ElfgWitch";
    			} 
    		}
    		
    		
    		
    		// Player clicked on one of the join or leave buttons
    		if ((x > 1278) && (x < 1373))
    		{	
    			
    			
    			
    			if (y > 175) {
    				
    				
    				
	    			int indexClicked = Math.round((y - 170)/50);
	    			
	    			if (indexClicked < 0 || indexClicked >= sessionsCreated.length) {
    					return;
    				}
	    			
	    			System.out.println("BILADOU!!");
	    			
	    		
	    			// Launch button clicked
	    			if (sessionsCreated[indexClicked] == 1 && 
	    					sessionsLaunched[indexClicked] == 0) {
	    				
	    				String token = refreshMyToken();
		    			String session = sessionIds.get(indexClicked);
		    			launchSession(session, token);
		    			sessionsLaunched[indexClicked] = 1;
		    			
		    			SessionIDCommand sessionidcmd = new SessionIDCommand(session);
		    			
		    			Client.clientChannel.send(Client.hostIP, sessionidcmd);
		    			
		    			System.out.println("Sent the session id to the server");
		    			
//		    			Client.clientChannel.acceptCommandsFrom(Client.hostIP);
		    			
		    			refresh();
		    			
//		    			// If you are the creator, skip right to the playing
//		    			if (creators.get(indexPlaying).equals(myUsername)) {
//		    				
//		    				try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//		    				
//		    				System.out.println("Playing");
//	    					
//	    					toReturn = true;
//	    					indexLaunched = indexClicked;
//	    					indexPlaying = indexClicked;
//		    			}
//	    				
		    			// Play button clicked
	    			} else if ((sessionsCreated[indexClicked] == 1 && 
	    					sessionsLaunched[indexClicked] == 1) || (sessionsJoined[indexClicked] == 1 && 
	    					sessionsLaunched[indexClicked] == 1)) {
	    				
	    					System.out.println("Playing");
	    					
	    					toReturn = true;
	    					indexLaunched = indexClicked;
	    					indexPlaying = indexClicked;
	    				
	    				// Watch button clicked
	    			} else if (sessionsJoined[indexClicked] == 0 && 
	    					sessionsLaunched[indexClicked] == 1 && sessionsCreated[indexClicked] == 0) {
	    				
	    				
	    					System.out.println("Watching");
	    			}
	    			
	    			// Join button clicked
	    			else if (sessionsJoined[indexClicked] == 0) {
	    			
		    			String token = refreshMyToken();
		    			String session = sessionIds.get(indexClicked);
		    			
		    			String username = findUsername(token);
		    			myUsername = username;
		    			
		    			
		    			joinSession(session, username, token);
		    			System.out.println("Session joined");
		    			sessionsJoined[indexClicked] = 1;
		    			
		    			refresh();
		    			
		    			// Leave button clicked
	    			} else {
	    				
	    				String token = refreshMyToken();
		    			String session = sessionIds.get(indexClicked);
		    			
		    			
		    			leaveSession(session, myUsername, token);
		    			sessionsJoined[indexClicked] = 0;
		    			
		    			refresh();
	    				
	    				
	    				
	    			}
	    			
	    			window.clear();
					window.draw(background, 0, 0);
	    		
    			}
    		}
    		
    		// Player clicked on the delete button
    		if ((x > 1136) && (x < 1232))
    		{	
    			if (y > 175) {
    				
    				System.out.println("Pressed delete button");
    				
    				int indexClicked = Math.round((y - 170)/50);
    				
    				if (indexClicked < 0 || indexClicked >= sessionsCreated.length) {
    					return;
    				}
    				
    				if (sessionsCreated[indexClicked] == 1 && sessionsLaunched[indexClicked] == 0) {
    					
    					String token = refreshMyToken();
		    			String session = sessionIds.get(indexClicked);
		    			deleteSession(session, token);
		    			sessionsCreated[indexClicked] = 0;
		    			
		    			refresh();
    				}
    				
    				window.clear();
					window.draw(background, 0, 0);
	    		
    			}
    		}
    		
    		
        }
	}
    
    public void setSessionName(String pName) {
    	sessionName = pName;
    }
    
    public String findUsername(String token) {
    	
    	String username = "";
    	
    	try {
			
			
			URL url = new URL(IPAddress.IP + "/oauth/username?access_token=" + token);
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
    
    public void createSession() {
    	
    	String newToken = refreshMyToken();
		
		try {
			
			
			String authenUsername = "bgp-client-name";
			String authenPasswd = "bgp-client-pw";
			
			Authenticator.setDefault(new Authenticator() {

			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {          
			        return new PasswordAuthentication(authenUsername, authenPasswd.toCharArray());
			    }
			});
			
			URL url = new URL(IPAddress.IP + "/api/sessions?access_token=" + newToken);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			String username = findUsername(newToken);
			myUsername = username;
			System.out.println("Username: " + username + " sessionName: " + sessionName);
	
			out.writeBytes("    \"creator\": \""+ username + "\",\n");
			out.writeBytes("    \"game\": \"" + sessionName + "\",\n");
			out.writeBytes("    \"savegame\": \"\"\n");
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
			refresh();
			
			
		} catch(Exception e) {e.printStackTrace();}
    }
    
    public void joinSession(String session, String username, String token) {
    	
    	try {
			
			/*
			URL url = new URL(IPAddress.IP + "2/api/sessions/" + session + "/players/" + username + "?access_token=" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=");
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
			*/
    		
    		URL url = new URL(IPAddress.IP + "/api/sessions/" + session + "/players/" + username + "?access_token=" + token);
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
    		con.setRequestMethod("PUT");
    		con.setRequestProperty("authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=");

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
    		
    		System.out.println("Problem was here");

			
		} catch(Exception e) {e.printStackTrace();}
    }
    
    public void leaveSession(String session, String username, String token) {
    	
    	try {
    		
    		URL url = new URL(IPAddress.IP + "/api/sessions/" + session + "/players/" + username + "?access_token=" + token);
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
    		con.setRequestMethod("DELETE");

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
    		
    		
    	} catch(Exception e) {e.printStackTrace();}
    	
    	
    }
    
    public void deleteSession(String session, String token) {
    	
    	try {
    		
    		URL url = new URL(IPAddress.IP + "/api/sessions/" + session + "?access_token=" + token);
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
    		con.setRequestMethod("DELETE");

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
    		
    	} catch(Exception e) {e.printStackTrace();}
    	
    	
    }
    
    public void launchSession(String session, String token) {
    	
    	try {
    		
    		URL url = new URL(IPAddress.IP + "/api/sessions/" + session + "?access_token=" + token);
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
    		con.setRequestMethod("POST");

  //  		int status = con.getResponseCode();
    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer content = new StringBuffer();
    		while((inputLine = in.readLine()) != null) {
    			content.append(inputLine);
    		}
    		in.close();
    		con.disconnect();
//    		System.out.println("Response status: " + status);
    		System.out.println(content.toString());
    		
    	} catch(Exception e) {}
    	
    	
    }
    
    public void refresh() {
    //	window.clear();
   // 	window.draw(background, 0, 0);
		creators.clear();
		sessionNames.clear();
		sessionIds.clear();
		mySessionIds.clear();
//		Arrays.fill(numPlayers, 0);
		Arrays.fill(sessionsLaunched, 0);
		
		// Update game services and sessions
		 findGameSessions();
    }
    
    public void setRefreshToken(String pRefreshToken) {
    	refreshToken = pRefreshToken;
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
    
    public void findGameSessions() {
    
        try {
        	
        	URL url = new URL(IPAddress.IP + "/api/sessions");
        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
        	con.setRequestMethod("GET");
        	con.setRequestProperty("cookie", "JSESSIONID=54B2765BECFB1BBFE5BE8B1442E45782");
        	con.setRequestProperty("authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=");
        	con.setRequestProperty("user-agent", "advanced-rest-client");
        	con.setRequestProperty("accept", "*/*");

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
        	Iterator<String> keys = obj.getJSONObject("sessions").keys();

        	int sessionIndex = 0;
        	
        	
        	
        	
        
        	while (keys.hasNext()) {
        		String sessionID = keys.next();
                sessionIds.add(sessionID);
                JSONArray s = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONArray("players");
                if (numPlayers[sessionIndex] != s.length() || sessionIndex > lastLength ) {
                	window.clear();
					window.draw(background, 0, 0);
				
                }
                
            	numPlayers[sessionIndex] = s.length();
               
                String creator = obj.getJSONObject("sessions").getJSONObject(sessionID).getString("creator");
                boolean launched = obj.getJSONObject("sessions").getJSONObject(sessionID).getBoolean("launched");
                if (launched) {
                	sessionsLaunched[sessionIndex] = 1;
                }
                
                for (int i = 0; i < s.length(); i++) {
                	if (s.get(i).equals(myUsername)) {
                		mySessionIds.put(sessionIndex, sessionID);
                	}
                }
                
                creators.add(creator);
                if (creator.equals(myUsername)) {
                	sessionsCreated[sessionIndex] = 1;
                	
                }
                int minPlayer = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONObject("gameParameters").getInt("minSessionPlayers");
                int maxPlayer = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONObject("gameParameters").getInt("maxSessionPlayers");
                
                minPlayers[sessionIndex] = minPlayer;
                maxPlayers[sessionIndex++] = maxPlayer;
                
                String sName = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONObject("gameParameters").getString("displayName");
                sessionNames.add(sName);
                
                
        	}
        	
        	if (sessionIndex - 1 != lastLength) {
        		window.clear();
				window.draw(background, 0, 0);
				lastLength = sessionIndex - 1;
        	} 
        	
        	
        	
        } catch(Exception e) {e.printStackTrace();}
    }
    
    
    
    public JSONArray getPlayers() {
		
		JSONArray s = null;
		try {
			URL url = new URL(IPAddress.IP + "/api/sessions/" + mySessionIds.get(indexLaunched));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=");

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
		 	JSONObject obj = new JSONObject(content.toString());
		 	
		 	
		 	
            s = obj.getJSONArray("players");
		 	for (int i = 0; i < s.length(); i++) {
		 		System.out.println(s.get(i));
		 	}
            
		 	
                
                
        	
		} catch(Exception e) {e.printStackTrace();}
		
		return s;
	}
    
    
    
    
    public String getLaunchedID() {
    	return mySessionIds.get(indexLaunched);
    }
     
    @Override
    public void handleKeyRelease(int value) {}

    @Override
    public void handleKeyType(char key) {}
    
    @Override
    public void handleKeyPress(int value) {
    	
    	
    }
    
    
    
	public void close() {
		window.close();
	}

}