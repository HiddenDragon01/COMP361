package restAPI;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RestAPIController {
	
	@Autowired
	private PostMappingService service;

	
	@GetMapping("/game")
	public List<RestAPIState> greeting() {
		return service.getAllPlayers();
	}
	
	@PostMapping("/addPlayer")
    public String addPlayerInfo(@RequestBody RestAPIState player) {
		
		
		return service.addPlayer(player);
		
		
    }
	
	@PutMapping("/updatePlayer") 
	public String updatePlayerInfo(@RequestBody RestAPIState player) {
			
		service.updatePlayer(player);
		return "Player updated successfully";
		
	}
	
	

}
