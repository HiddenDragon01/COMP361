import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONObject;

public class RestApiClient {

  public static String getPlayerData(Player player) throws IOException{
	  
//    //URL needs to be changed to whatever our url is i guess
//		HttpURLConnection connection = (HttpURLConnection) new URL("52.14.147.198:4242/boot/" + player.getName()).openConnection();
//		
//		connection.setRequestMethod("GET");
//
//		int responseCode = connection.getResponseCode();
//		if(responseCode == 200){
//			String response = "";
//			Scanner scanner = new Scanner(connection.getInputStream());
//			while(scanner.hasNextLine()){
//				response += scanner.nextLine();
//				response += "\n";
//			}
//			scanner.close();
//
//			return response;
//		}
//		
//		// an error happened
//		return null;
//		
//	}
//  
	  URL url = new URL("http://52.14.147.198:4242/player" + player.getName());
	  HttpURLConnection con = (HttpURLConnection) url.openConnection();
	  con.setRequestMethod("GET");

	  int status = con.getResponseCode();
	  BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	  String inputLine;
	  StringBuffer content = new StringBuffer();
	  while((inputLine = in.readLine()) != null) {
	  	content.append(inputLine);
	  }
	  in.close();
	  con.disconnect();
	  System.out.println("Response status: " + status);
	  return content.toString();
}

	public static void setPlayerData(Player player) throws IOException{

//		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/player/" + color).openConnection();
//
//		connection.setRequestMethod("POST");
//		
//		String colorName = color.name();
//		String postData = "color=" + URLEncoder.encode(colorName,java.nio.charset.StandardCharsets.UTF_8.toString());
//		postData += "&x=" + x;
//		postData += "&y=" + y;
//		postData += "town=" + URLEncoder.encode(pTown,java.nio.charset.StandardCharsets.UTF_8.toString());
//		postData += "&points=" + points;
//
//		connection.setDoOutput(true);
//	    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
//	    wr.write(postData);
//	    wr.flush();
//		
//		int responseCode = connection.getResponseCode();
//		if(responseCode == 200){
//			System.out.println("POST was successful.");
//		}
//		else if(responseCode == 401){
//			System.out.println("Bad. POST didn't work.");
//		}
		
		
	}
}
