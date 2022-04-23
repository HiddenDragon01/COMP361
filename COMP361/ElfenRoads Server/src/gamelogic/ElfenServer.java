package gamelogic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ElfenServer extends Thread {
	
	private ArrayList<String> playerNames;
	
	public void addPlayer(String playerName) {
		playerNames.add(playerName);
	}
	
	public void run() {

		
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		ArrayList<Socket> clientSockets = new ArrayList<>();
		
		try {
			serverSocket = new ServerSocket(4444);
		} catch(IOException e) {
			System.out.println("Could not listen on port: 4444");
			System.exit(-1);
		}
		
		
		for (int i = 0; i < playerNames.size(); i++) {
			try {
				clientSocket = serverSocket.accept();
				System.out.println("client " + i + " accepted");
			} catch(IOException e) {
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
			clientSockets.add(clientSocket);
		}
		
		// TODO: Phase phase = phase 1
		//		sendPhaseToAllPlayers(phase)					
		//		out.print( â€œchoose a counterâ€?)	
		
		int i = 0;
		
		for (Socket client: clientSockets) {
			
			try {
				InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
				BufferedReader bf = new BufferedReader(in);
				
				PrintWriter out = new PrintWriter(client.getOutputStream(),true);
				out.println(playerNames.get(i++));
				
				// If name is not this player, then Client executes the 
				// UnitCommand which does nothing. Else, Client executes the command
				// based on the phase it is in
				
				
				// Problem: How do I read in a serialized Command if bf just
				// reads in strings? 
				String command = bf.readLine();
				
				// command.execute()
				
				
				
				

			
				// TODO: Notify all other players of the change and send phase to 
				// all players
				
				
				
				
			} catch(Exception e) {e.printStackTrace();}
			
		}
		
	}
	

	
	

}
