package movies.spring.data.neo4j.services;

import java.util.*;

import movies.spring.data.neo4j.domain.Object;
import movies.spring.data.neo4j.domain.Role;
import movies.spring.data.neo4j.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    private final static Logger LOG = LoggerFactory.getLogger(MovieService.class);

	private final MovieRepository movieRepository;
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	private Map<String, java.lang.Object> toD3Format(Collection<Object> objects) {
		List<Map<String, java.lang.Object>> nodes = new ArrayList<>();
		List<Map<String, java.lang.Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<Object> result = objects.iterator();
		while (result.hasNext()) {
			Object object = result.next();
			nodes.add(map("title", object.getTitle(), "label", "object"));
			int target = i;
			i++;
			for (Role role : object.getRoles()) {
				Map<String, java.lang.Object> actor = map("title", role.getUser().getName(), "label", "actor");
				int source = nodes.indexOf(actor);
				if (source == -1) {
					nodes.add(actor);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
		}
		return map("nodes", nodes, "links", rels);
	}

	private Map<String, java.lang.Object> map(String key1, java.lang.Object value1, String key2, java.lang.Object value2) {
		Map<String, java.lang.Object> result = new HashMap<String, java.lang.Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

    @Transactional(readOnly = true)
    public Object findByTitle(String title) {
        Object result = movieRepository.findByTitle(title);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Object> findByTitleLike(String title) {
        Collection<Object> result = movieRepository.findByTitleLike(title);
        return result;
    }

	@Transactional(readOnly = true)
	public Map<String, java.lang.Object>  graph(int limit) {
		Collection<Object> result = movieRepository.graph(limit);
		return toD3Format(result);
	}
}
