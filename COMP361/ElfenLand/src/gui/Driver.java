package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

import org.minueto.window.MinuetoWindowInvalidStateException;

import com.google.gson.Gson;

import gamelogic.BootColor;
import gamelogic.ElfengoldGame;
import gamelogic.Game;
import gamelogic.GameVariant;
import gamelogic.Player;
import gamelogic.TownName;


public class Driver {

	public Driver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		/*
		
		
		// Start up the Login Screen
		GUI_Login loginScreen = new GUI_Login();
		String refreshToken = "";
				
				
				
				
		// For some reason, closing a window gives an error. Therefore, just 
				// ignore the exception
		try {
					
			refreshToken = loginScreen.start();
			loginScreen.close();
				
		} catch(MinuetoWindowInvalidStateException e) {} 
		
		SavedGamesGUI gui = new SavedGamesGUI();
		gui.setRefreshToken(refreshToken);
		gui.start();
		*/
		
		Player p = new Player();
		p.setDestination(TownName.GRANGOR);
		Player p2 = new Player();
		p2.setBoot(BootColor.BLACK);
		Player p3 = new Player();
		p3.setBoot(BootColor.RED);
		p.setBoot(BootColor.BLUE);
		Player p4 = new Player();
		p4.setBoot(BootColor.YELLOW);
		p2.setName("Bea");
		p3.setName("Jifang");
		p4.setName("Alex");
		p.setLocation(TownName.BEATA);
		ElfengoldGame g = new ElfengoldGame(GameVariant.TOWNCARDS, "hello");
		g.addPlayer("11111", p);
		g.addPlayer("11", p2);
		g.addPlayer("111", p3);
		g.addPlayer("1111", p4);
		
		Gson gson = new Gson();
		
		String savedGame = gson.toJson(g);
//		System.out.println(savedGame);
//		PrintWriter writer = new PrintWriter("myjson.json", "UTF-8");
//		writer.println(savedGame);
//		writer.close();
		
		String text = new String(Files.readAllBytes(Paths.get("myjson.json")), StandardCharsets.UTF_8);
		System.out.println(text);

		ElfengoldGame g2 = gson.fromJson(text, ElfengoldGame.class);

		
		String x = "ryan";
		System.out.println(x);
	}

}
