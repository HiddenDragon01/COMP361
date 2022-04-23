package gamelogic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.IPAddress;

public class deleteSessions {
	 
	public static void deleteSession(String sessionID, String token) {
		
		
		try {
			
			URL url = new URL(IPAddress.IP + "/api/sessions/" + sessionID + "?access_token=" + token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("DELETE");
			con.setRequestProperty("authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=,Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=,Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=");

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
			System.out.println(content.toString());
			
		} catch(Exception e) {};
	}
	
	
	public static String getToken() {
		
		
		String refreshToken1 = "";
		
		try {
			
			String authenUsername = "bgp-client-name";
			String authenPasswd = "bgp-client-pw";
			
			Authenticator.setDefault(new Authenticator() {

			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {          
			        return new PasswordAuthentication(authenUsername, authenPasswd.toCharArray());
			    }
			});
			
			URL url = new URL(IPAddress.IP + "/oauth/token?grant_type=password&username=" + "service" + "&password=" + "abc123_ABC123");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("user_oauth_approval=true&_csrf=19beb2db-3807-4dd5-9f64-6c733462281b&authorize=true");
			out.flush();
			out.close();

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
			System.out.println(content.toString());
			
			
			
			
			
			
			int count = 0;
			
			
			
			for (int i = 0; i < content.length(); i++) {
				
				if (content.charAt(i) == '"' ) {
					count++;
					continue;
				}
				
				
				
				if (count == 11) {
					refreshToken1 += content.charAt(i);
				}
				
			}
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} 
		
		refreshToken1 = refreshToken1.replace("+", "%2B");
		
		return refreshToken1;
	
		
	    	
	}
	
	 public static String refreshMyToken(String refreshToken) {
	    	
	    	String token = "";
	    	
	    	try {
	    		
	    		URL url = new URL(IPAddress.IP + "/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken);
	    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    		con.setRequestMethod("POST");

	    		/* Payload support */
	    		con.setDoOutput(true);
	    		DataOutputStream out = new DataOutputStream(con.getOutputStream());
	    		out.writeBytes("user_oauth_approval=true&_csrf=19beb2db-3807-4dd5-9f64-6c733462281b&authorize=true");
	    		out.flush();
	    		out.close();

	    		int status = con.getResponseCode();
	    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    		String inputLine;
	    		StringBuffer content = new StringBuffer();
	    		while((inputLine = in.readLine()) != null) {
	    			content.append(inputLine);
	    		}
	    		in.close();
	    		con.disconnect();
	    		
	    		JSONObject obj = new JSONObject(content.toString());
	    		token = obj.getString("access_token");
	    		token = token.replace("+", "%2B");
		    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    	
	    	return token;
	    	
	 }
	 
	 
	 public static void deleteAllSessions() {
		 
		 
	
			    
		        try {
		        	
		        	URL url = new URL(IPAddress.IP + "/api/sessions");
		        	HttpURLConnection con = (HttpURLConnection) url.openConnection();
		        	con.setRequestMethod("GET");
		        	con.setRequestProperty("cookie", "JSESSIONID=54B2765BECFB1BBFE5BE8B1442E45782");
		        	con.setRequestProperty("authorization", "Basic YmdwLWNsaWVudC1uYW1lOmJncC1jbGllbnQtcHc=");
		        	con.setRequestProperty("user-agent", "advanced-rest-client");
		        	con.setRequestProperty("accept", "*/*");

		        	int status = con.getResponseCode();
		        	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		        	String inputLine;
		        	StringBuffer content = new StringBuffer();
		        	while((inputLine = in.readLine()) != null) {
		        		content.append(inputLine);
		        	}
		        	in.close();
		        	con.disconnect();
		        	
		        	// Keep these lines. For some reason, without them, the program crashes.
		        	System.out.println("Response status: " + status);
		        	System.out.println(content.toString());
		        	
		        	
		        	JSONObject obj = new JSONObject(content.toString());
		        	Iterator<String> keys = obj.getJSONObject("sessions").keys();

		        	int sessionIndex = 0;
		        	
		        
		        	while (keys.hasNext()) {
		        		String sessionID = keys.next();

		                boolean launched = obj.getJSONObject("sessions").getJSONObject(sessionID).getBoolean("launched");
		                
		                if (launched) {
		                	deleteSession(sessionID, refreshMyToken(getToken()));
		                }
		            
		                
		        	}
		        	
		        	
		        	
		        } catch(Exception e) {e.printStackTrace();}
		    
		 
	 }
	
	public static void main(String[] args) { 
		
		deleteAllSessions();
		
		
	}

}
