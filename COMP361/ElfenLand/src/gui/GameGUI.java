package gui;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoStopWatch;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.handlers.MinuetoWindowHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoFrame;
import org.minueto .window.MinuetoWindow;

import gamelogic.BootColor;
import gamelogic.Client;
import gamelogic.Counter;
import gamelogic.Game;
import gamelogic.GameVariant;
import gamelogic.Player;
import gamelogic.RegionKind;
import gamelogic.Road;
import gamelogic.TownName;


public class GameGUI extends Thread {

	protected final Game game;
	protected final MinuetoWindow window = new MinuetoFrame(1400, 700, true);
	protected final MinuetoEventQueue queue = new MinuetoEventQueue();
	protected final Player player;
	protected final HashMap<TownName, Point> townCoordinates = new HashMap<>();
	protected final HashMap<TownName, Point> townpieceCoordinates = new HashMap<>();
	protected final HashMap<Road, List<Polygon>> roadPolygons = new HashMap<>();
	protected final ArrayList<Polygon> faceUpCounterPolygons = new ArrayList<>();
	protected final ArrayList<Polygon> countersInHandPolygons = new ArrayList<>();
	protected final ArrayList<Polygon> cardsInHandPolygons = new ArrayList<>();
	protected final HashMap<TownName, Polygon> townPolygons = new HashMap<>();
	protected final HashMap<Polygon, Counter> faceUpCounters = new HashMap<>();
	protected final HashMap<BootColor, MinuetoColor> colorConverter = new HashMap<>();
	
	private final Set<MinuetoMouseHandler> handlers = new HashSet<>();
	protected final HashMap<Player, Polygon> infoBoxPolygons = new HashMap<>();
	protected Player playerEnlargedInfo;
	
	protected BasicGUI guiObserver = null;
	
	
	public void setGuiObserver(BasicGUI g)
	{
		guiObserver = g;
	}
	
	public void removeGuiObserver(BasicGUI g)
	{
		guiObserver = null;
	}
	
	public GameGUI(Game game, Player player) 
	{
		this.game = game; 
		this.player = player;

		createTownCenterPoints();
		createTownPiecePoints();
		createColorConverters();
		createTownPolygons();
		createFaceUpCountersPolygons();
		createCountersInHandPolygons();
		createCardsInHandPolygons();	
		createRoadPolygons();		
		createInfoBoxPolygons();
		
		// Add handlers to queue
		registerGlobalMouseHandlers();
		
	}
	
	private final MinuetoMouseHandler infoBoxEnlarger =  new InfoBoxEnlarger();
	private final MinuetoMouseHandler saveHandler =  new SaveHandler();
	
	public void registerGlobalMouseHandlers()
	{
		if (handlers.size() == 0)
		{
			handlers.add(infoBoxEnlarger);
			handlers.add(saveHandler);
			
			for (MinuetoMouseHandler handler: handlers)
			{
				window.registerMouseHandler(handler, queue);
			}
		}
	}
	
	public void unregisterGlobalMouseHandlers()
	{
		
		for (MinuetoMouseHandler handler: handlers)
		{
			window.unregisterMouseHandler(handler, queue);
		}
		
		handlers.remove(saveHandler);
		handlers.remove(infoBoxEnlarger);
	}

	private void createTownPiecePoints() {
		townpieceCoordinates.put(TownName.ELVENHOLD, new Point(943, 289));
		townpieceCoordinates.put(TownName.BEATA, new Point(1035, 409));
		townpieceCoordinates.put(TownName.ERGEREN, new Point(1008, 233));
		townpieceCoordinates.put(TownName.GRANGOR, new Point(364, 353));
		townpieceCoordinates.put(TownName.JACCARANDA, new Point(598, 40));
		townpieceCoordinates.put(TownName.JXARA, new Point(602, 461));
		townpieceCoordinates.put(TownName.MAHDAVIKIA, new Point(398, 445));
		townpieceCoordinates.put(TownName.STRYKHAVEN, new Point(923, 469));
		townpieceCoordinates.put(TownName.TICHIH, new Point(870, 44));
		townpieceCoordinates.put(TownName.USSELEN, new Point(264, 74));
		townpieceCoordinates.put(TownName.WYLHIEN, new Point(384, 23));
		townpieceCoordinates.put(TownName.VIRST, new Point(800, 480));
		townpieceCoordinates.put(TownName.YTTAR, new Point(341, 173));
		townpieceCoordinates.put(TownName.ALBARAN, new Point(493, 261));
		townpieceCoordinates.put(TownName.KIHROMAH, new Point(411, 253));
		townpieceCoordinates.put(TownName.DAGAMURA, new Point(458, 330));
		townpieceCoordinates.put(TownName.PARUNDIA, new Point(458, 208));
		townpieceCoordinates.put(TownName.FEODOR, new Point(598, 252));
		townpieceCoordinates.put(TownName.THROTMANNI, new Point(603, 144));
		townpieceCoordinates.put(TownName.RIVINIA, new Point(732, 187));
		townpieceCoordinates.put(TownName.LAPPHALIA, new Point(731, 397));
	}

