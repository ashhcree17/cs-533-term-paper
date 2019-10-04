package abac.spring.data.neo4j.controller;

import java.util.Map;

import abac.spring.data.neo4j.domain.Object;
import abac.spring.data.neo4j.services.ObjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ObjectController {

	private final ObjectService objectService;
	
	public ObjectController(ObjectService objectService) {
		this.objectService = objectService;
	}

    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return objectService.graph(limit == null ? 100 : limit);
	}

    @GetMapping("/index")
	public Map<String, Object> index(@RequestParam(value = "limit",required = false) Integer limit) {
		return objectService.graph(limit == null ? 100 : limit);
	}
}
