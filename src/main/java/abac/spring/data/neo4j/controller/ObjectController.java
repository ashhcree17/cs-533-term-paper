package abac.spring.data.neo4j.controller;

import java.util.Map;

import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.services.IndexingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ObjectController {

	private final IndexingService indexingService;
	
	public ObjectController(IndexingService indexingService) {
		this.indexingService = indexingService;
	}

    @GetMapping("/graph")
	public Map<String, ObjectNode> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return indexingService.graph(limit == null ? 100 : limit);
	}

    @GetMapping("/index")
	public Map<String, ObjectNode> index(@RequestParam(value = "limit",required = false) Integer limit) {
		return indexingService.graph(limit == null ? 100 : limit);
	}
}
