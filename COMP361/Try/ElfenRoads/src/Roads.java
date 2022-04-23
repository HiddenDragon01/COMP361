import java.util.ArrayList;
import java.awt.Polygon;

enum RoadType{ //not complete
	RIVER,
	FOREST,
	MOUNTAIN,
	GRASS,
	DESERT
}

enum TransportationCounter{ //not complete
	NONE,
	BATEAU,
	DRAGON,
	CHARIOT
}

/*
 * At the beginning of the game we can initialize all the predetermined roads
 */
public class Roads {
	private String town1; //towns should be added as a class at some point
	private String town2;
	private int[] points; //this attribute just helps draw it - can be taken off if we get to a point we don't need to visualize it
	private int[] xy;
	private Polygon shape;
	private TransportationCounter counter;
	private RoadType type;
	
	static private Boolean initialized = false;
	static private ArrayList<Roads> roadsList = new ArrayList<>();
	
	/*Parameter pPoints is only so I can draw them in Minueto (takes the points in a different format than the java polygon class)
	 * 	That's just to verify 
	 * 	So they can be removed in the actual implementation
	 * 
	 * 
	 */
	private Roads(String pTown1, String pTown2, int[] pPoints, int[] pXy, Polygon pShape) {
		town1 = pTown1;
		town2 = pTown2;
		points = pPoints;
		xy = pXy;
		shape = pShape;
	}
	
	public static void initializeRoads() {
		if (initialized) {
			return;
		}
		else {
			//initialize all roads; this will be kind of long cause there are a shit load of roads. could do something to make it neater but idk what
			//these are two for examples - should probably make these shapes a little bigger though
			int[] ints1 = {0, 50, 0, 65, 50, 65, 75, 30, 125, 30, 125, 10, 75, 10, 50, 50}; //the points are only necessary so i can draw them in minueto
			int[] xy1 = {795, 330}; //starting point for the drawing
			int[] xPoints1 = {795, 795, 845, 870, 920, 920, 870, 845}; //x values plus x starting point
			int[] yPoints1 = {380, 395, 395, 360, 360, 340, 340, 380}; //y values plus y starting point
			int numPoints1 = 8;
			Polygon shape1 = new Polygon(xPoints1, yPoints1, numPoints1);
			
			Roads road1 = new Roads("main town","second town", ints1, xy1, shape1);
			roadsList.add(road1);
			
			int[] ints2 = {0, 120, 0, 140, 90, 20, 90, 0}; //points for minueto drawing
			int[] xy2 = {850, 355}; //starting point for drawing
			int[] xPoints2 = {850, 850, 940, 940}; // x values plus starting x
			int[] yPoints2 = {475, 495, 375, 355}; //y values plus starting y
			int numPoints2 = 4;
			Polygon shape2 = new Polygon(xPoints2, yPoints2, numPoints2);
			Roads road2 = new Roads("main town","third town", ints2, xy2, shape2);
			roadsList.add(road2);
			initialized = true;
			return;
		}
	}
	
	public Polygon getShape() {
		return shape;
	}
	
	public String getTown1() {
		return town1;
	}
	
	public String getTown2() {
		return town2;
	}
	
	public static int[] getPoints(int index) {
		return roadsList.get(index).points;
	}
	
	/*
	 * if on a road and if boot is in one of the towns it connects to (can add more conditions later like road and counter)
	 * can also consider a more effective way to search roads, and maybe once finding the road we can pass another function where the road will check the move itself
	 */
	public static Boolean travel(int mouseX, int mouseY, Boot pBoot) {
		for (Roads road : roadsList) {
			if (road.getShape().contains(mouseX, mouseY)) {
				if (pBoot.getTown().equals(road.getTown1())) {
					return true;
				}
				if (pBoot.getTown().equals(road.getTown2())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
