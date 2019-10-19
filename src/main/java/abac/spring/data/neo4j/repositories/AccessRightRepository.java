package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.AccessRight;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "accessRights", path = "accessRights")
public interface AccessRightRepository extends Neo4jRepository<AccessRight, Long> {
    AccessRight findByType(@Param("type") String type);
    Collection<AccessRight> findByTypeLike(@Param("type") String type);
}