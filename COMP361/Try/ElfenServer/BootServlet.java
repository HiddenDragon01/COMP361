import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class BootServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String requestUrl = request.getRequestURI();
		String color = requestUrl.substring("/boot/".length());
		
		Person person = DataStore.getInstance().getPerson(name);
		
		if(person != null){
			String json = "{\n";
			json += "\"color\": " + JSONObject.quote(boot.getBootColor()) + ",\n";
			json += "\"x-coordinate\": " + boot.getX() + ",\n";
			json += "\"y-coordinate\": " + boot.getY() + "\n";
			json += "\"town\": " + JSONObject.quote(boot.getTown()) + "\n";
			json += "\"points\": " + boot.getPoints() + "\n";
			json += "}";
			response.getOutputStream().println(json);
		}
		else{
			//That person wasn't found, so return an empty JSON object. We could also return an error.
			response.getOutputStream().println("{}");
		}
	}
	
	

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String color = request.getParameter("color");
		int x_coordinate = Integer.parseInt(request.getParameter("x"));
		int y_coordinate = Integer.parseInt(request.getParameter("y"));
		String town = request.getParameter("town");
		String points = Integer.parseInt(getParameter("points"));
		
		BootStore.getInstance().putBoot(new Boot(color, x_coordinate, y_coordinate, town, points));
	}
}
