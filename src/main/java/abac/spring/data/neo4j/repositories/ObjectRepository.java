package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.domain.SourceNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "objects", path = "objects")
public interface ObjectRepository extends Neo4jRepository<ObjectNode, Long> {
    @Query("MATCH (m:ObjectNode)<-[r:ASSIGN]-(a:ObjectAttribute) RETURN m,r,a LIMIT {limit}")
	Collection<ObjectNode> graph(@Param("limit") int limit);

    @Query("MATCH (m:objectNode)<-[r:ASSIGN|ASSOC*5]-() RETURN m,r")
	Collection<SourceNode> dfs();
}