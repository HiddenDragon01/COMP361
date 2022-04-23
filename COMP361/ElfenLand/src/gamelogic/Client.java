package gamelogic;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;
import org.minueto.window.MinuetoWindowInvalidStateException;

import commands.AddPlayerCommand;
import commands.CardsToKeepCommand;
import commands.ChooseBootCommand;
import commands.ChooseGoldCardPileCommand;
import commands.CounterToKeepCommand;
import commands.DoubleCounterCommand;
import commands.DrawCounterCommand;
import commands.DrawFaceUpCardCommand;
import commands.DrawRandomCardCommand;
import commands.DrawRandomCounterCommand;
import commands.ElfengoldCountersToKeepCommand;
import commands.ExchangeSelectedCommand;
import commands.MoveBootCommand;
import commands.PassTravelCommand;
import commands.PassTurnCommand;
import commands.PlaceBidCommand;
import commands.PlaceCounterCommand;
import commands.RemoteCommand;
import commands.SaveGameCommand;
import commands.SessionIDCommand;
import commands.SetVisibleCounterCommand;
import commands.TestCommand;
import gui.AuctionGUI;
import gui.ChooseBootGUI;
import gui.ChooseCardsToKeepGUI;
import gui.ChooseCounterToKeepElfengoldGUI;
import gui.ChooseCounterToKeepGUI;
import gui.ChooseVisibleCounterGUI;
import gui.DrawCardGUI;
import gui.DrawCounterGUI;
import gui.EndTravelOptionGUI;
import gui.GUI_Lobby;
import gui.GUI_Login;
import gui.GameGUI;
import gui.GameGUI_Elfengold;
import gui.GameSavedGUI;
import gui.IPAddress;
import gui.MoveBootGUI;
import gui.PlaceCounterOnRoadElfenGoldGUI;
import gui.PlaceCounterOnRoadGUI;
import gui.TravelGUI;
import gui.WinnerGUI;
import networking.CommandChannel;

public class Client implements Observer{
	
	
	
	private static final Client INSTANCE = new Client();
	private static Thread lastThread = null;
	private static GUI_Lobby ls;
		
	// Field for storing the current Game instance
	private Game currGame;
	
	public boolean allReady = false;

	// Field for storing the IP of the server for easy changes
	public final static String hostIP = "172.28.255.73";
	
	public static String gameVariant;
	
	private static String myUsername;
		
	// Starts up the CommandChannel to talk to server
	public static CommandChannel clientChannel;
		
	private Client() {}
	
	public static Client instance() {return INSTANCE;}
	
	// Method used by commands to access the game instance to be modified
	public Game getGame()
	{
		return currGame;
	}
	
//	static MinuetoWindow window = new MinuetoFrame(1400, 700, true);
	static GameGUI gui;
	
	@Override
	public void restartChooseCounterGUI() {
		
		System.out.println("Starting up the gui again!");
		
		startChooseCounter(gui);
		
	}
	
	@Override
	public void restartPlaceCounterOnRoadGUI() {
		
		System.out.println("Starting up the gui again!");
		
		startPlaceCounterOnRoad(gui);
		
	}
	
	@Override
	public void restartTravelGUI() {
		
		System.out.println("Starting up the gui again!");
		
		startTravel(gui);
		
	}
	
	@Override
	public void restartChooseCounterToKeep() {
		
		System.out.println("Starting up the gui again!");
		
		startChooseCounterToKeep(gui);
		
	}
	
	@Override
	public void restartChooseVisibleCounter() {
		
		System.out.println("Starting up the gui again!");
		
		startChooseVisibleCounterGUI(gui);
		
	}
	
	@Override
	public void restartDrawCardGUI() {
		
		System.out.println("Starting up the gui again!");
		
		startDrawCardGUI(gui);
		
	}
	
	public void restartAuctionGUI() {
		
		System.out.println("Starting up the gui again!");
		
		startAuctionGUI(gui);
		
	}
	
	public void restartChooseCardsToKeep() {
		System.out.println("Starting up choose cards to keep");
		startChooseCardsToKeep(gui);
	}
	
	public void restartEndTravelGUI() {
		System.out.println("Ending travel options GUI");
		startEndTravelOptionGUI(gui);
	}
	
	
	public static void setReady() {
		
		System.out.println("Setting allREady to true");
		INSTANCE.allReady = true;
		System.out.println("set!" + INSTANCE.allReady);
	}
	
