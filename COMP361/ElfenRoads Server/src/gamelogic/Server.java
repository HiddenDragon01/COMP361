package gamelogic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import commands.RemoteCommand;
import commands.TestCommand;
import networking.CommandChannel;

public class Server {
	
	private static final Server INSTANCE = new Server();

	public String sessionID ="";
	// Field for storing the current Game instance
	private Game currGame;
	
	private static String gameNameServer;
		
	public static CommandChannel serverChannel;
	
	public static Server instance() {return INSTANCE;}
	
	public static String gameVariant;
	
	public static boolean allHere;
	
	public boolean sessionIDset = false;


			
	// Method used by commands to access the game instance to be modified
	public Game getGame()
	{
		return currGame;
	}
	
	public void setGame()
	{
		currGame = null;
	}
	
	public void setSessionID(String newSessionID)
	{
		
		System.out.println("In setSessionID, old sessionID: " + sessionID);
		this.sessionID = newSessionID;
		
		this.sessionIDset = true;
		
		System.out.println("In setSessionID, new sessionID: " + sessionID);
		
		System.out.println("In setSessionID, sessionIDset: " + sessionIDset);
		
		createGame();

	}
	
	
		
		
	public static JSONArray getPlayers(String sessionID) {
		
		JSONArray s = null;
		try {
			URL url = new URL(IPAddress.IP + "/api/sessions/" + sessionID);
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
	
	public static String getVariant(String sessionID) {
		
	 	System.out.println("In here");
	 
		String s = "";
		try {
			URL url = new URL(IPAddress.IP + "/api/sessions/" + sessionID);
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
		 	
		 	gameNameServer = obj.getJSONObject("gameParameters").getString("name");
		 	
            s = obj.getJSONObject("gameParameters").getString("displayName");
		 	System.out.println("Game name: " + s);
		 	
                
                
        	
		} catch(Exception e) {e.printStackTrace();}
		
		return s;
	}
	
	public static String getRefreshToken() {
		
		String refreshToken = "";
		
		try {
			
			String authenUsername = "bgp-client-name";
			String authenPasswd = "bgp-client-pw";
			
			Authenticator.setDefault(new Authenticator() {

			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {          
			        return new PasswordAuthentication(authenUsername, authenPasswd.toCharArray());
			    }
			});
			
			URL url = new URL(IPAddress.IP + "/oauth/token?grant_type=password&username=" + "service" + "&password=" + "abc123_ABC123");
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
			System.out.println("Response status: " + status);
			System.out.println(content.toString());
			
			
			
			
			
			
			int count = 0;
			
			
			
			for (int i = 0; i < content.length(); i++) {
				
				if (content.charAt(i) == '"' ) {
					count++;
					continue;
				}
				
				
				
				if (count == 11) {
					refreshToken += content.charAt(i);
				}
				
			}
			
			
			
		} catch(Exception e) {
			return null;
		}
		
		return refreshToken;
	}
		
	public static String refreshMyToken(String refreshToken) {
	    	
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


	
	public void saveGame() {
		JSONArray names = getPlayers(INSTANCE.sessionID); 
		
		String refreshToken = getRefreshToken();
		
		String token = refreshMyToken(refreshToken);
		
		try {
			URL url = new URL("http://172.28.83.210:4242/api/gameservices/ElfgRegular/savegames/XYZ46?access_token="+token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("content-type", "application/json");
	
			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("\"gamename\": \"" + gameNameServer + "\",\n");
			out.writeBytes("\"players\": [\n");
			for(int i = 0; i < names.length(); i++) {
				String name = names.getString(i);
				out.writeBytes("    \""+ name + "\",\n");
				
			}
			out.writeBytes("],\n");
			out.writeBytes("\"savegameid\": \"XYZ46\"\n");
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
		}catch (Exception e) {e.printStackTrace();};
		
		serverChannel.resetServer();

	}
	
	public void createGame() {
		
		System.out.println("SERVER: In create game function now");
		
		String[] gameNameVariant = getVariant(INSTANCE.sessionID).split(" ", 2);
		
		String gameEdition = gameNameVariant[0];
		
		System.out.println("Game edition: " + gameEdition);
		
		gameVariant = gameNameVariant[1];
		
		System.out.println("Game variant: " + gameVariant);

		
		Game game;
		
		if(gameEdition.equalsIgnoreCase("ElfenLand")) {
			System.out.println("CREATING ELFEN LAND GAME VARIANT");
			game = new Game(GameVariant.valueOf(gameVariant.toUpperCase().replaceAll("\\s", "")), "Server");
		}
		else {
			System.out.println("CREATING ELFEN GOLD GAME VARIANT");
			game = new ElfengoldGame(GameVariant.valueOf(gameVariant.toUpperCase().replaceAll("\\s", "")), "Server");
			if(game.gameVariant == GameVariant.RANDOMTOWNVALUES) {
				TownName.randomizeGoldValues();
			}
		}
		
		
		System.out.println("SETTING CURR GAME");
		INSTANCE.currGame = game;
		
		
		System.out.println("Game created!");
		
		// get the players
		JSONArray playerArray = getPlayers(INSTANCE.sessionID);
		
		
		// create the players and add them to the participants list
		for (int i = 0; i < playerArray.length(); i++) {
			String username ="";
			try {
				username = playerArray.getString(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Player p = new Player();
			p.setName(username);
			
			System.out.println("Player created: " + username);
			
			INSTANCE.currGame.addPlayerID(username);
			
			INSTANCE.currGame.addPlayer(username, p);
		}
		
		INSTANCE.currGame.shufflePlayerIndex();
		
		serverChannel.setNumPlayers(playerArray.length());
		
		
	}
	
	
	public static void sendPlayers() {
		
		
		//serverChannel.numPlayers = playerArray.length();
		if(INSTANCE.currGame instanceof ElfengoldGame) {
			serverChannel.sendStartElfengoldGame(gameVariant);
		}
		else {
			serverChannel.sendStartGameCommand(gameVariant);
		}
		
		System.out.println("Send Game command sent!");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Server woke up!");
		
		// Send the players to each client
		for(Player p : INSTANCE.currGame.getParticipants())
		{
			System.out.println("Sending the player " + p.getName());
			serverChannel.setPlayer(p.getName());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Once all the players have been sent, let the clients know
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		serverChannel.allPlayersAdded();
		
		System.out.println("All players added command sent!");
		
		serverChannel.sendPlayerIndexArray(INSTANCE.currGame.getPlayerArray());
		
	}
		
	
	
	public static void main(String[] args){
		
		
		
		System.out.println("hello");
		// Begin accepting client connections
		serverChannel = new CommandChannel(4444);
		
		System.out.println("Waiting for clients....");
		
		
		// Start up the commandchannel and wait for clients.
		// Once all the clients have joined, in commandchannel.java, code will be executed (check there in the run method)
//		
//		try {
//			Thread.sleep(14000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("Done sleeping....");
		
		
		
		
		
		
		
		
		
//		while(!allHere)
//		{
//			// wait until everyone has joined
//		}
		
		// Wait until everyone's started the game 
		
		
		
		
	}

}
