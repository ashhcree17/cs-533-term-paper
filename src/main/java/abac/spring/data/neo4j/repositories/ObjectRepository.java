package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.domain.SourceNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "objects", path = "objects")
public interface ObjectRepository extends Neo4jRepository<ObjectNode, Long> {
    @Query("MATCH (m:ObjectNode)<-[r:ASSIGN]-(a:ObjectAttribute) RETURN m,r,a")
	Collection<ObjectNode> graph();

    @Query("MATCH (m)<-[:ASSIGN|ASSOC]-(n) RETURN m,n")
    List<SourceNode> dfs();
}