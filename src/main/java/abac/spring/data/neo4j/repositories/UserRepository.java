package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {
    User findById(int id);
}