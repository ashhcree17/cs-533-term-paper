package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.ObjectNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "objects", path = "objects")
public interface ObjectRepository extends Neo4jRepository<ObjectNode, Long> {
    @Query("MATCH (m:ObjectNode)<-[r:ASSOC]-(a:User) RETURN m,r,a LIMIT {limit}")
	Collection<ObjectNode> graph(@Param("limit") int limit);
//
//    //todo update this
//    @Query("MATCH (m:ObjectNode)<-[r:ACTED_IN]-(a:User) RETURN m,r,a LIMIT {limit}")
//	Collection<ObjectNode> index(@Param("limit") int limit);
}