package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.UserAttribute;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserAttributeRepository extends Neo4jRepository<UserAttribute, Long> {
    UserAttribute findByType(String type);
}