	public static void createGame(String gameVariant) {
		
		System.out.println("In the createGame method now");
		
		INSTANCE.currGame = new Game(GameVariant.valueOf(gameVariant.toUpperCase().replaceAll("\\s", "")), myUsername);
		
	}
	
	public static void createElfengoldGame(String gameVariant) {
		
		System.out.println("In the createElfengoldGame method now");
		
		INSTANCE.currGame = new ElfengoldGame(GameVariant.valueOf(gameVariant.toUpperCase().replaceAll("\\s", "")), myUsername);
		
	}
	
	public static void addPlayer(String PlayerID) {
		
		System.out.println("In the addPlayer method now");
		
		Player p = new Player();
		
		p.setName(PlayerID);
		
		INSTANCE.currGame.addPlayer(PlayerID, p);
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
		
	}
	
	public void executeDrawRandomCounter()
	{
		DrawRandomCounterCommand drawrandomcountercmd = new DrawRandomCounterCommand();
		
		clientChannel.send(hostIP, drawrandomcountercmd);
	}
	
//	public void executeDrawCounter(Counter c)
//	{
//		
//		DrawCounterCommand drawcountercmd = new DrawCounterCommand(c);
//		clientChannel.send(hostIP, drawcountercmd);
//		
//	}
	
	public void executeDrawCounter(int i)
	{
		
		DrawCounterCommand drawcountercmd = new DrawCounterCommand(i);
		clientChannel.send(hostIP, drawcountercmd);
		
	}
	
	public void executePlaceCounter(int c, Road r)
	{		
		RegionKind kind = r.getRegionKind();
		
		TownName source = r.getSourceTown();
		
		TownName dest = r.getDestinationTown();
		
		PlaceCounterCommand placeCounterCommand = new PlaceCounterCommand(c, kind, source, dest);	
		
		System.out.println("SENDING EXECUTE PLACE COUNTER COMMAND THING HERE");
		clientChannel.send(hostIP, placeCounterCommand);
			
	}
	
	public void executeTravelOnRoad(TownName townName, ArrayList<Integer> cardsUsed)
	{
		
		MoveBootCommand moveBootCommand = new MoveBootCommand(townName, cardsUsed);
		clientChannel.send(hostIP, moveBootCommand);
		
	}
	
	public void executePassTurn()
	{
		PassTurnCommand passturncmd = new PassTurnCommand();
		clientChannel.send(hostIP, passturncmd);
		
	}
	
	
	public void executeChosenCounterToKeep(int c)
	{
		// This is called when the GUI has already validated the move, so all that's left is for client to create the corresponding command
		
		// And send it to the server
		
		
		CounterToKeepCommand counterToKeepCommand = new CounterToKeepCommand(c, currGame.myUsername);
		clientChannel.send(hostIP, counterToKeepCommand);
		
	}
	
	public void executeChosenCardsToKeep(ArrayList<Integer> chosenCardIndices) {

		CardsToKeepCommand cardsToKeepCommand = new CardsToKeepCommand(chosenCardIndices);
		clientChannel.send(hostIP, cardsToKeepCommand);
	}
	
	public void executeChooseVisibleCounter(int c)
	{
		
		SetVisibleCounterCommand visibleCounterCommand = new SetVisibleCounterCommand(c, currGame.myUsername);
		clientChannel.send(hostIP, visibleCounterCommand);
		
	}
	
	public void executePlaceBid(int bid)
	{
		
		PlaceBidCommand bidCommand = new PlaceBidCommand(bid);
		clientChannel.send(hostIP, bidCommand);
		
	}
	
	public void executePassTravel(EndTravelOption option)
	{
		
		PassTravelCommand passTravelCommand = new PassTravelCommand(option);
		clientChannel.send(hostIP, passTravelCommand);
		
	}
	
	public void executeElfengoldCountersToKeep(ArrayList<Integer> counterIndices) {
		
		ElfengoldCountersToKeepCommand countersCommand= new ElfengoldCountersToKeepCommand(counterIndices, currGame.myUsername);
		clientChannel.send(hostIP, countersCommand);
	}
	
	public void executeDrawRandomCard()
	{
		DrawRandomCardCommand drawrandomcardcmd = new DrawRandomCardCommand();
		
		clientChannel.send(hostIP, drawrandomcardcmd);
	}
	
	public void executeChooseGoldCardPile()
	{
		ChooseGoldCardPileCommand drawgoldpilecmd = new ChooseGoldCardPileCommand();
		
		clientChannel.send(hostIP, drawgoldpilecmd);
	}
	
