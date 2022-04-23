import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*;

public class LobbyScreen implements MinuetoKeyboardHandler, MinuetoMouseHandler {
	
	private String sessionName;
	private final MinuetoWindow window;
	private MinuetoImage background;
	private MinuetoImage inputField;
	private ArrayList<String> sessionNames;
	private int[] numPlayers;
	private ArrayList<String> creators;
	private ArrayList<String> gameServices;
	private ArrayList<String> sessionIds;
	private boolean wrongSessionName;
	private String refreshToken;
	private int[] sessionsJoined;
	private int[] sessionsCreated;
	private int[] sessionsLaunched;
	private int[] minPlayers;
	private int[] maxPlayers;
	private String myUsername;
	private boolean toReturn;
	private int indexPlaying;


	
	
    /**
     * Displays the lobby screen.
     */
	
	public LobbyScreen() {
		
		window = new MinuetoFrame(1400, 700, true);
		sessionNames = new ArrayList<>();
		numPlayers = new int[10];
		creators = new ArrayList<>();
		sessionName = "";
		gameServices = new ArrayList<>();
		wrongSessionName = false;
		sessionIds = new ArrayList<>();
		refreshToken = "";
		sessionsJoined = new int[10];
		sessionsCreated = new int[10];
		sessionsLaunched = new int[10];
		minPlayers = new int[10];
		maxPlayers = new int[10];
		myUsername = "";
		indexPlaying = 0;
		
		
		
		
	}
	
	
    public boolean start() {
    	
    	
    	MinuetoImage sumbitButton;
    	MinuetoImage smallerSumbitButton;
        MinuetoEventQueue queue;
        queue = new MinuetoEventQueue();
        window.registerKeyboardHandler(this, queue);
        window.registerMouseHandler(this, queue);
        
        // Load images
        String imgDir = System.getProperty("user.dir") + "/GameAssets/";
        try {
            background = new MinuetoImageFile(imgDir + "lobby_background.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return false;
        }
        
        try {
            inputField = new MinuetoImageFile(imgDir + "LoginField.png");
        } catch(MinuetoFileException e) {
     
        	System.out.println("Could not load image file");
            return false;
        }
        
        try {
            sumbitButton = new MinuetoImageFile(imgDir + "LoginSubmit.png");
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
        
       

        
        // Set up text to draw
        MinuetoFont fontArial14 = new MinuetoFont("Arial", 40, false, false); 
        MinuetoFont fontArial15 = new MinuetoFont("Arial", 30, false, false); 
        MinuetoFont fontArial16 = new MinuetoFont("Arial", 25, false, false); 
        MinuetoFont fontArial17 = new MinuetoFont("Arial", 28, false, false); 
        MinuetoImage createNewSessionText = new MinuetoText("Create New Session: " ,fontArial14, MinuetoColor.BLACK); 
        MinuetoImage gameServersText = new MinuetoText("Game Servers: " ,fontArial14, MinuetoColor.BLACK); 
        MinuetoImage submitText = new MinuetoText("Create", fontArial14,MinuetoColor.WHITE);
        MinuetoImage launchText = new MinuetoText("Launch", fontArial17,MinuetoColor.WHITE);
        MinuetoImage deleteText = new MinuetoText("Delete", fontArial15,MinuetoColor.WHITE);
        MinuetoImage joinText = new MinuetoText("Join", fontArial15,MinuetoColor.WHITE);
        MinuetoImage leaveText = new MinuetoText("Leave", fontArial15,MinuetoColor.WHITE);
        MinuetoImage playText = new MinuetoText("Play", fontArial15,MinuetoColor.WHITE);
        MinuetoImage watchText = new MinuetoText("Watch", fontArial15,MinuetoColor.WHITE);
        MinuetoImage availSessionsText = new MinuetoText("Available sesions",fontArial14,MinuetoColor.BLACK);
        MinuetoImage gameText = new MinuetoText("Game",fontArial14,MinuetoColor.BLACK);
        MinuetoImage creatorText = new MinuetoText("Creator",fontArial14,MinuetoColor.BLACK);
        MinuetoImage playersText = new MinuetoText("Players",fontArial14,MinuetoColor.BLACK);
        MinuetoImage refreshText = new MinuetoText("Refresh",fontArial14,MinuetoColor.WHITE);
        MinuetoImage noGameService = new MinuetoText("The game service does not exist.",fontArial16,MinuetoColor.RED);
        
        window.setVisible(true);
        window.draw(background, 0, 0);
        window.draw(inputField, 450, 60);
        
        findGameSessions();
        findGameServices();
       
        
        
       

        while(true) { 
        	
        	if (toReturn) {
        		
        		if (creators.get(indexPlaying).equals(myUsername)) {
        			return true;
        		}
        		
        		return false;
        	}
        	
        	// Draw shapes
        	//window.draw(players, 0, 0);	
        	//window.draw(loginTitle, 670, 20);
        	window.draw(createNewSessionText, 520, 10);
        	window.draw(gameServersText, 65, 10);
        	
        	
        	if (wrongSessionName) {
        		window.draw(noGameService, 1000, 75);
        	}
        	
        	int drawIndex = 60;
        	
        	for (int i = 0; i < gameServices.size(); i++) {
        		
        		
        		MinuetoImage gameServer = new MinuetoText(gameServices.get(i),fontArial14,MinuetoColor.BLACK);
        		window.draw(gameServer, 65, drawIndex);
        		drawIndex += 50;
        		
        	
        	}
        	
        	
        	
        	window.draw(sumbitButton, 620, 150);
        	window.draw(sumbitButton, 1095, 150);
        	
        	window.draw(submitText, 648, 162);
        	window.draw(refreshText, 1120, 160);
        	
        	window.draw(availSessionsText, 570, 230);
         	window.draw(gameText, 450, 280);
         	window.draw(creatorText, 670, 280);
         	window.draw(playersText, 900, 280);
         	
	            
        	while(queue.hasNext()) {
        		
        		queue.handle();
        		MinuetoImage updatedSessionName;
        		updatedSessionName = new MinuetoText(String.valueOf(String.valueOf(sessionName).toLowerCase()) ,fontArial14,MinuetoColor.BLACK);
        		window.draw(updatedSessionName, 470, 70);
		            
        		for (int i = 0; i < sessionNames.size(); i++) {
        			
        			int drawLine = 320 + (i * 50);
        			
        			MinuetoImage SessionName;
        			SessionName = new MinuetoText(String.valueOf(sessionNames.get(i)) ,fontArial14,MinuetoColor.BLACK);
    	        	window.draw(SessionName, 450, drawLine);
	        		MinuetoImage creatorDisplay;
	        		creatorDisplay = new MinuetoText(String.valueOf(creators.get(i)) ,fontArial14,MinuetoColor.BLACK);
	        		
	        		boolean created = false;
	        		if (creators.get(i).equals(myUsername)) {
	        			created = true;
	        		}
	        		
	        		window.draw(creatorDisplay, 670, drawLine);
	        		MinuetoImage numPlayersDisplay;
	        		numPlayersDisplay = new MinuetoText(String.valueOf(numPlayers[i]) ,fontArial14,MinuetoColor.BLACK);
            		window.draw(numPlayersDisplay, 900, drawLine);
            		
            		
            		if ((sessionsLaunched[i] == 1 && sessionsJoined[i] == 1) || 
            				(sessionsLaunched[i] == 1 && created)) {
            			window.draw(smallerSumbitButton, 1240, drawLine);
            			window.draw(playText, 1257, drawLine);
            		}
            		
            		else if (sessionsLaunched[i] == 1 && sessionsJoined[i] == 0) {
            			window.draw(smallerSumbitButton, 1240, drawLine);
            			window.draw(watchText, 1248, drawLine);
            		}
            		
            		else if (created) {
            			
            			window.draw(smallerSumbitButton, 1100, drawLine);
            			window.draw(deleteText, 1105, drawLine);
            			
            			if (numPlayers[i] >= minPlayers[i]) {
            			
	        				window.draw(smallerSumbitButton, 1240, drawLine);
	            			window.draw(launchText, 1242, drawLine);
            			}
            		}
            		
            		else if (sessionsJoined[i] == 0 && numPlayers[i] <= maxPlayers[i]) {
	            		window.draw(smallerSumbitButton, 1240, drawLine);
	            		window.draw(joinText, 1255, drawLine);
            		} else {
            			window.draw(smallerSumbitButton, 1240, drawLine);
	            		window.draw(leaveText, 1250, drawLine);
            		}
            		
            		
            	
        		}
        		
        		
        		
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
	
    @Override
	public void handleMouseRelease(int x, int y, int button) {
    	
		
		if (button == 1)
        {
			// Player created a session
    		if ((x > 637) && (x < 785) && (y > 153) && (y < 221))
    		{	
    			
    			
    			for (int i = 0; i < gameServices.size(); i++) {
    				if (gameServices.get(i).equalsIgnoreCase(sessionName)) {
    					
    					sessionName = gameServices.get(i);
    					
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
    						
    						URL url = new URL("http://172.28.9.138:4242/api/sessions?access_token=" + newToken);
    						HttpURLConnection con = (HttpURLConnection) url.openConnection();
    						con.setRequestMethod("POST");
    						con.setRequestProperty("Content-Type", "application/json");

    						/* Payload support */
    						con.setDoOutput(true);
    						DataOutputStream out = new DataOutputStream(con.getOutputStream());
    						out.writeBytes("{\n");
    						String username = findUsername(newToken);
    						myUsername = username;
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
    					
    					
    					
    					
    					
    					break;
    				}
    				
    				// Game Service was not found. Display the error message.
    				if (i == gameServices.size() - 1) {
    					wrongSessionName = true;
    				}
    			}
    	
   		    	
   		    	
    		}
    		
    		
    		
    		
    		// Player clicked on one of the join or leave buttons
    		if ((x > 1243) && (x < 1338))
    		{	
    			if (y > 325) {
    				
	    			int indexClicked = Math.round((y - 320)/50);
	    			
	    			
	    			// Launch button clicked
	    			if (sessionsCreated[indexClicked] == 1 && 
	    					sessionsLaunched[indexClicked] == 0) {
	    				
	    				String token = refreshMyToken();
		    			String session = sessionIds.get(indexClicked);
		    			launchSession(session, token);
		    			sessionsLaunched[indexClicked] = 1;
		    			
		    			refresh();
	    				
		    			// Play button clicked
	    			} else if ((sessionsCreated[indexClicked] == 1 && 
	    					sessionsLaunched[indexClicked] == 1) || (sessionsJoined[indexClicked] == 1 && 
	    					sessionsLaunched[indexClicked] == 1)) {
	    				
	    					System.out.println("Playing");
	    					
	    					toReturn = true;
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
	    		
    			}
    		}
    		
    		// Player clicked on the delete button
    		if ((x > 1101) && (x < 1197))
    		{	
    			if (y > 325) {
    				
    				int indexClicked = Math.round((y - 320)/50);
    				
    				if (sessionsCreated[indexClicked] == 1 && sessionsLaunched[indexClicked] == 0) {
    					
    					String token = refreshMyToken();
		    			String session = sessionIds.get(indexClicked);
		    			deleteSession(session, token);
		    			sessionsCreated[indexClicked] = 0;
		    			
		    			refresh();
    				}
	    		
    			}
    		}
    		
    		// Refresh button
    		if ((x > 1097) && (x < 1275) && (y > 153) && (y < 223))
    		{	
    			
    			 refresh();
   		    	
    		}
        }
	}
    
    public String findUsername(String token) {
    	
    	String username = "";
    	
    	try {
			
			
			URL url = new URL("http://172.28.9.138:4242/oauth/username?access_token=" + token);
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
    
    public void joinSession(String session, String username, String token) {
    	
    	try {
			
			/*
			URL url = new URL("http://172.28.9.138:42422/api/sessions/" + session + "/players/" + username + "?access_token=" + token);
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
    		
    		URL url = new URL("http://172.28.9.138:4242/api/sessions/" + session + "/players/" + username + "?access_token=" + token);
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
    		
    		URL url = new URL("http://172.28.9.138:4242/api/sessions/" + session + "/players/" + username + "?access_token=" + token);
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
    		
    		URL url = new URL("http://172.28.9.138:4242/api/sessions/" + session + "?access_token=" + token);
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
    		
    		URL url = new URL("http://172.28.9.138:4242/api/sessions/" + session + "?access_token=" + token);
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
    		con.setRequestMethod("POST");

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
    
    public void refresh() {
    	window.clear();
    	window.draw(background, 0, 0);
    	window.draw(inputField, 450, 60);
		creators.clear();
    	gameServices.clear();
		sessionNames.clear();
		sessionIds.clear();
		Arrays.fill(numPlayers, 0);
		Arrays.fill(sessionsLaunched, 0);
		
		// Update game services and sessions
		 findGameServices();
		 findGameSessions();
    }
    
    public void setRefreshToken(String pRefreshToken) {
    	refreshToken = pRefreshToken;
    }
    
    public String refreshMyToken() {
    	
    	String token = "";
    	
    	try {
    		
    		URL url = new URL("http://172.28.9.138:4242/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken);
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
        	
        	URL url = new URL("http://172.28.9.138:4242/api/sessions");
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
        	
        	// Keep these lines. For some reason, without them, the program crashes.
        	System.out.println("Response status: " + status);
        	System.out.println(content.toString());
        	
        	
        	JSONObject obj = new JSONObject(content.toString());
        	Iterator<String> keys = obj.getJSONObject("sessions").keys();

        	int sessionIndex = 0;
        	
        
        	while (keys.hasNext()) {
        		String sessionID = keys.next();
                sessionIds.add(sessionID);
                JSONArray s = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONArray("players");
                numPlayers[sessionIndex] = s.length();
                String creator = obj.getJSONObject("sessions").getJSONObject(sessionID).getString("creator");
                boolean launched = obj.getJSONObject("sessions").getJSONObject(sessionID).getBoolean("launched");
                if (launched) {
                	sessionsLaunched[sessionIndex] = 1;
                }
                creators.add(creator);
                if (creator.equals(myUsername)) {
                	sessionsCreated[sessionIndex] = 1;
                }
                int minPlayer = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONObject("gameParameters").getInt("minSessionPlayers");
                int maxPlayer = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONObject("gameParameters").getInt("maxSessionPlayers");
                
                minPlayers[sessionIndex] = minPlayer;
                maxPlayers[sessionIndex++] = maxPlayer;
                
                String sessionName = obj.getJSONObject("sessions").getJSONObject(sessionID).getJSONObject("gameParameters").getString("displayName");
                sessionNames.add(sessionName);
                
                
        	}
        	
        	
        	
        } catch(Exception e) {e.printStackTrace();}
    }
    
    public void findGameServices() {
    	
    	 // Find GameServices
        try {
        	
        	URL url = new URL("http://172.28.9.138:4242/api/gameservices");
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
   
        	boolean nameFound = false;
        	String gameService = "";
        	 
        	
        	for (int i = 0; i < content.length(); i++) {
				
				if (content.charAt(i) == 'N') {
					i += 6;
					nameFound = true;
					continue;
				}
				
				if (nameFound) {
					gameService += content.charAt(i);
					
					if (content.charAt(i+1) == '"') {
						nameFound = false;
						gameServices.add(gameService);
						gameService = "";
					}
					
				}
				
				
				
			}
        	
        } catch(Exception e) {e.printStackTrace();}
    }
    
    
     
    @Override
    public void handleKeyRelease(int value) {}

    @Override
    public void handleKeyType(char key) {}
    
    @Override
    public void handleKeyPress(int value) {
    	
    	wrongSessionName = false;

        switch(value) {
        
        
            case MinuetoKeyboard.KEY_DELETE:
            	// Enable functionality to delete from session name
            	window.clear();
            	window.draw(background, 0, 0);
            	window.draw(inputField, 450, 60);
            	
            	if (sessionName.length() != 0) {
            		sessionName = removeLast(sessionName);
            	}
            	break;
            default:
            	
            	// If did not enter delete, then add new letter 
            	if (sessionName.length() < 10) {
            		sessionName = sessionName.concat(Character.toString ((char)value));
            	}	
            	break;
        }
    }
    
	public void close() {
		window.close();
	}

}

