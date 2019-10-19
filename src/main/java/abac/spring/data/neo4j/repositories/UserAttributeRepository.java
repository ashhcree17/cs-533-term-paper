package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.UserAttribute;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "userAttributes", path = "userAttributes")
public interface UserAttributeRepository extends Neo4jRepository<UserAttribute, Long> {
    UserAttribute findByType(@Param("type") String type);
    Collection<UserAttribute> findByTypeLike(@Param("type") String type);
}