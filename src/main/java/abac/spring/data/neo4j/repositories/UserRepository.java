package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.SourceNode;
import abac.spring.data.neo4j.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (m:User)<-[r:ASSIGN]-(a:UserAttribute) RETURN m,r,a LIMIT {limit}")
    Collection<User> graph(@Param("limit") int limit);

//    @Query("MATCH (m)<-[:ASSIGN|ASSOC]-(n) RETURN m,n")
//    List<SourceNode> dfs();

    //match (Yoav:Person{name:"Yoav"})-[:liked]->(movie:Movie),
    //(Yoav)-[:watched]->(movie),
    //(Yoav)-[:other]->(movie)
    //return movie

    @Query("MATCH (a:User) <-[:ASSIGN|ASSOC]- (b) RETURN b")
    List<SourceNode> dfs();
}