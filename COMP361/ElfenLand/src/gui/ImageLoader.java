package gui;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import gamelogic.BootColor;
import gamelogic.Card;
import gamelogic.Counter;
import gamelogic.TownName;
import gamelogic.TravelCard;
import gamelogic.TravelCounter;

public class ImageLoader {
	
	private static String base_dir = System.getProperty("user.dir") + "/GameAssets/";
	
	public ImageLoader(String base_dir) { ImageLoader.base_dir = base_dir; }
	public ImageLoader() {}
		
	private static MinuetoImageFile templateLoad(String fileName) 
	{
//		System.out.println(fileName);
//		System.out.println(base_dir + fileName);
		try {
            return new MinuetoImageFile(base_dir + fileName);
        } catch(MinuetoFileException e) {
            e.printStackTrace();
            return null;
          
        }
	}
	
	/**
	 * @param fileName : name of the file to load, including the image type (e.g.background.jpg)
	 * @return MinuetoImageFile for the input file name
	 */
	public static MinuetoImageFile get(String fileName)
	{
		return templateLoad(fileName);
	}
	
	/**
	 * @param c: color of the boot image to load
	 * @return MinuetoImageFile for the boot of color c
	 */
	public static MinuetoImageFile getBoot(BootColor c)
	{
		return templateLoad("Boots/boot-" + c.toString().toLowerCase() + ".png");
	}
	
	public static MinuetoImageFile getTakenBoot(BootColor c)
	{
		return templateLoad("Boots/boot-" + c.toString().toLowerCase() + "-taken.png");
	}
	public static MinuetoImage getRound(int round) {
		return templateLoad("Round/Round-" + round + ".png");
	}
	
	public static MinuetoImageFile getTownPiece(BootColor c)
	{
		return templateLoad("TownPieces/townpiece-" + c.toString().toLowerCase() + ".png");
	}
	
	public static MinuetoImageFile getCounter(Counter c)
	{
		
		if (c instanceof TravelCounter )
		{
			return templateLoad("Counters/" + ((TravelCounter) c).getTransportationKind().toString().toLowerCase() + ".png");
		}
		
		return templateLoad("Counters/" + c.getCounterKind().toString().toLowerCase() + ".png");

		
	}
	
	public static MinuetoImageFile getFaceDownCounter()
	{

		return templateLoad("Counters/facedown.png");

		
	}
	
	public static MinuetoImageFile getCard(Card c)
	{
		if (c instanceof TravelCard)
		{
			return templateLoad("Cards/card-" + ((TravelCard) c).getTransportationKind().toString().toLowerCase() + ".png");

		}
		
		else
		{
			return templateLoad("Cards/card-" + (c.getCardKind().toString().toLowerCase() + ".png"));

		}
	}
	
	
	public static MinuetoImageFile getSelectionBox(boolean isSelected)
	{
		String fileName = "Counters/";
		
		if (!isSelected){ fileName += "placecounter.png"; }
		else { fileName += "selected.png"; }
		
		return templateLoad(fileName);
	}
	
	public static MinuetoImageFile getTownCard(TownName t) {
		return templateLoad("TownCards/" + t.toString().toLowerCase() + ".png");
	}
	
	
	public static MinuetoImageFile getGoldValueToken(int value)
	{
		assert value > 1 && value < 8;
		return templateLoad("GoldValueTokens/" + value + ".png");
	}
	
	public static MinuetoImageFile getConfetti() {
		return templateLoad("confetti.png");
	}
	
	public static MinuetoImageFile getGoldCard(int numGoldCards){
		assert numGoldCards > 0;
		String fileName = "GoldCards/";
		if (numGoldCards == 1) { fileName += "GoldCardsx1.png"; }
		else { fileName += "GoldCardsx3.png"; }
		
		return templateLoad(fileName);
	}
	
}
