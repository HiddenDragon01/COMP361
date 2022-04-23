package gamelogic;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;
import org.minueto.window.MinuetoWindowInvalidStateException;

import commands.AddPlayerCommand;
import commands.ChooseBootCommand;
import commands.RemoteCommand;
import commands.SessionIDCommand;
import commands.TestCommand;

import networking.CommandChannel;

public class Client {
	
	private static final Client INSTANCE = new Client();
	
	// Field for storing the current Game instance
	private Game currGame;
	
	private boolean allReady = false;

	// Field for storing the IP of the server for easy changes
	private final static String hostIP = "172.28.58.170";
	
	public static String gameVariant;
	
	private static String myUsername;
		
	// Starts up the CommandChannel to talk to server
	private static CommandChannel clientChannel;
	
	
	private Client() {}
	
	public static Client instance() {return INSTANCE;}
	
	// Method used by commands to access the game instance to be modified
	public Game getGame()
	{
		return currGame;
	}
	
	public static void createGame(String gameVariant) {
		
		INSTANCE.currGame = new Game(GameVariant.valueOf(gameVariant.toUpperCase()), 1, myUsername);
	}
	
	
	// Methods used by GUI to inform client that a move attempt has been made.
	
	public void executeChooseBoot(BootColor c)
	{
		Player p = currGame.getPlayer(currGame.myUsername);
		if(MoveValidator.chooseBoot(p, c))
		{
			ChooseBootCommand choosebootcommand = new ChooseBootCommand(currGame.myUsername, c);
			
			clientChannel.send(hostIP, choosebootcommand);
		}
		
		else {
			// This is where the client would send a message to the GUI telling it that the player already has a boot!
		}
	}
	
	public void executeDrawRandomCounter()
	{
		// Not sure if this move has to be validated
		
		// Upon validation, sends the command to the server
	}
	
	public void executeDrawCounter(Counter c)
	{
		// Here, the counter is found by the GUI by checking which counter is at the x and y location that the player clicked on.
		
		
		// Not sure if this move has to be validated
		
		// Upon validation, sends the command to the server
	}
	
	public void executePlaceCounter(Counter c, Road r)
	{
		if(MoveValidator.placeCounter(c, r))
		{
			// Upon validation, sends the command to the server
			
		}
		
	}
	
	public void executeTravelOnRoad(Player p, Road r, ArrayList<TravelCard> cardsUsed)
	{
		if(MoveValidator.travelOnRoad(p, r, cardsUsed))
		{
			// Upon validation, sends the command to the server
			
//			UpdatePlayerCommand update = new UpdatePlayerCommand(p.getPlayerId(), r.getDestinationTown(), cardsUsed);
//			
//			clientChannel.send("172.28.9.138", (RemoteCommand) update);
			
			TestCommand test = new TestCommand();
			
			clientChannel.send("localhost", (RemoteCommand) test);
			
		}
		
	}
	
	public void executePassTurn()
	{
		// Not sure if this move has to be validated
		
		// Upon validation, sends the command to the server
	}
	
	
	public void executeChosenCounterToKeep(Counter c)
	{
		// Not sure if this move has to be validated
		
		// Upon validation, sends the command to the server
	}
	
	
	public static String findUsername(String token) {
    	
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
	
//	public static String refreshMyToken(String refreshToken) {
//    	
//    	String token = "";
//    	
//    	try {
//    		
//    		URL url = new URL("http://172.28.9.138:4242/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken);
//    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//    		con.setRequestMethod("POST");
//
//    		/* Payload support */
//    		con.setDoOutput(true);
//    		DataOutputStream out = new DataOutputStream(con.getOutputStream());
//    		out.writeBytes("user_oauth_approval=true&_csrf=19beb2db-3807-4dd5-9f64-6c733462281b&authorize=true");
//    		out.flush();
//    		out.close();
//
//    		int status = con.getResponseCode();
//    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//    		String inputLine;
//    		StringBuffer content = new StringBuffer();
//    		while((inputLine = in.readLine()) != null) {
//    			content.append(inputLine);
//    		}
//    		in.close();
//    		con.disconnect();
//    		
//    		JSONObject obj = new JSONObject(content.toString());
//    		token = obj.getString("access_token");
//    		token = token.replace("+", "%2B");
//	    		
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    	}
//    	
//    	return token;
//    	
//    }
//
//	
//	
//	public void startChooseBoot() {
//		
//		System.out.println("All players have been added!");
//		
//		
//		
//		
//		// Starts up the boot selection screen
//		MinuetoWindow window = new MinuetoFrame(1400, 700, true);
//		GameGUI gui = new GameGUI(INSTANCE.currGame, window, INSTANCE.currGame.getPlayer(myUsername));
//		ChooseBootGUI chooseBoot = new ChooseBootGUI(gui);
//		chooseBoot.start();
//		try {
//			chooseBoot.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
//		// Start up the Login Screen
//		GUI_Login loginScreen = new GUI_Login();
//		String refreshToken = "";
//		
//		
		
		
		// For some reason, closing a window gives an error. Therefore, just 
//		// ignore the exception
//		try {
//			
//			refreshToken = loginScreen.start();
//			loginScreen.close();
//		
//		}
//
//		catch(MinuetoWindowInvalidStateException e) {} 
//		
//		
//		
//		
		
		
//		
//		// Start up the lobby screen
//		GUI_Lobby ls = new GUI_Lobby();
//
//		
//		boolean isCreator = false; 
//		
//		// For some reason, closing a window gives an error. Therefore, just 
//		// ignore the exception
//		try {
//			
//			ls.setRefreshToken(refreshToken);
//			isCreator = ls.start();
//			
//			System.out.println("Lobby Service Ended!!!!");
//		
//		}
//		
//		
//	
//		catch(MinuetoWindowInvalidStateException e) {} 
//		
//		myUsername = findUsername(refreshMyToken(refreshToken));
//		
//		System.out.println("My username is: " + myUsername);
//		
//		
//		// Start connection to Server
//		clientChannel = new CommandChannel(hostIP, 4444);
//		
//		TestCommand test = new TestCommand();
//		
//		clientChannel.send(hostIP, test);
//		
		
//		
//		if (isCreator) {
//			String launchedID = ls.getLaunchedID();
//			System.out.println(launchedID);
//			
//
//			SessionIDCommand sessionidcmd = new SessionIDCommand(launchedID);
//			
//			clientChannel.send(hostIP, sessionidcmd);
//			
//			System.out.println("Sent the session id to the server");
//			
//		}
		
		
//		INSTANCE.clientChannel.acceptCommandsFrom(INSTANCE.hostIP);
		
	
		// Starts up the main GUI screen

		
		
	}

	public static void startChooseBoot() {
		// TODO Auto-generated method stub
		
	}

	public static void addPlayer(String aPlayerID) {
		// TODO Auto-generated method stub
		
	}
	
}