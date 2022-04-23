import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.awt.Rectangle;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;
import org.minueto.image.*; 

public class Town {
	private final ArrayList<Town> adjacentTowns = new ArrayList<>();
	private final String displayableName;
	private final Rectangle area;
//	private boolean visited = false;
	private int blue_townpieceX;
	private int blue_townpieceY;
	private MinuetoImage blue_townpiece;
	private int red_townpieceX;
	private int red_townpieceY;
	private MinuetoImage red_townpiece;
	
	static private Boolean initialized = false;
	//static private ArrayList<Town> TOWNS = new ArrayList<>();
	static private final Map<TownName, Town> TOWNS = new HashMap<>();
	
	private Town(String pDisplayableName, Rectangle pArea, int blue_pX, int blue_pY, MinuetoImage blue_pImage, int red_pX, int red_pY, MinuetoImage red_pImage) {
		
		displayableName = pDisplayableName;
		area = pArea;
		blue_townpieceX = blue_pX;
		blue_townpieceY = blue_pY;
		blue_townpiece = blue_pImage;
		red_townpieceX = red_pX;
		red_townpieceY = red_pY;
		red_townpiece = red_pImage;
	}
	
	public static void initializeTowns() {
		if (initialized) {
			return;
		}
		else {
			
			 MinuetoImage blue_token = new MinuetoRectangle(15,15,MinuetoColor.BLUE,true);
			 MinuetoImage red_token = new MinuetoRectangle(15,15,MinuetoColor.RED,true);

//			backGroundPoint = backGroundPoint.scale(0.2,0.2);
			
			Town elvenhold = new Town("Elvenhold", new Rectangle(845, 245, 75, 95), 845, 245, blue_token, 886, 245, red_token);
			TOWNS.put(TownName.ELVENHOLD, elvenhold);
			
			Town lapphalya = new Town("Lapphalya", new Rectangle(670, 350, 60, 40), 670, 350, blue_token, 687, 350, red_token);
			TOWNS.put(TownName.LAPPHALYA,lapphalya);
			
			Town dagamura = new Town("Dag'Amura", new Rectangle(535, 310, 50, 40), 535, 310, blue_token, 552, 310, red_token);
			TOWNS.put(TownName.DAGAMURA, dagamura);

			Town feodor = new Town("Feodor", new Rectangle(676, 230, 55, 45),676,230,blue_token, 693,230, red_token);
			TOWNS.put(TownName.FEODOR, feodor);
			
			Town virst = new Town("Virst", new Rectangle(738, 450, 50, 50),738,450,blue_token, 755,450, red_token);
			TOWNS.put(TownName.VIRST, virst);

			Town strykbaven = new Town("Strykbaven", new Rectangle(890, 410, 50, 50),890,410,blue_token, 909,410, red_token);
			TOWNS.put(TownName.STRYKHAVEN, strykbaven);

			Town beata = new Town("Beata", new Rectangle(980, 360, 50, 50),980,360,blue_token, 997,360, red_token);
			TOWNS.put(TownName.BEATA, beata);
			
			Town ergeren = new Town("Erg'Eren", new Rectangle( 965, 170, 50, 50),965,170,blue_token, 982,170, red_token);
			TOWNS.put(TownName.ERGEREN, ergeren);
			
			Town cicbib = new Town("Cicbib'", new Rectangle( 855, 65, 50, 50),855,65,blue_token, 872,65, red_token);
			TOWNS.put(TownName.CICBIB, cicbib);
			
			Town rivinia = new Town("Rivinia", new Rectangle( 810, 175, 50, 50),810,175,blue_token, 827,175, red_token);
			TOWNS.put(TownName.RIVINIA, rivinia);
			
			Town cbrotmanni = new Town("Cbrotmanni", new Rectangle( 715, 110, 50, 50),715,110,blue_token, 732,110, red_token);
			TOWNS.put(TownName.CBROTMANNI, cbrotmanni);
			
			Town jaccaranda = new Town("Jaccaranda", new Rectangle( 590, 50, 50, 50),590,50,blue_token, 607,50, red_token);
			TOWNS.put(TownName.JACCARANDA, jaccaranda);
			
			Town albaran = new Town("Albaran", new Rectangle( 540, 200, 50, 50),540, 200,blue_token,557, 200, red_token);
			TOWNS.put(TownName.ALBARAN, albaran);

			Town jxara = new Town("Jxara", new Rectangle( 525, 440, 50, 50),525, 440,blue_token, 542, 440, red_token);
			TOWNS.put(TownName.JXARA, jxara);
			
			Town dabdavikia = new Town("Dab Davikia", new Rectangle( 310, 430, 70,50),310, 430,blue_token, 327, 430, red_token);
			TOWNS.put(TownName.DABDAVIKIA, dabdavikia);
			
			Town grangor = new Town("Grangor", new Rectangle( 310, 325, 50,55),310, 325,blue_token, 327, 325, red_token);
			TOWNS.put(TownName.GRANGOR, grangor);
			
			Town kibromab = new Town("Kibromab", new Rectangle(420, 280, 50,50),420, 280,blue_token, 437, 280, red_token);
			TOWNS.put(TownName.KIBROMAB, kibromab);
			
			Town darundia = new Town("Darundia", new Rectangle(437, 145, 50,50),437, 145,blue_token, 454, 145, red_token);
			TOWNS.put(TownName.DARUNDIA, darundia);
			
			Town wylbien = new Town("Wylbien", new Rectangle(445, 20, 50,50),445, 20,blue_token, 462, 20, red_token);
			TOWNS.put(TownName.WYLHIEN, wylbien);
			
			Town usselen = new Town("Usselen", new Rectangle(315, 80, 50,50),315, 80,blue_token, 332, 80, red_token);
			TOWNS.put(TownName.USSELEN, usselen);
			
			Town yttar = new Town("Yttar", new Rectangle(295, 190, 50,50),295, 190,blue_token,312, 190, red_token);
			TOWNS.put(TownName.YTTAR, yttar);
			
			elvenhold.addAdjacentTown(lapphalya);
			elvenhold.addAdjacentTown(strykbaven);
			elvenhold.addAdjacentTown(beata);
			elvenhold.addAdjacentTown(virst);
			elvenhold.addAdjacentTown(rivinia);
			elvenhold.addAdjacentTown(ergeren);

			lapphalya.addAdjacentTown(elvenhold);
			lapphalya.addAdjacentTown(dagamura);
			lapphalya.addAdjacentTown(feodor);
			lapphalya.addAdjacentTown(virst);
			lapphalya.addAdjacentTown(rivinia);
			lapphalya.addAdjacentTown(jxara);


			dagamura.addAdjacentTown(lapphalya);
			dagamura.addAdjacentTown(feodor);
			dagamura.addAdjacentTown(albaran);
			dagamura.addAdjacentTown(jxara);
			dagamura.addAdjacentTown(dabdavikia);
			dagamura.addAdjacentTown(kibromab);
			
			kibromab.addAdjacentTown(dagamura);


			
			feodor.addAdjacentTown(lapphalya);
			feodor.addAdjacentTown(dagamura);
			feodor.addAdjacentTown(rivinia);
			feodor.addAdjacentTown(cbrotmanni);
			feodor.addAdjacentTown(albaran);


			virst.addAdjacentTown(lapphalya);
			virst.addAdjacentTown(strykbaven);
			virst.addAdjacentTown(elvenhold);
			virst.addAdjacentTown(jxara);

			strykbaven.addAdjacentTown(virst);
			strykbaven.addAdjacentTown(elvenhold);
			strykbaven.addAdjacentTown(beata);
			
			beata.addAdjacentTown(strykbaven);
			beata.addAdjacentTown(elvenhold);
			
			ergeren.addAdjacentTown(elvenhold);
			ergeren.addAdjacentTown(cicbib);
			
			cicbib.addAdjacentTown(ergeren);
			cicbib.addAdjacentTown(rivinia);
			cicbib.addAdjacentTown(cbrotmanni);
			cicbib.addAdjacentTown(jaccaranda);

			rivinia.addAdjacentTown(cicbib);
			rivinia.addAdjacentTown(elvenhold);
			rivinia.addAdjacentTown(lapphalya);
			rivinia.addAdjacentTown(feodor);
			rivinia.addAdjacentTown(cbrotmanni);
			
			cbrotmanni.addAdjacentTown(rivinia);
			cbrotmanni.addAdjacentTown(feodor);
			cbrotmanni.addAdjacentTown(cicbib);
			cbrotmanni.addAdjacentTown(jaccaranda);
			cbrotmanni.addAdjacentTown(albaran);

			jaccaranda.addAdjacentTown(cbrotmanni);
			jaccaranda.addAdjacentTown(cicbib);
			jaccaranda.addAdjacentTown(wylbien);


			albaran.addAdjacentTown(cbrotmanni);
			albaran.addAdjacentTown(dagamura);
			albaran.addAdjacentTown(feodor);
			albaran.addAdjacentTown(darundia);
			albaran.addAdjacentTown(wylbien);

			
			jxara.addAdjacentTown(lapphalya);
			jxara.addAdjacentTown(dagamura);
			jxara.addAdjacentTown(virst);
			jxara.addAdjacentTown(dabdavikia);

			dabdavikia.addAdjacentTown(jxara);
			dabdavikia.addAdjacentTown(dagamura);
			dabdavikia.addAdjacentTown(grangor);
			
			grangor.addAdjacentTown(dabdavikia);
			grangor.addAdjacentTown(darundia);
			grangor.addAdjacentTown(yttar);

			
			darundia.addAdjacentTown(grangor);
			darundia.addAdjacentTown(albaran);
			darundia.addAdjacentTown(wylbien);
			darundia.addAdjacentTown(usselen);
			darundia.addAdjacentTown(yttar);


			wylbien.addAdjacentTown(darundia);
			wylbien.addAdjacentTown(albaran);
			wylbien.addAdjacentTown(jaccaranda);
			wylbien.addAdjacentTown(usselen);

			usselen.addAdjacentTown(wylbien);
			usselen.addAdjacentTown(darundia);
			usselen.addAdjacentTown(yttar);

			yttar.addAdjacentTown(usselen);
			yttar.addAdjacentTown(darundia);
			yttar.addAdjacentTown(grangor);

			
			
			return;
			
		}
	}
	
//	public boolean isVisited() {
//		return visited;
//	}
//	
//	public void setVisited() {
//		visited = true;
//	}
	
