package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.AccessRight;
import abac.spring.data.neo4j.domain.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AccessRightRepository extends Neo4jRepository<AccessRight, Long> {
    AccessRight findByType(String type);
}