	public void executeDrawFaceUpCardCommand(int selectedIndex)
	{
		DrawFaceUpCardCommand drawgoldpilecmd = new DrawFaceUpCardCommand(selectedIndex);
		
		clientChannel.send(hostIP, drawgoldpilecmd);
	}
	
	public void executeExchangeCounterCommand(Road r1, Road r2, int c1, int c2, int exc)
	{
		RegionKind kind1 = r1.getRegionKind();
		TownName source1 = r1.getSourceTown();
		TownName dest1 = r1.getDestinationTown();
		
		RegionKind kind2 = r2.getRegionKind();
		TownName source2 = r2.getSourceTown();
		TownName dest2 = r2.getDestinationTown();
		
		ExchangeSelectedCommand exchangeselectedcmd = new ExchangeSelectedCommand(kind1, source1, dest1, kind2, source2, dest2, c1, c2, exc);
		
		clientChannel.send(hostIP, exchangeselectedcmd);
	}
	
	public void executeDoubleCounterCommand(Road r, int c1, int c2)
	{
		RegionKind kind = r.getRegionKind();
		TownName source = r.getSourceTown();
		TownName dest = r.getDestinationTown();
		
		DoubleCounterCommand doublecountercmd = new DoubleCounterCommand(kind, source, dest, c1, c2);
		
		clientChannel.send(hostIP, doublecountercmd);
		
	}
	
	public void executeSaveGame()
	{
		SaveGameCommand savegamecmd = new SaveGameCommand();
		
		clientChannel.send(hostIP, savegamecmd);
	}
	
	
	public static String findUsername(String token) {
    	
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
			
			
			
		} catch(Exception e) {}
    	
    	return username;
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

	
	
