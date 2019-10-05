package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.Role;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface RoleRepository extends Neo4jRepository<Role, Long> {
    Role findByType(String type);
}