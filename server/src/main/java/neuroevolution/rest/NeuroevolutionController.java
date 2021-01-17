package neuroevolution.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/neuroevolution")
public class NeuroevolutionController {

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		log.info("test");
		return ResponseEntity.ok("test");
	}

}