	private void createTownCenterPoints() {
		// Populate townCoordinates list
		townCoordinates.put(TownName.ELVENHOLD, new Point(875, 299));
		townCoordinates.put(TownName.BEATA, new Point(1008, 387));
		townCoordinates.put(TownName.ERGEREN, new Point(988, 205));
		townCoordinates.put(TownName.GRANGOR, new Point(331, 348));
		townCoordinates.put(TownName.JACCARANDA, new Point(603, 75));
		townCoordinates.put(TownName.JXARA, new Point(536, 463));
		townCoordinates.put(TownName.MAHDAVIKIA, new Point(333, 451));
		townCoordinates.put(TownName.STRYKHAVEN, new Point(915, 434));
		townCoordinates.put(TownName.TICHIH, new Point(881, 91));
		townCoordinates.put(TownName.USSELEN, new Point(335, 109));
		townCoordinates.put(TownName.WYLHIEN, new Point(468, 49));
		townCoordinates.put(TownName.VIRST, new Point(762, 471));
		townCoordinates.put(TownName.YTTAR, new Point(317, 222));
		townCoordinates.put(TownName.ALBARAN, new Point(564, 225));
		townCoordinates.put(TownName.KIHROMAH, new Point(447, 304));
		townCoordinates.put(TownName.DAGAMURA, new Point(559, 329));
		townCoordinates.put(TownName.PARUNDIA, new Point(458, 173));
		townCoordinates.put(TownName.FEODOR, new Point(694, 250));
		townCoordinates.put(TownName.THROTMANNI, new Point(738, 136));
		townCoordinates.put(TownName.RIVINIA, new Point(838, 199));
		townCoordinates.put(TownName.LAPPHALIA, new Point(698, 367));
	}

	private void createColorConverters() {
		colorConverter.put(BootColor.RED, new MinuetoColor(213,12, 48, 255));
		colorConverter.put(BootColor.BLUE, new MinuetoColor(17, 94, 170, 255));
		colorConverter.put(BootColor.BLACK, new MinuetoColor(38, 38, 38, 255));
		colorConverter.put(BootColor.YELLOW, new MinuetoColor(252, 226, 24, 255));
		colorConverter.put(BootColor.GREEN, new MinuetoColor(49, 120, 40, 255));
		colorConverter.put(BootColor.PURPLE, new MinuetoColor(128, 49, 98, 255));
	}

	private void createFaceUpCountersPolygons() {
		int[] rows = new int[] {8, 43, 78};
		int[] cols = new int[] {1236, 1277, 1316, 1357};
		int counter_width = 30;
		
		for (int row: rows)
		{
			for (int col: cols)
			{
				faceUpCounterPolygons.add(createPolygon(col, row, counter_width, counter_width));
			}
		}
	}

	private void createTownPolygons() {
		for (TownName t: TownName.values())
		{
			int dx = 50; int dy = 50;
			
			if (t == TownName.ELVENHOLD) { dx = 80; dy = 80; }
			
			int x = townCoordinates.get(t).x;
			int y = townCoordinates.get(t).y;
			townPolygons.put(t, createPolygon(x-dx/2, y-dy/2, dx, dy));
		}
	}

	private void createCountersInHandPolygons() {
		int[] rows;
		int[] cols;
		int counter_width;
		// Create polygons for counters in hand
		rows = new int[] {331, 388, 445, 502, 559, 616};
		cols = new int[] {73, 142};
		counter_width = 48;
		
		int slope = 0;
		for (int row: rows)
		{
			for (int col: cols)
			{
				col -= 5 * slope;
				countersInHandPolygons.add(createPolygon(col, row, counter_width, counter_width));
			}
			
			slope++;
		}
	}

