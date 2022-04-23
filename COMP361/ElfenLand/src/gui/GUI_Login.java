package gui;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoKeyboard;
import org.minueto.handlers.MinuetoKeyboardHandler;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;
import org.minueto.window.MinuetoFrame;
import org.minueto.window.MinuetoWindow;


public class GUI_Login implements MinuetoKeyboardHandler, MinuetoMouseHandler {
	
	
	// Define variables
	private MinuetoWindow window;
	private String login;
	private String password_text;
	private Entry entry;
	private String real_pass;
	private boolean upper;
	private boolean loginError;
	private boolean toReturn;
	private String refreshToken;
	private MinuetoImage loginBackground;
	private MinuetoImage inputField;
	private MinuetoImage loginSubmit;
	
	
	private enum Entry {USERNAME, PASSWORD}
	
	public GUI_Login() {
		upper = false;
		loginError = false;
		refreshToken = "";
		window = new MinuetoFrame(1400, 700, true);
		login = "";
		password_text = "";
		entry = Entry.USERNAME;
		real_pass = "";
	}

	
    public String start() {
    	
    	
    	// Create window and register keyboard

        MinuetoEventQueue queue;
        queue = new MinuetoEventQueue();
        window.registerKeyboardHandler(this, queue);
        
        window.registerMouseHandler(this, queue);
        
        String imgDir = System.getProperty("user.dir") + "/GameAssets/";
        System.out.println(imgDir);
        
        try {
            loginBackground = new MinuetoImageFile(imgDir + "LoginBackground.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return refreshToken;
        }
        
        try {
            inputField = new MinuetoImageFile(imgDir + "LoginField.png");
        } catch(MinuetoFileException e) {
     
        	System.out.println("Could not load image file");
            return refreshToken;
        }
        
        try {
            loginSubmit = new MinuetoImageFile(imgDir + "LoginSubmit.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return refreshToken;
        }

        
        
        // Set up draw username, password, rectangle, submit button
        MinuetoFont fontArial14;
        MinuetoImage username;
        MinuetoImage password;
        MinuetoImage loginTitle;
        fontArial14 = new MinuetoFont("Arial",40,false, false); 
        MinuetoFont fontArial15 = new MinuetoFont("Arial",20,false, false); 
        username = new MinuetoText("Username: " ,fontArial14, MinuetoColor.BLACK); 
        password = new MinuetoText("Password: " ,fontArial14, MinuetoColor.BLACK); 
        loginTitle = new MinuetoText("LOGIN", fontArial14, MinuetoColor.BLACK);
        MinuetoImage submitBox = new MinuetoRectangle(200,60,MinuetoColor.BLUE,true);
        MinuetoImage submitText;
        submitText = new MinuetoText("Submit",fontArial14,MinuetoColor.WHITE);
        
        MinuetoImage loginErrorMessage = new MinuetoText("Username or password incorrect",fontArial15,MinuetoColor.RED);
        
        MinuetoImage outline = new MinuetoRectangle(850,600,MinuetoColor.BLACK,false);
        window.setVisible(true);
        
        window.draw(loginBackground, 0, 0);
        window.draw(inputField, 450, 280);
        window.draw(inputField, 450, 430);
        
        
        

        while(true) { 
        	
        	if (toReturn) {
        		return refreshToken;
        	}
        	
        	if (loginError) {
            	window.draw(loginErrorMessage, 460, 510);
            }
        	
        	// Draw shapes
        		
        	window.draw(loginTitle, 640, 140);
        	window.draw(username, 460, 230);
        	window.draw(password, 460, 380);
        	window.draw(loginSubmit, 620, 540);
        	window.draw(submitText, 648, 552);
	            
        	while(queue.hasNext()) {
	            	
	            // Draw username and password in the boxes
        		queue.handle();
        		MinuetoImage update_username;
        		update_username = new MinuetoText(String.valueOf(login) ,fontArial14,MinuetoColor.BLUE); 
        		window.draw(update_username, 460, 290);
        		MinuetoImage updated_password;
        		updated_password = new MinuetoText(String.valueOf(password_text) ,fontArial14,MinuetoColor.RED);
        		window.draw(updated_password, 460, 440);
        	}
	
        		window.render();
	            Thread.yield();
	            
        } 
        
    }
   
    
    public void handleKeyPress(int value) {
    	
    	loginError = false;
    	
    	
        switch(value) {
        	
        	case MinuetoKeyboard.KEY_ENTER:
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
					
					URL url = new URL(IPAddress.IP + "/oauth/token?grant_type=password&username=" + login + "&password=" + real_pass);
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
					loginError = true;
					return;
				}
				
				refreshToken1 = refreshToken1.replace("+", "%2B");
				
				
				refreshToken = refreshToken1;
			
				
				toReturn = true;
				break;
        	
        	
          	case MinuetoKeyboard.KEY_SHIFT:
          		
          		upper = true;
          		break;
            
            	
//            case MinuetoKeyboard.KEY_ENTER:
//            	// Done entering password (username?)
//            	passFlag = true;
//            	break;
            case MinuetoKeyboard.KEY_DELETE:
            	// Enable functionality to delete from username and password
            	window.clear();
            	
            	
            	window.draw(loginBackground, 0, 0);
            	window.draw(inputField, 450, 280);
                window.draw(inputField, 450, 430);
            	
            	
                if (entry == Entry.USERNAME && login.length() > 0) {
            		login = removeLast(login);
            	} 
            	if (entry == Entry.PASSWORD && password_text.length() > 0) {
            		password_text = removeLast(password_text);
            		real_pass = removeLast(real_pass);
            	}
            
            	break;
            default:
            	// If did not enter enter or delete, then add new letter 
            	if (login.length() < 10 &&  entry == Entry.USERNAME) {
            		if (upper) {
            			login = login.concat(Character.toString ((char)value));
            		} else {
            			login = login.concat(Character.toString ((char)value).toLowerCase());
            		}
            		
            	}
            	if (entry == Entry.PASSWORD && password_text.length() < 15) {
            		
            		if (upper) {
            			
            			if (value == 45) {
            				real_pass = real_pass.concat("_");
            			}
            			else { 
            				
            				real_pass = real_pass.concat(Character.toString((char) value));
            			
            			}
            			
            			
            		} else {
            			
            			real_pass = real_pass.concat(Character.toString((char) value).toLowerCase());
            			
            			
            		}
            		
            		password_text = password_text.concat("*");
            	}
       
            	break;
        }
    }

    public void handleKeyRelease(int value) {
    	
    	switch (value) {
    	
    		
    	
	    	case MinuetoKeyboard.KEY_SHIFT:
	  		
		  		upper = false;
		  		break;
		  		
    	}
	    
  
    }

    public void handleKeyType(char key) {
   // 	System.out.println("You just typed a key!");
    }
    
    public static String removeLast(String s) {
        return (s == null || s.length() == 0)
          ? null 
          : (s.substring(0, s.length() - 1));
    }



	public void handleMouseMove(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}



	public void handleMousePress(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public void close() {
		window.close();
	}
	
	
	public void handleMouseRelease(int x, int y, int button) {

		
		if (button == 1)
        {	
			
    		if ((x > 637) && (x < 785) && (y > 543) && (y < 611))
    		{	
    			
    			
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
    				
    				URL url = new URL(IPAddress.IP + "/oauth/token?grant_type=password&username=" + login + "&password=" + real_pass);
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
    				loginError = true;
    				return;
    			}
    			
    			refreshToken1 = refreshToken1.replace("+", "%2B");
    			
    			
    			refreshToken = refreshToken1;
    		
    			
    			toReturn = true;
    		
    			
   		    	
    		}
    		
    		if ((x > 450) && (x < 450 + inputField.getWidth()) && (y > 280) && (y < 280 + inputField.getHeight()))
    		{	
    			
    			entry = Entry.USERNAME;
   		    	
    		}
    		
    		if ((x > 450) && (x < 450 + inputField.getWidth()) && (y > 430) && (y < 430 + inputField.getHeight()))
    		{	
    			
    			entry = Entry.PASSWORD;
   		    	
    		}
        }
		
	}
}
