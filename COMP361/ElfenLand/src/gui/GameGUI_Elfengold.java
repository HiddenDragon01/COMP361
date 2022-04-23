package gui;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoWindow;

import gamelogic.Card;
import gamelogic.Counter;
import gamelogic.ElfengoldGame;
import gamelogic.GamePhase;
import gamelogic.Player;
import gamelogic.RegionKind;
import gamelogic.Road;
import gamelogic.TownName;
import gamelogic.TransportationKind;
import gamelogic.TravelCard;

public class GameGUI_Elfengold extends GameGUI {

	protected final ArrayList<Polygon> faceUpCardsPolygons = new ArrayList<>();
	protected final Polygon goldCardPilePolygon = createPolygon(1169, 343, 156, 93);
	protected final Polygon cardPile = createPolygon(1155, 86, 171, 95);
	
	public GameGUI_Elfengold(ElfengoldGame game, Player player) 
	{
		super(game, player);
		
		addToRoadPolygons(new int[] {970}, new int[] {325}, Road.get(RegionKind.RIVER, TownName.BEATA, TownName.ELVENHOLD));
		addToRoadPolygons(new int[] {827}, new int[] {379}, Road.get(RegionKind.LAKE, TownName.ELVENHOLD, TownName.VIRST));
		addToRoadPolygons(new int[] {876}, new int[] {377}, Road.get(RegionKind.LAKE, TownName.ELVENHOLD, TownName.STRYKHAVEN));
		addToRoadPolygons(new int[] {877}, new int[] {210}, Road.get(RegionKind.RIVER, TownName.ELVENHOLD, TownName.RIVINIA));
		addToRoadPolygons(new int[] {838}, new int[] {429}, Road.get(RegionKind.LAKE, TownName.STRYKHAVEN, TownName.VIRST));
		addToRoadPolygons(new int[] {876}, new int[] {153}, Road.get(RegionKind.RIVER, TownName.RIVINIA, TownName.TICHIH));
		addToRoadPolygons(new int[] {604}, new int[] {504}, Road.get(RegionKind.RIVER, TownName.VIRST, TownName.JXARA));
		addToRoadPolygons(new int[] {366}, new int[] {476}, Road.get(RegionKind.RIVER, TownName.JXARA, TownName.MAHDAVIKIA));
		addToRoadPolygons(new int[] {327}, new int[] {395},  Road.get(RegionKind.RIVER, TownName.MAHDAVIKIA, TownName.GRANGOR));
		addToRoadPolygons(new int[] {336}, new int[] {275}, Road.get(RegionKind.LAKE, TownName.GRANGOR, TownName.YTTAR));
		addToRoadPolygons(new int[] {377}, new int[] {252}, Road.get(RegionKind.LAKE, TownName.GRANGOR, TownName.PARUNDIA));
		addToRoadPolygons(new int[] {382}, new int[] {199}, Road.get(RegionKind.LAKE, TownName.YTTAR, TownName.PARUNDIA));
		addToRoadPolygons(new int[] {402}, new int[] {84}, Road.get(RegionKind.RIVER, TownName.WYLHIEN, TownName.USSELEN));

		// Create polygons for clickable squares on towns		
		addToRoadPolygons(new int[] {915, 985}, new int[] {314, 364}, Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.BEATA));
		addToRoadPolygons(new int[] {795, 814}, new int[] {331, 317}, Road.get(RegionKind.PLAINS, TownName.ELVENHOLD, TownName.LAPPHALIA));
		addToRoadPolygons(new int[] {981, 933}, new int[] {222, 254}, Road.get(RegionKind.WOODS, TownName.ELVENHOLD, TownName.ERGEREN));
		
		
		// beata
		addToRoadPolygons(new int[] {966, 942}, new int[] {440, 416}, Road.get(RegionKind.PLAINS, TownName.BEATA, TownName.STRYKHAVEN));
				
		// strykhaven
		addToRoadPolygons(new int[] {793, 886}, new int[] {503, 462}, Road.get(RegionKind.MOUNTAINS, TownName.STRYKHAVEN, TownName.VIRST));
		
		// ergeren
		addToRoadPolygons(new int[] {900, 951}, new int[] {146, 150}, Road.get(RegionKind.WOODS, TownName.ERGEREN, TownName.TICHIH));
		
