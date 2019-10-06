package abac.spring.data.neo4j.controller;

import abac.spring.data.neo4j.services.GraphingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class ObjectController {

	private final GraphingService graphingService;
	
	public ObjectController(GraphingService graphingService) {
		this.graphingService = graphingService;
	}

    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return graphingService.graph(limit == null ? 100 : limit);
	}

    @GetMapping("/index")
	public Map<String, Object> index() {
//		return indexingService.index();
		return null;
	}
}