	private void createCardsInHandPolygons() {
		// Create polygons for cards in hand
		int row = 545; int init_col = 251;
		int card_width = 92; int card_height = 144; int spaceBtwCard = 10;
		
		for (int i1 = 0; i1 < 8; i1++)
		{
			int col = init_col + (spaceBtwCard + card_width) * i1;
			cardsInHandPolygons.add(createPolygon(col, row, card_width, card_height));
	
		}
	}

	private void createRoadPolygons() {
		// Create polygons for clickable squares on roads		
		addToRoadPolygons(new int[] {938, 962}, new int[] {329, 347}, Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA));
		addToRoadPolygons(new int[] {777, 754}, new int[] {345, 355}, Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.LAPPHALIA));
		addToRoadPolygons(new int[] {978, 956}, new int[] {248, 252}, Road.get(RegionKind.WOODS, TownName.ELVENHOLD, TownName.ERGEREN));
		
		
		// beata
		addToRoadPolygons(new int[] {992, 1008}, new int[] {440, 416}, Road.get(RegionKind.PLAINS, TownName.BEATA, TownName.STRYKHAVEN));
				
		// strykhaven
		addToRoadPolygons(new int[] {845, 876}, new int[] {502, 489}, Road.get(RegionKind.MOUNTAINS, TownName.STRYKHAVEN, TownName.VIRST));
		
		// ergeren
		addToRoadPolygons(new int[] {914, 939}, new int[] {146, 150}, Road.get(RegionKind.WOODS, TownName.ERGEREN, TownName.TICHIH));
		
		// tichih
		addToRoadPolygons(new int[] {798, 820}, new int[] {120, 113}, Road.get(RegionKind.PLAINS, TownName.TICHIH, TownName.THROTMANNI));
		addToRoadPolygons(new int[] {679, 731}, new int[] {77, 66}, Road.get(RegionKind.MOUNTAINS, TownName.TICHIH, TownName.JACCARANDA));
		
		// rivinia
		addToRoadPolygons(new int[] {765, 781}, new int[] {262, 240}, Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.LAPPHALIA));
		addToRoadPolygons(new int[] {744, 764}, new int[] {218, 206}, Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.FEODOR));
		addToRoadPolygons(new int[] {783, 759}, new int[] {153, 144}, Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.THROTMANNI));
		
		// virst
		addToRoadPolygons(new int[] {703, 701}, new int[] {410, 387}, Road.get(RegionKind.PLAINS, TownName.VIRST, TownName.LAPPHALIA));
		addToRoadPolygons(new int[] {630, 664}, new int[] {491, 482}, Road.get(RegionKind.PLAINS, TownName.VIRST, TownName.JXARA));
		
		// lapphalya
		addToRoadPolygons(new int[] {720, 696}, new int[] {277, 264}, Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.FEODOR));
		addToRoadPolygons(new int[] {602, 629}, new int[] {353, 365}, Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.DAGAMURA));
		addToRoadPolygons(new int[] {614, 640}, new int[] {412, 404}, Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.JXARA));
		
		// feodor
		addToRoadPolygons(new int[] {694, 706}, new int[] {188, 171}, Road.get(RegionKind.DESERT, TownName.FEODOR, TownName.THROTMANNI));
		addToRoadPolygons(new int[] {597, 618}, new int[] {284, 271}, Road.get(RegionKind.DESERT, TownName.FEODOR, TownName.DAGAMURA));
		addToRoadPolygons(new int[] {618, 641}, new int[] {221, 224}, Road.get(RegionKind.DESERT, TownName.FEODOR, TownName.ALBARAN));
		
		// throtmanni
		addToRoadPolygons(new int[] {624, 648}, new int[] {177, 167}, Road.get(RegionKind.DESERT, TownName.THROTMANNI, TownName.ALBARAN));
		addToRoadPolygons(new int[] {633, 662}, new int[] {100, 109}, Road.get(RegionKind.MOUNTAINS, TownName.THROTMANNI, TownName.JACCARANDA));

		// jaccamanda
		addToRoadPolygons(new int[] {515, 540}, new int[] {48, 46}, Road.get(RegionKind.MOUNTAINS, TownName.JACCARANDA, TownName.WYLHIEN));

		// albaran
		addToRoadPolygons(new int[] {558, 559}, new int[] {244, 268}, Road.get(RegionKind.DESERT, TownName.ALBARAN, TownName.DAGAMURA));
		addToRoadPolygons(new int[] {488, 510}, new int[] {170, 172}, Road.get(RegionKind.DESERT, TownName.ALBARAN, TownName.PARUNDIA));
		addToRoadPolygons(new int[] {503, 533}, new int[] {106, 118}, Road.get(RegionKind.DESERT, TownName.ALBARAN, TownName.WYLHIEN));
		
		// dagamura
		addToRoadPolygons(new int[] {541, 533}, new int[] {378, 398}, Road.get(RegionKind.WOODS, TownName.DAGAMURA, TownName.JXARA));
		addToRoadPolygons(new int[] {523, 503}, new int[] {309, 298}, Road.get(RegionKind.WOODS, TownName.DAGAMURA, TownName.KIHROMAH));
		addToRoadPolygons(new int[] {450, 480}, new int[] {374, 361}, Road.get(RegionKind.MOUNTAINS, TownName.DAGAMURA, TownName.MAHDAVIKIA));

		// jxara
		addToRoadPolygons(new int[] {415, 439}, new int[] {467, 460}, Road.get(RegionKind.MOUNTAINS, TownName.JXARA, TownName.MAHDAVIKIA));
		
		// mahdavikia
		addToRoadPolygons(new int[] {297, 292}, new int[] {394, 414}, Road.get(RegionKind.MOUNTAINS, TownName.MAHDAVIKIA, TownName.GRANGOR));

		// grangor
		addToRoadPolygons(new int[] {311, 312}, new int[] {265, 291}, Road.get(RegionKind.MOUNTAINS, TownName.GRANGOR, TownName.YTTAR));
		
		// yttar
		addToRoadPolygons(new int[] {343, 328}, new int[] {135, 149}, Road.get(RegionKind.WOODS, TownName.YTTAR, TownName.USSELEN));

		// parundia
		addToRoadPolygons(new int[] {378, 402}, new int[] {139, 136}, Road.get(RegionKind.WOODS, TownName.PARUNDIA, TownName.USSELEN));
		addToRoadPolygons(new int[] {441, 435}, new int[] {97, 120}, Road.get(RegionKind.PLAINS, TownName.PARUNDIA, TownName.WYLHIEN));
		
		// usselen
		addToRoadPolygons(new int[] {375, 400}, new int[] {43, 44}, Road.get(RegionKind.PLAINS, TownName.WYLHIEN, TownName.USSELEN));
	}
	
	
	public class InfoBoxEnlarger implements MinuetoMouseHandler
	{

		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int arg0, int arg1, int arg2) {
			
			if (playerEnlargedInfo == null)
			{
				for (Player player: infoBoxPolygons.keySet())
				{
					Polygon p = infoBoxPolygons.get(player);
					if (p.contains(new Point(arg0, arg1))) {
						playerEnlargedInfo = player;
						drawInfoBox(playerEnlargedInfo);
					}
				}
			}
			
			else if (infoBoxPolygons.get(playerEnlargedInfo).contains(new Point(arg0, arg1))) {
				playerEnlargedInfo = null;
				drawBackground();
				
			}
			
			else { return; }
			
			if (guiObserver != null)
			{
				guiObserver.drawSelection();
			}
		}
	}
	
	
	public class SaveHandler implements MinuetoMouseHandler
	{

		private final Polygon saveButton = createPolygon(1286, 453, 93, 34);
		
		@Override
		public void handleMouseMove(int arg0, int arg1) {}

		@Override
		public void handleMousePress(int arg0, int arg1, int arg2) {}

		@Override
		public void handleMouseRelease(int arg0, int arg1, int arg2) {
			
			if (saveButton.contains(new Point(arg0, arg1)))
			{
				//TODO: send a command
				
				Client.instance().executeSaveGame();
				
				System.out.println("save");
			}
			
		}
		
	}
	
	
	/**
	 * @param x: top left x coordinate
	 * @param y: top left y coordinate
	 * @param dx: width of the rectangle
	 * @param dy: height of the rectangle
	 * @return Polygon object for that rectangle
	 */
	protected static Polygon createPolygon(int x, int y, int dx, int dy)
	{
		return new Polygon(new int[] {x, x+dx, x+dx, x}, 
				new int[] {y, y, y+dy, y+dy}, 4);
	}
	
	protected void addToRoadPolygons(int[] xs, int[] ys, Road road)
	{	assert xs.length == ys.length;
		
		int maxNum = xs.length;
		
		String gameEdition = "Elfengold";
		if (gameEdition.equals("Elfenland"))
		{
			if (road.getRegionKind() == RegionKind.LAKE 
					|| road.getRegionKind() == RegionKind.RIVER)  { return; }
			
			else { maxNum = 2; }
		}
	
		int box_size = 20;

		if (!roadPolygons.containsKey(road))
		{
			List<Polygon> polygons = new ArrayList<>();
			roadPolygons.put(road, polygons);
		}
		
		List<Polygon> polygons = roadPolygons.get(road);
		
		for (int i=0; i<maxNum; i++)
		{
			polygons.add(createPolygon(xs[i], ys[i], box_size, box_size));
		}
	}
	
	protected MinuetoWindow getWindow() { return window; }
	
	protected void displayMessage(String txt)
	{
		assert txt != null;
		
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
        
        // Display the message for x seconds
        while (watch.getTime() < 2000 )
        {
        	window.draw(scaled_dialog_box, window.getWidth() / 2 - scaled_dialog_box.getWidth() / 2, 
        			window.getHeight() / 2 - scaled_dialog_box.getHeight() / 2);
        	
        	int lineNo = 0;
        	for (MinuetoImage minuetoText: minuetoTexts)
        	{
        		window.draw(minuetoText, window.getWidth() / 2 - minuetoText.getWidth() / 2, 
        				window.getHeight() / 2 - scaled_dialog_box.getHeight() / 2 + text_frame + lineNo*(text_Yspacing+textHeight));
        		lineNo ++;
        	}
        	
        	window.render();
            Thread.yield();
        }
        
        drawBackground();
        
        watch.reset();
        
	}
	

	protected void drawBackground()
	{
		MinuetoImage background = ImageLoader.get("new-background.png");
		window.draw(background, 0, 0);
		
		// Create an array for the placement of boots on towns 
		// to make sure boots do not overlap
		Point[] bootPlacement = 
			   {new Point(-20, -15),
				new Point(20, -15),
				new Point(-30, 0),
				new Point(5, 0),
				new Point(-20, 15),
				new Point(20, 15)};
		int i = 0;
		
		drawPhaseInfo();
		drawPlayerPoints();
		drawTownInfo();	
		drawPlayerCards();
		drawPlayerCounters();
		drawRoundNumber();
		drawFaceUpCounters();
		drawRoadTokens();
		drawSaveButton();
		drawAdditional();
		
		
		//TODO: Draw the starting player card
		
		if (game.getVariant() == GameVariant.TOWNCARDS && player.getDestination() != null)
		{
			drawPlayerTownCard();
		}
		
		
		for (Player p: game.getParticipants())
		{
			drawBoot(bootPlacement, i, p);
			
			if (p != player)
			{
				if (p != playerEnlargedInfo)
				{
					drawInfoBox(p);
				}
				
			}	
			
			i++;

		}
		
		if (playerEnlargedInfo != null)
		{
			drawInfoBox(playerEnlargedInfo);
		}

		
		
		window.render();
		Thread.yield();
	}


	private void drawSaveButton() {
		window.draw(ImageLoader.get("Save-Game-Button.png"), 0, 0);
		
	}

	void drawAdditional() {}
	
	void drawPhaseInfo() {
		if(game.getCurrentPhase() != null && game.getCurrentPlayer() != null) {
			MinuetoFont phaseFont = new MinuetoFont("Serif", 12, false, false); 
			MinuetoImage phaseText = new MinuetoText(game.getCurrentPhase().getDescription()+", "+game.getCurrentPlayer().getName()+"'s turn", phaseFont, MinuetoColor.WHITE);
			window.draw(phaseText, 10, 0);
		}
	}

	
	
	void createInfoBoxPolygons()
	{
		int i = 0;
		MinuetoImage infoBox = ImageLoader.get("Player-Info-Box-Small.png");
		for (Player p: game.getParticipants())
		{
			if (p != player)
			{
				int height = infoBox.getHeight();
				int x = 0;
				int y = 15 + (height * i);
				infoBoxPolygons.put(p, createPolygon(x, y, infoBox.getWidth(), height));
				i++;
			}
		}
	}
	

	void drawInfoBox(Player player) {
		
		MinuetoImage refSmallBox = ImageLoader.get("Player-Info-Box-Small.png");; 	
		MinuetoImage infoBox = refSmallBox;
		if(playerEnlargedInfo != null)
		{
			System.out.println("not null!");
			if(player.getName().equals(playerEnlargedInfo.getName()))
			{
				System.out.println("drawbig!");
				infoBox = ImageLoader.get("Player-Info-Box-Big.png");
			}
			
		}

			
		Polygon p = infoBoxPolygons.get(player);
		int infobox_x = p.xpoints[0];
		int infobox_y = p.ypoints[0];
		int infobox_h = refSmallBox.getHeight();
		window.draw(infoBox, infobox_x, infobox_y);

		
		// Name
		MinuetoFont nameFont = new MinuetoFont("Serif", 18, false, false); 
		MinuetoImage nameText = new MinuetoText(player.getName(), nameFont, MinuetoColor.WHITE);
		window.draw(nameText, 10, -3 + infobox_y + infobox_h/4);

		// Number of points, cards and counters
		MinuetoFont infoFont = new MinuetoFont("Serif", 14, false, false); 
		MinuetoImage pointsText = new MinuetoText("x" + player.getPoints(), infoFont, MinuetoColor.WHITE);
		MinuetoImage cardsText = new MinuetoText("x" + player.getNumCards(), infoFont, MinuetoColor.WHITE);
		MinuetoImage countersText = new MinuetoText("x" +player.getNumCounters(), infoFont, MinuetoColor.WHITE);

		int y_pos = infobox_y + infobox_h/4;
		window.draw(countersText, 170, y_pos);
		window.draw(cardsText, 220, y_pos);
		
		// Draw coloured square and number of points
		if(player.hasBoot()) {
			MinuetoImage colourSquare = new MinuetoRectangle(13,13,colorConverter.get(player.getBoot()),true);
			window.draw(colourSquare, 85, infobox_y + 18);
			window.draw(pointsText, 100, y_pos);
		}
		
		// draw visible counters
		if(playerEnlargedInfo != null)
		{
		if (player.getName().equals(playerEnlargedInfo.getName())) {
			
			
			// Create polygons for counters in hand
			int[] rows = new int[] {33, 66, 99, 132, 165, 198};
			int[] cols = new int[] {48+infobox_y, 85+infobox_y};
			int max = playerEnlargedInfo.getCounters().size();
			int i = 0;
			for (int col: cols)
			{
				for (int row: rows)
				{
					if (i < max)
					{
						Counter c = playerEnlargedInfo.getCounters().get(i);
						if (c.isVisible())
						{
							window.draw(ImageLoader.getCounter(playerEnlargedInfo.getCounters().get(i)).scale(0.17, 0.17), row, col);
						}
						else
						{
							window.draw(ImageLoader.getFaceDownCounter().scale(0.17, 0.17), row, col);;
						}
					}
					
					else {return;}
					i ++;
				}

			}	
         
		}}	
	}

	
	

	private void drawBoot(Point[] bootPlacement, int i, Player p) {
		Point bootPoint = townCoordinates.get(p.getLocation()); 
		
		if (p.hasBoot()) 
		{
			MinuetoImage boot = ImageLoader.getBoot(p.getBoot()).scale(0.1, 0.1);
			int h = boot.getHeight();
			window.draw(boot, bootPoint.x + bootPlacement[i].x, bootPoint.y + bootPlacement[i].y - h);
		
		}
	}


	private void drawPlayerTownCard() {
		MinuetoImage cImg = ImageLoader.getTownCard(player.getDestination()).scale(0.95,0.95);
		window.draw(cImg, 65,20);
	}


	private void drawRoadTokens() {
		for(Road r: roadPolygons.keySet() )
		{	
			
//			for (Polygon p:roadPolygons.get(r))
//			{
//				MinuetoImage roadcounter = ImageLoader.getCounter(new TravelCounter(TransportationKind.UNICORN, true));
//				ScalingFactor scale = new ScalingFactor(roadcounter.getWidth(), roadcounter.getHeight(), p);
//				roadcounter = roadcounter.scale(scale.factorX, scale.factorY);
//				window.draw(roadcounter, p.xpoints[0],p.ypoints[0]);
//			}
			
			// check if there no counter on that road
			if (r.getNumCounters() > 0)
			{
				ArrayList<Counter> counters = r.getCounters();
				
				for(int i=0; i<counters.size(); i++)
				{
					MinuetoImage roadcounter = ImageLoader.getCounter(counters.get(i));
					ScalingFactor scale = new ScalingFactor(roadcounter.getWidth(), roadcounter.getHeight(), roadPolygons.get(r).get(i));
					roadcounter = roadcounter.scale(scale.factorX, scale.factorY);
					window.draw(roadcounter, roadPolygons.get(r).get(i).xpoints[0], roadPolygons.get(r).get(i).ypoints[0]);
				}

				
			}

		}
	}


	private void drawFaceUpCounters() {
		for (int i1 = 0; i1 <  game.getFaceUpCounters().size(); i1++)
		{
			MinuetoImage cImg = ImageLoader.getCounter(game.getFaceUpCounters().get(i1));
			Polygon p = faceUpCounterPolygons.get(i1);
			ScalingFactor scale = new ScalingFactor(cImg.getWidth(), cImg.getHeight(), p);
			cImg = cImg.scale(scale.factorX, scale.factorY);
			window.draw(cImg, p.xpoints[0], p.ypoints[0]);
		}
	}


	private void drawRoundNumber() {
		if (game.getRound() > 0 && game.getRound() < 5)
		{
			MinuetoImage roundCard = ImageLoader.getRound(game.getRound());
			window.draw(roundCard, 0, 0);
		}
	}


	private void drawPlayerCounters() {
		for (int i1 = 0; i1 <  player.getNumCounters() ; i1++)
		{
			Polygon p = countersInHandPolygons.get(i1);
			MinuetoImage conterImg = ImageLoader.getCounter(player.getCounters().get(i1));
			ScalingFactor scale = new ScalingFactor(conterImg.getWidth(), conterImg.getHeight(), p);
			conterImg = conterImg.scale(scale.factorX, scale.factorY);
			window.draw(conterImg, p.xpoints[0], p.ypoints[0]);
		}
	}


	private void drawPlayerCards() {
		for (int i1 = 0; i1 <  player.getNumCards() ; i1++)
		{
			MinuetoImage cardImg = ImageLoader.getCard(player.getCards().get(i1));
			Polygon p = cardsInHandPolygons.get(i1);
			ScalingFactor scale = new ScalingFactor(cardImg.getWidth(), cardImg.getHeight(), p);
			cardImg = cardImg.scale(scale.factorX, scale.factorY);
			window.draw(cardImg, p.xpoints[0], p.ypoints[0]);
		}
	}


	/**
	 * Draws info in the white boxes next to each town. 
	 * In elfenland, this corresponds to the town tokens not amassed yet.
	 */
	void drawTownInfo() {
		for (TownName t : TownName.values()) {
			if (t != TownName.ELVENHOLD) {
				int drawTP = 0;
				for (Player p : game.getParticipants()) {
					if(p.hasBoot()) {
						if(!p.getVisited().contains(t)) {
							MinuetoImage townpieceIMG = new MinuetoRectangle(8,8,colorConverter.get(p.getBoot()),true);
							window.draw(townpieceIMG, (int) townpieceCoordinates.get(t).getX() + drawTP, (int) townpieceCoordinates.get(t).getY());
							
						}
						drawTP += 11;
					}
				}
			}
			
		}
	}


	private void drawPlayerPoints() {
		if (player.hasBoot())
		{
			MinuetoFont serifFont = new MinuetoFont("Serif", 20, false, false); 
			MinuetoImage pointsText = new MinuetoText("x" + player.getPoints(), serifFont, MinuetoColor.WHITE);
			window.draw(pointsText, 1208, 650);
			MinuetoImage townPiece = ImageLoader.getTownPiece(player.getBoot());
			window.draw(townPiece, 0, 0);
		}
	}
		
	
	@Override
	public void run()
	{
		window.setVisible(true);
		
		drawBackground();
				
	}
	
	
	
}
