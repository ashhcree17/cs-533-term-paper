package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
public interface PersonRepository extends Neo4jRepository<User, Long> {

    User findByName(String name);

}