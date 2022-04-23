package gamelogic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Try {

	public Try() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {                 
			URL url = new URL("http://172.28.83.210:4242/api/gameservices/ElfRegular/savegames/WGIX0?access_token=ZmDZiFVvzNFTkHLYWUfdz7c/5OY=");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("content-type", "application/json");

			/* Payload support */
			con.setDoOutput(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("{\n");
			out.writeBytes("\"gamename\": \"ElfRegular\",\n");
			out.writeBytes("\"players\": [\n");
			out.writeBytes("    \"ryan\",\n");
			out.writeBytes("    \"shalee\"\n");
			out.writeBytes("],\n");
			out.writeBytes("\"savegameid\": \"WGIX0\"\n");
			out.writeBytes("}");
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
			
		} catch(Exception e) {e.printStackTrace();}

	}

}
