package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.ObjectAttribute;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "objectAttributes", path = "objectAttributes")
public interface ObjectAttributeRepository extends Neo4jRepository<ObjectAttribute, Long> {
	ObjectAttribute findByType(@Param("type") String type);
    Collection<ObjectAttribute> findByTypeLike(@Param("type") String type);
}