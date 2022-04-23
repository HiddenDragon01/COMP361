
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.json.JSONObject;
import org.minueto.*; 
import org.minueto.image.*; 
import org.minueto.window.*; 

public class ClientGame2 {
	private int currRound;
	private GamePhase currGamePhase;
	private int numOfPlayers;
	private GameEdition gameEdition;
	private List<GameVariant> gameVariants;
	private GameBoard myGameBoard;
	private Player myCurrentPlayer;
	private Map<String, Player> players;
//	private Boot boot;
	private String myUsername;
	private boolean isCreator;
	
	
	public ClientGame2() {
		currRound = 0;
		gameVariants = new ArrayList<>();
		players =  new HashMap<>();
	}
	
	
	public Player getPlayer(String name) {
		return players.get(name);
	}
	
	public void putPlayer(Player player) {
		players.put(player.getName(), player);
	}
	
	public static void main(String[] args) throws IOException {
		ClientGame2 game = new ClientGame2();
		game.start();
	}
	
	public void start() throws IOException {
		
		//this.isCreator = isCreator;
		
		// comment out lobbyservice stuff for now
		/*
		refreshToken = refreshMyToken(refreshToken);
		myUsername = findUsername(refreshToken);
		
		postInfo(myUsername, 835, 255, 0);
		*/
		MinuetoWindow window; 
        MinuetoImage background;
        MinuetoFont fontArial14;
        MinuetoImage redBootPic;
        MinuetoImage blueBootPic;
        MinuetoImage updatedPoints;
        MinuetoImage updatedPointsBlue;
		
		 String imgDir = System.getProperty("user.dir") + "/GameAssets/";
	        
	        try {
	            background = new MinuetoImageFile(imgDir + "Background.png");
	        } catch(MinuetoFileException e) {
	            System.out.println("Could not load image file");
	            return;
	        }
	        
	        try {
	            redBootPic = new MinuetoImageFile(imgDir + "Boots/boot-red.png");
	        } catch(MinuetoFileException e) {
	            System.out.println("Could not load image file");
	            return;
	        }
	        
	        try {
	            blueBootPic = new MinuetoImageFile(imgDir + "Boots/boot-blue.png");
	        } catch(MinuetoFileException e) {
	           System.out.println("Could not load image file");
	           return;
	        }	
	        
	        
	        
		
	        redBootPic = redBootPic.scale(0.125,0.125);
	        Boot redBoot = new Boot(redBootPic, Town.getTownsMap().get(TownName.ELVENHOLD), BootColor.RED);
	        Player p1 = new Player("biladou", "flower12", 12, redBoot);
	        p1.setCurrentXYPosition(835,255);
	        
	        blueBootPic = blueBootPic.scale(0.125,0.125);
	        Boot blueBoot = new Boot(blueBootPic, Town.getTownsMap().get(TownName.ELVENHOLD), BootColor.BLUE);
	        Player p2 = new Player("filou2000", "allo1213", 13, blueBoot);
	        p2.setCurrentXYPosition(875,255);
		
		
		
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
        
        window.render();
        Thread.yield();
       
    	//******************** Client receives player position from server
        Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			echoSocket = new Socket("localhost",4444);
			out = new PrintWriter(echoSocket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Do not know about host: localhost");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("could not get I/O for the connection to: localhost");
			System.exit(1);
		}
		
		
		String str = in.readLine();
		int x = Integer.parseInt(str);
		str = in.readLine();
		int y = Integer.parseInt(str);
		
		out.close();
		in.close();
		echoSocket.close();
		
		
		//redraw
		window.clear();
		window.draw(background, 0, 0);
        window.draw(updatedPoints, 1210, 655);
        window.draw(blueBootPic, 875, 255);
        /*
		while(true) {
			window.draw(redBootPic, x,y);
            window.render();
            Thread.yield();
		}
		*/
        window.draw(redBootPic, x,y);
        window.render();
        Thread.yield();
		
        //********************

        
	
}
}
