package gui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import org.json.JSONObject;

public class ServiceToken {
	
	
public static String getRefreshToken() {
	
	String refreshToken = "";
	
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
				refreshToken += content.charAt(i);
			}
			
		}
		
		
		
	} catch(Exception e) {
		return null;
	}
	
	return refreshToken;
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

	public ServiceToken() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String refreshToken = getRefreshToken();
		String token = refreshMyToken(refreshToken);
		System.out.println(token);

	}

}