	public static void startChooseBoot() {
		
		System.out.println("All players have been added!");
		
		
		
		ls.close();
		
		// Starts up the boot selection screen
		if(!(INSTANCE.currGame instanceof ElfengoldGame)) {
			gui =  new GameGUI(INSTANCE.currGame, INSTANCE.currGame.getPlayer(myUsername));
		}
		else {
			gui =  new GameGUI_Elfengold(((ElfengoldGame) INSTANCE.currGame), INSTANCE.currGame.getPlayer(myUsername));
		}
		ChooseBootGUI chooseBoot = new ChooseBootGUI(gui);
		chooseBoot.start();
		try {
			chooseBoot.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		// Replace this with the line after the while loop once the thing is fixed
//		while (true)
//		{
//			gui.drawBackground(Optional.empty());
//		}
		
		
//		try {
//			Thread.sleep(9000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//		
//		startChooseCounter(gui);			
	}
	
	public static void startChooseCounter(GameGUI gui) {
		
		System.out.println("in startChooseCounter now");
		
//		if (lastThread != null)
//		{
//			lastThread.interrupt();
//		}
		
		DrawCounterGUI drawCounter = new DrawCounterGUI(gui);
//		lastThread=drawCounter;
		drawCounter.start();
		try {
			drawCounter.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void startPlaceCounterOnRoad(GameGUI gui) {
		
		System.out.println("in placeCounterOnRoad now");
		
		if(INSTANCE.currGame instanceof ElfengoldGame) {
			PlaceCounterOnRoadElfenGoldGUI placeCounterOnRoad = new PlaceCounterOnRoadElfenGoldGUI(gui);
			placeCounterOnRoad.start();
			try {
				placeCounterOnRoad.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		
		PlaceCounterOnRoadGUI placeCounterOnRoad = new PlaceCounterOnRoadGUI(gui);
		placeCounterOnRoad.start();
		try {
			placeCounterOnRoad.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public static void startTravel(GameGUI gui) {
		
		System.out.println("in startTravel now");
		
		
		
		TravelGUI travel = new TravelGUI(gui);
//		lastThread.interrupt();
		
		travel.start();
		
		lastThread = travel;
		try {
			travel.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void startChooseCounterToKeep(GameGUI gui) {
		
		System.out.println("in choose counter to keep now");
		
		
//		lastThread.interrupt();
		
		if(INSTANCE.currGame instanceof ElfengoldGame) {
			ChooseCounterToKeepElfengoldGUI counterToKeep = new ChooseCounterToKeepElfengoldGUI(gui);
			counterToKeep.start();
			try {
				counterToKeep.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		ChooseCounterToKeepGUI counterToKeep = new ChooseCounterToKeepGUI(gui);
		
		counterToKeep.start();
		try {
			counterToKeep.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startChooseCardsToKeep(GameGUI gui) {
		
		System.out.println("in choose cards to keep now");
		
		ChooseCardsToKeepGUI cardsToKeep = new ChooseCardsToKeepGUI(gui);
		
		cardsToKeep.start();
		try {
			cardsToKeep.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startWinnerGUI(String playerName) {
		
		WinnerGUI winner = new WinnerGUI(gui, playerName);
		
		winner.start();
		try {
			winner.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void startChooseVisibleCounterGUI(GameGUI gui) {
		
		System.out.println("in ChooseVisibleCounterGUI now");
		
		ChooseVisibleCounterGUI visiblecounter = new ChooseVisibleCounterGUI(gui);
		
		visiblecounter.start();
		try {
			visiblecounter.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startDrawCardGUI(GameGUI gui) {
		
		System.out.println("in drawcard now");
		
		
		DrawCardGUI drawCard = new DrawCardGUI(gui);

		drawCard.start();
		try {
			drawCard.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void startAuctionGUI(GameGUI gui) {
		
		System.out.println("in auction now");
		
//		lastThread.interrupt();
		AuctionGUI auction = new AuctionGUI(gui);
		
		auction.start();
		try {
			auction.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startEndTravelOptionGUI(GameGUI gui) {
		
		System.out.println("in start end travel now");
		
//		lastThread.interrupt();
		EndTravelOptionGUI endOption = new EndTravelOptionGUI(gui);
		
		endOption.start();
		try {
			endOption.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void startGameSavedGUI() {
		
		GameSavedGUI gamesaved = new GameSavedGUI(gui);
		
		gamesaved.start();
		try {
			gamesaved.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String refreshToken = "";
		
		// Start up the lobby screen
			ls = new GUI_Lobby();

			

			boolean isCreator = false; 
			
			// For some reason, closing a window gives an error. Therefore, just 
			// ignore the exception
			try {
				
				ls.setRefreshToken(refreshToken);
				isCreator = ls.start();
				
				boolean loaded = ls.loaded();
				
				
				if (loaded) {
					String savedID = ls.getID();
					System.out.println("Loading " + savedID);
				}
				
				// If loaded, then use ls.getID() to get the id of the new game.
				// Then, the new id to create a new game
				
				System.out.println("Lobby Service Ended!!!!");
			
			} catch(MinuetoWindowInvalidStateException e) {} 
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		// Start up the Login Screen
		GUI_Login loginScreen = new GUI_Login();
		String refreshToken = "";
		
		
		
		
		// For some reason, closing a window gives an error. Therefore, just 
		// ignore the exception
		try {
			
			refreshToken = loginScreen.start();
			loginScreen.close();
		
		}

		catch(MinuetoWindowInvalidStateException e) {} 
		
		
		
		// Start up the lobby screen
		ls = new GUI_Lobby();

		
		boolean isCreator = false; 
		
		// For some reason, closing a window gives an error. Therefore, just 
		// ignore the exception
		try {
			
			ls.setRefreshToken(refreshToken);
			isCreator = ls.start();
			
			boolean loaded = ls.loaded();
			
			
			if (loaded) {
				String savedID = ls.getID();
				System.out.println("Loading " + savedID);
			}
			
			// If loaded, then use ls.getID() to get the id of the new game.
			// Then, the new id to create a new game
			
			System.out.println("Lobby Service Ended!!!!");
		
		}
		
		
	
		catch(MinuetoWindowInvalidStateException e) {} 
		
		if (!isCreator) {
			// Start connection to Server
			clientChannel = new CommandChannel(hostIP, 4444);
		}
		
		myUsername = findUsername(refreshMyToken(refreshToken));
		
		System.out.println("My username is: " + myUsername);
		
//		
//		// Start connection to Server
		
		TestCommand test = new TestCommand();
		
		clientChannel.send(hostIP, test);
		
		
		
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
		
		
		INSTANCE.clientChannel.acceptCommandsFrom(INSTANCE.hostIP);
		
		
//		while(true) {
//			boolean myBool = INSTANCE.allReady;
//			if(myBool) {
//				break;
//			}
//		}
		
//		System.out.println("Out of the while loop now!");
//		
//		startChooseBoot();
		
	
		// Starts up the main GUI screen

		
		
	}

	

	

	
}