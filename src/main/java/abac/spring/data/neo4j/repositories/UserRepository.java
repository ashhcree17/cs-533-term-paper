package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (m:User)<-[r:ASSIGN]-(a:UserAttribute) RETURN m,r,a LIMIT {limit}")
    Collection<User> graph(@Param("limit") int limit);
}