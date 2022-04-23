package restAPI;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostMappingService {
	

	private List<RestAPIState> players = new ArrayList<>();
	
	List<RestAPIState> getAllPlayers() {
		return players;
	}
	
	public String addPlayer(RestAPIState player) {
		players.add(player);
		return "Player added successfully";
	}
	
	public void updatePlayer(RestAPIState player) {
		
		String name = player.getName();
		
		for (int i = 0; i < players.size(); i++) {
			
			if (players.get(i).getName().equals(name)) {
				players.set(i, player);
				
				// It is the next player's turn in the queue
				if (i != (players.size() - 1)) {
					players.get(i + 1).changeTurn();
				} else {
					players.get(0).changeTurn();
				}
				
				
				break;
			}
			
			
		}
		
		
	}
	

}
