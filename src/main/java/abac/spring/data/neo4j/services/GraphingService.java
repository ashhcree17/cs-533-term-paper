package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.ObjectAttribute;
import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.repositories.ObjectRepository;
import abac.spring.data.neo4j.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GraphingService {
    private final static Logger LOG = LoggerFactory.getLogger(GraphingService.class);

	private final ObjectRepository objectRepository;
	private final UserRepository userRepository;

	public GraphingService(ObjectRepository objectRepository, UserRepository userRepository) {
		this.objectRepository = objectRepository;
		this.userRepository = userRepository;
	}

	private Map<String, Object> toD3Format(Collection<ObjectNode> objectNodes) {
		List<Map<String, Object>> nodes = new ArrayList<>();
		List<Map<String, Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<ObjectNode> result = objectNodes.iterator();
		while (result.hasNext()) {
			ObjectNode objectNode = result.next();
			nodes.add(map("id", objectNode.getId(), "label", "objectNode"));
			int target = i;
			i++;
			for (ObjectAttribute objectAttribute : objectNode.getObjectAttributes()) {
				Map<String, Object> objectAttr = map("type", objectAttribute.getType(), "label", "objectAttribute");
				int source = nodes.indexOf(objectAttr);
				if (source == -1) {
					nodes.add(objectAttr);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
		}
		return map("nodes", nodes, "links", rels);
	}

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> graph(int limit) {
		Collection<ObjectNode> result = objectRepository.graph(limit);
		return toD3Format(result);
	}
}
