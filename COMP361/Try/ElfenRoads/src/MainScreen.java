import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;


import org.minueto.*; 
import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import restAPI.RestAPIController;
import restAPI.RestServiceApplication;

import org.minueto.handlers.*; 
import org.minueto.image.*; 
import org.minueto.window.*;



public class MainScreen {
	
	
	
	public static void main(String[] args) {
		
		
		/*
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(RestServiceApplication.class, args);
		
		*/
		
		/*
		
		LoginScreen loginScreen = new LoginScreen();
		String refreshToken = "";
		
		
		
		
		// For some reason, closing a window gives an error. Therefore, just 
		// ignore the exception
		try {
			
			refreshToken = loginScreen.start();
			loginScreen.close();
		
		}
	
		catch(MinuetoWindowInvalidStateException e) {} 
		
	
		
		 
		LobbyScreen ls = new LobbyScreen();
		boolean isCreator = false;
		
		// For some reason, closing a window gives an error. Therefore, just 
		// ignore the exception
		try {
			
			ls.setRefreshToken(refreshToken);
			isCreator = ls.start();
		
		}
	
		catch(MinuetoWindowInvalidStateException e) {} 
		
		*/
		
		
		// Create first player by setting isCreator = true and passing a 
		// name and color into game.start
		
		// Create second player by setting isCreator = false and passing a 
		// different name and color into game.start
		
		boolean isCreator = true;
		
		if (isCreator) {
			System.setProperty("java.awt.headless", "false");
			SpringApplication.run(RestServiceApplication.class, args);
		}
		
		

		
		
		// Main screen code goes here
		
		Game game = new Game();
	
		// "ryan" should be changed to "refreshToken", however, I am keeping it
		// here for simplicity
		
		// TODO: Find out some way of assigning color
		game.start(isCreator, "ryan", "red");
		
		
		
		
		
		
	}

}
