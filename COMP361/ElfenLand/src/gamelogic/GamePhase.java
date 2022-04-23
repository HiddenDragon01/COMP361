package gamelogic;

public enum GamePhase {
	DRAWTRAVELCARDSONE,
	DRAWTRAVELCARDSTWO,
	DRAWTRAVELCARDSTHREE,
	DRAWTOKENSANDCOUNTERS,
	DRAWFACEUPCOUNTERONE,
	DRAWFACEUPCOUNTERTWO,
	DRAWFACEUPCOUNTERTHREE,
	AUCTION,
	PLANTRAVEL,
	MOVEBOOT,
	CHOOSECARDSTOKEEP,
	DRAWCARDAFTERTRAVELONE,
	DRAWCARDAFTERTRAVELTWO,
	FINISHROUND;
	
	private String description;
	
	static {
		DRAWTRAVELCARDSONE.description = "Draw card 1";
		DRAWTRAVELCARDSTWO.description = "Draw card 2";
		DRAWTRAVELCARDSTHREE.description = "Draw card 3";
		DRAWTOKENSANDCOUNTERS.description = "Choose visible counter";
		DRAWFACEUPCOUNTERONE.description = "Draw counter";
		DRAWFACEUPCOUNTERTWO.description = "Draw counter";
		DRAWFACEUPCOUNTERTHREE.description = "Draw counter";
		AUCTION.description = "Auction";
		PLANTRAVEL.description = "Place counter";
		MOVEBOOT.description = "Travel";
		CHOOSECARDSTOKEEP.description = "Choose cards to keep";
		DRAWCARDAFTERTRAVELONE.description = "Draw card";
		DRAWCARDAFTERTRAVELTWO.description = "Draw card";
		FINISHROUND.description = "Choose counter to keep";
	}
	
	public String getDescription() {
		return description;
	}
}
