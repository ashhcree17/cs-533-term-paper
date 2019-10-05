package abac.spring.data.neo4j.controller;

import abac.spring.data.neo4j.services.IndexingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class ObjectController {

	private final IndexingService indexingService;
	
	public ObjectController(IndexingService indexingService) {
		this.indexingService = indexingService;
	}

    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return indexingService.graph(limit == null ? 100 : limit);
	}

    @GetMapping("/index")
	public Map<String, Object> index(@RequestParam(value = "limit",required = false) Integer limit) {
		return indexingService.graph(limit == null ? 100 : limit);
	}
}
