package movies.spring.data.neo4j.repositories;

import java.util.Collection;

import movies.spring.data.neo4j.domain.Object;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends Neo4jRepository<Object, Long> {

	Object findByTitle(@Param("title") String title);

	Collection<Object> findByTitleLike(@Param("title") String title);

    @Query("MATCH (m:Object)<-[r:ACTED_IN]-(a:User) RETURN m,r,a LIMIT {limit}")
	Collection<Object> graph(@Param("limit") int limit);
}