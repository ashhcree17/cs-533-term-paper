package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.Object;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "objects", path = "objects")
public interface ObjectRepository extends Neo4jRepository<Object, Long> {

	// todo put in objectattribute repository
//	Object findByType(@Param("type") String type);

	Collection<Object> findByTypeLike(@Param("type") String type);

    @Query("MATCH (m:Object)<-[r:ACTED_IN]-(a:User) RETURN m,r,a LIMIT {limit}")
	Collection<Object> graph(@Param("limit") int limit);

    //todo update this
    @Query("MATCH (m:Object)<-[r:ACTED_IN]-(a:User) RETURN m,r,a LIMIT {limit}")
	Collection<Object> index(@Param("limit") int limit);
}