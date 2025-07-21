package se.kau.cs.sy.services.board;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import se.kau.cs.sy.board.Board;
import se.kau.cs.sy.board.Link;
import se.kau.cs.sy.board.Location;
import se.kau.cs.sy.board.TransportType;
import se.kau.cs.sy.match.ClassicConfiguration;
import se.kau.cs.sy.transfer.board.SimpleBoardDTO;
import se.kau.cs.sy.transfer.match.MatchConfigurationDTO;

@RestController
public class BoardController {

	private Board board = Board.create();
	
	@GetMapping("/boards") 
	public Set<SimpleBoardDTO> getBoards() {
		Set<SimpleBoardDTO> result = new HashSet<SimpleBoardDTO>();
		result.add(new SimpleBoardDTO(board.getId(), board.getName()));
		return result;
	}
	
	@GetMapping("/board/details")
	public Board getBoardDetails() {
		return board;
	}
	
	@GetMapping("/links")
	public Set<Link> getLinks(
			@RequestParam(value = "node", defaultValue = "1") int nodeNumber,
			@RequestParam(value = "type", defaultValue= "") TransportType type) {
		
		if (type == null) return board.getLinks(nodeNumber);
		else return board.getLinks(nodeNumber, type);
	}
	
	@GetMapping("/neighbours")
	public Set<Integer> getNeighbours(
			@RequestParam(value = "node", defaultValue = "1") int nodeNumber,
			@RequestParam(value = "type", defaultValue = "") TransportType type) {
		
		if (type == null) return board.getNeighbourNodes(nodeNumber);
		else return board.getNeighbourNodes(nodeNumber, type);
	}
	
	@GetMapping("/location/{nodeNumber}")
	public Location getLocation(@PathVariable int nodeNumber) {
		return board.getLocation(nodeNumber);
	}
	
	@GetMapping(
		value="/map",
		produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getBoardImage() throws IOException {
		InputStream is = getClass().getResourceAsStream("/static/map.jpg");
		return is.readAllBytes();
	}
	
	@GetMapping("/config")
	public MatchConfigurationDTO getDefaultConfiguration() {
		return new MatchConfigurationDTO.Builder(new ClassicConfiguration()).build();
	}
}