		// tichih
		addToRoadPolygons(new int[] {773, 843}, new int[] {120, 113}, Road.get(RegionKind.PLAINS, TownName.TICHIH, TownName.THROTMANNI));
		addToRoadPolygons(new int[] {779, 822}, new int[] {77, 66}, Road.get(RegionKind.MOUNTAINS, TownName.TICHIH, TownName.JACCARANDA));
		
		// rivinia
		addToRoadPolygons(new int[] {752, 746}, new int[] {262, 240}, Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.LAPPHALIA));
		addToRoadPolygons(new int[] {791, 723}, new int[] {218, 206}, Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.FEODOR));
		addToRoadPolygons(new int[] {807, 805}, new int[] {153, 144}, Road.get(RegionKind.WOODS, TownName.RIVINIA, TownName.THROTMANNI));
		
		// virst
		addToRoadPolygons(new int[] {701, 724}, new int[] {410, 387}, Road.get(RegionKind.PLAINS, TownName.VIRST, TownName.LAPPHALIA));
		addToRoadPolygons(new int[] {580, 606}, new int[] {491, 482}, Road.get(RegionKind.PLAINS, TownName.VIRST, TownName.JXARA));
		
		// lapphalya
		addToRoadPolygons(new int[] {712, 707}, new int[] {277, 264}, Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.FEODOR));
		addToRoadPolygons(new int[] {575, 654}, new int[] {353, 365}, Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.DAGAMURA));
		addToRoadPolygons(new int[] {569, 591}, new int[] {412, 404}, Road.get(RegionKind.WOODS, TownName.LAPPHALIA, TownName.JXARA));
		
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
	
		// Create polygons for face up cards on the board
		int row = 200;
		int[] cols = new int[] {1079, 1182, 1285};
		int card_width = 91;
		int card_height = 133;
		for (int col: cols)
		{
			faceUpCardsPolygons.add(createPolygon(col, row, card_width, card_height));
		}
	}
	
	
	/**
	 * Draw player info box, which includes the num of tokens amassed, 
	 * the gold value, and the number of cards and tokens in hand
	 */
	@Override
	void drawInfoBox(Player p)
	{
		MinuetoImage refSmallBox = ImageLoader.get("Player-Info-Box-Small-ElfenGold.png");
		MinuetoImage infoBox = refSmallBox;
		if(playerEnlargedInfo != null)
		{
			if(p.getName().equals(playerEnlargedInfo.getName()))
			{
				infoBox = ImageLoader.get("Player-Info-Box-Big-ElfenGold.png");
			}
		}
		
		
		Polygon pol = infoBoxPolygons.get(p);
		int infobox_x = pol.xpoints[0];
		int infobox_y = pol.ypoints[0];
		int infobox_h = refSmallBox.getHeight();
		window.draw(infoBox, infobox_x, infobox_y);
		
		// Name
		MinuetoFont nameFont = new MinuetoFont("Serif", 18, false, false); 
		MinuetoImage nameText = new MinuetoText(p.getName(), nameFont, MinuetoColor.WHITE);
		window.draw(nameText, 10, -3 + infobox_y + infobox_h/4);
		
		// Number of points, cards and counters
		MinuetoFont infoFont = new MinuetoFont("Serif", 10, false, false); 
		MinuetoImage pointsText = new MinuetoText("x" + p.getPoints(), infoFont, MinuetoColor.WHITE);
		MinuetoImage cardsText = new MinuetoText("x" + p.getNumCards(), infoFont, MinuetoColor.WHITE);
		MinuetoImage countersText = new MinuetoText("x" +p.getNumCounters(), infoFont, MinuetoColor.WHITE);
		MinuetoImage goldText = new MinuetoText("x" +p.getGoldCoins(), infoFont, MinuetoColor.WHITE);

		int y_pos = 3 + infobox_y + infobox_h/4;
		window.draw(countersText, 145, y_pos);
		window.draw(cardsText, 178, y_pos);
		window.draw(goldText, 213, y_pos);
		
		// Draw coloured square and number of points
		if(p.hasBoot()) {
			MinuetoImage colourSquare = new MinuetoRectangle(11,11,colorConverter.get(p.getBoot()),true);
			window.draw(colourSquare, 95, infobox_y + 16);
			window.draw(pointsText, 107, y_pos);
		}
		
		
		// draw visible counters
		if(playerEnlargedInfo != null)
		{
		if (p.getName().equals(playerEnlargedInfo.getName())) {

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

			}}}
		
	}
	
	@Override
	void createInfoBoxPolygons()
	{
		int i = 0;
		MinuetoImage infoBox = ImageLoader.get("Player-Info-Box-Small-ElfenGold.png");
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
	
	@Override
	void drawAdditional() 
	{
		
		drawFaceUpCards();
		
		drawGoldPile();
		
		drawPlayerGold();
		
		if (game.getCurrentPhase() == GamePhase.AUCTION)
		{
			drawAuctionCounters();
		}
		
	}


	private void drawAuctionCounters() {
		
		for (int i1 = 0; i1 < ((ElfengoldGame) game).getAuctionPile().size(); i1++)
		{
			MinuetoImage cImg = ImageLoader.getCounter(((ElfengoldGame) game).getAuctionPile().get(i1));
			Polygon p = faceUpCounterPolygons.get(i1);
			ScalingFactor scale = new ScalingFactor(cImg.getWidth(), cImg.getHeight(), p);
			cImg = cImg.scale(scale.factorX, scale.factorY);
			window.draw(cImg, p.xpoints[0], p.ypoints[0]);
		}
		
	}


	private void drawGoldPile() {
		assert game instanceof ElfengoldGame;
		
		int numGoldCards = ((ElfengoldGame) game).getGoldCardPile();
		
		if (numGoldCards > 0)
		{
			MinuetoImage goldpileImg = ImageLoader.getGoldCard(numGoldCards);
			window.draw(goldpileImg, 0, 0);
			
			// Draw the number of gold cards in the pile
			MinuetoFont serifFont = new MinuetoFont("Serif", 15, false, true); 
			MinuetoImage numGoldCardsText = new MinuetoText("x" + numGoldCards, serifFont, MinuetoColor.WHITE);
			window.draw(numGoldCardsText, 1332, 382);
			
			
		}
	}


	private void drawFaceUpCards() {
		assert game instanceof ElfengoldGame;
		ArrayList<Card> faceUpCards = ((ElfengoldGame) game).getFaceUpCards();
		for (int i=0; i<faceUpCards.size(); i++)
		{
			MinuetoImage cardImg = ImageLoader.getCard(faceUpCards.get(i));
			Polygon p = faceUpCardsPolygons.get(i);
			ScalingFactor scale = new ScalingFactor(cardImg.getWidth(), cardImg.getHeight(), p);
			cardImg = cardImg.scale(scale.factorX, scale.factorY);
			window.draw(cardImg, p.xpoints[0], p.ypoints[0]);
		
		
		
		
		}
		
	}


	private void drawPlayerGold() {
		
		if (player.hasBoot())
		{
			MinuetoFont serifFont = new MinuetoFont("Serif", 20, false, false); 
			MinuetoImage pointsText = new MinuetoText("x" + player.getGoldCoins(), serifFont, MinuetoColor.WHITE);
			window.draw(pointsText, 1335, 650);
			MinuetoImage goldpiecesImg = ImageLoader.get("Gold-Pieces.png");
			window.draw(goldpiecesImg, 0, 0);
		}
	}
	
	/**
	 * Draw town tokens not amassed yet, as well as the gold value of the town.
	 * in the white rectangle boxes.
	 */
	@Override
	void drawTownInfo() {
		
		for (TownName t : TownName.values()) {
			int drawTP = 0;
			
			if (t != TownName.ELVENHOLD) {
				
				for (Player p : game.getParticipants()) {
					if(p.hasBoot()) {
						if(!p.getVisited().contains(t)) {
							MinuetoImage townpieceIMG = new MinuetoRectangle(8,8,colorConverter.get(p.getBoot()),true);
							window.draw(townpieceIMG, (int) townpieceCoordinates.get(t).getX() + drawTP, (int) townpieceCoordinates.get(t).getY());
							
						}
						drawTP += 11;
					}
				}
				
				// draw town gold value
				if (t.getGoldValue() > 0 && t.getGoldValue() < 8)
				{
					MinuetoImage towngoldIMG = ImageLoader.getGoldValueToken(t.getGoldValue());
					ScalingFactor scale = new ScalingFactor(towngoldIMG.getWidth(), towngoldIMG.getWidth(), 12, 12);
					towngoldIMG = towngoldIMG.scale(scale.factorX, scale.factorY);
					window.draw(towngoldIMG, (int) townpieceCoordinates.get(t).getX() + drawTP + 5, (int) townpieceCoordinates.get(t).getY() - 2);
				}
				
			}
			
			
			
		}
	}
	
	

}
