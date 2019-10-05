package abac.spring.data.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Michael Hunger
 * @author Mark Angrish
 */
@SpringBootApplication
@EnableNeo4jRepositories("abac.spring.data.neo4j.repositories")
public class ABACApplication {

    public static void main(String[] args) {
        SpringApplication.run(ABACApplication.class, args);
    }
}