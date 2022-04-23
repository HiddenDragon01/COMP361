import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class localServer {
	public static void main (String[] args) throws IOException {
		
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(4444);
		} catch(IOException e) {
			System.out.println("Could not listen on port: 4444");
			System.exit(-1);
		}
		
		// accepting two clients, a placeholder for threading
		
		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
			System.out.println("client accepted");
		} catch(IOException e) {
			System.out.println("Accept failed: 4444");
			System.exit(-1);
		}
		
		Socket clientSocket2 = null;
		try {
			clientSocket2 = serverSocket.accept();
			System.out.println("client2 accepted");
		} catch(IOException e) {
			System.out.println("Accept failed: 4444");
			System.exit(-1);
		}
	
		
		InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		// reading data from clients
		
		String str = bf.readLine();
		int x = Integer.parseInt(str);
		str = bf.readLine();
		int y = Integer.parseInt(str);
		
		// outputting data to other clients
		PrintWriter out = new PrintWriter(clientSocket2.getOutputStream(),true);
		out.println(x);
		out.println(y);
		out.flush();
		
		
		
		serverSocket.close();
		clientSocket.close();
	}
}