	public MinuetoImage getBlueTownpiece() {
		return blue_townpiece;
	}
	
	public int getBlueTownpieceX() {
		return blue_townpieceX;
	}
	
	public int getBlueTownpieceY() {
		return blue_townpieceY;
	}
	
	public MinuetoImage getRedTownpiece() {
		return red_townpiece;
	}
	
	public int getRedTownpieceX() {
		return red_townpieceX;
	}
	
	public int getRedTownpieceY() {
		return red_townpieceY;
	}
	
	public Rectangle getArea() {
		return area;
	}
	
	public void addAdjacentTown(Town pTown) {
		adjacentTowns.add(pTown);
	}
	
	public ArrayList<Town> getAdjacentTowns() { 
		return adjacentTowns;
	}
	
	public static Map<TownName, Town> getTownsMap() {
		return (Map<TownName, Town>) Collections.unmodifiableMap(TOWNS) ; 
	}
	
	public static Optional<TownName> getDestinationTown(int mouseX, int mouseY, TownName currentTownName)
	{
		Town currentTown = TOWNS.get(currentTownName);
		
		for (TownName townName: TOWNS.keySet())
		{
			Town t = TOWNS.get(townName);
			if(t.getArea().contains(mouseX, mouseY))
			{
				if(t.getAdjacentTowns().contains(currentTown))
				{
					return Optional.of(townName);
				}
			}
		}
		return Optional.empty();
	}
	
//	public static Boolean travel(int mouseX, int mouseY, Boot pBoot) {
//		for (Town t : TOWNS) {
//			if (t.getArea().contains(mouseX, mouseY)) {
//				if (t.getAdjacentTowns().contains(pBoot.getTown())) {
//					pBoot.setTown(t);
//					if (!t.isVisited()) {
//						t.setVisited();
//						pBoot.increasePoints();
//					}
//					return true;
//				}
//				else {
//					return false;
//				}
//				
//			}
//		}
//		return false;
//	}
}
