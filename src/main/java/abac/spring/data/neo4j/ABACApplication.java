package abac.spring.data.neo4j;

import abac.spring.data.neo4j.db.ConnectionFactory;
import abac.spring.data.neo4j.domain.User;
import abac.spring.data.neo4j.domain.UserAttribute;
//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

//@SpringBootApplication
//@EnableNeo4jRepositories("abac.spring.data.neo4j.repositories")
public class ABACApplication {
    public static void main(String[] args) {
//        SpringApplication.run(ABACApplication.class, args);
        new ClassPathXmlApplicationContext("graph-context.xml"); //Trigger injection of the Graph Database

        //Save a pairing
        User u1 = new User();
        User u2 = new User();
        UserAttribute ua = new UserAttribute("role:researcher");
        ua.addUser(u1);
        ua.addUser(u2);
        u1.addUserAttribute(ua);
        u2.addUserAttribute(ua);

        ConnectionFactory.getInstance().shutdownDb();
    }
}