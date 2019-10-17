package abac.spring.data.neo4j;

import abac.spring.data.neo4j.db.ConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class BaseTest {
    static GraphDatabaseService graphDb = null;

    @BeforeClass
    public static void setupGraphDb() {
        new ClassPathXmlApplicationContext("test-graph-context.xml");
        graphDb = ConnectionFactory.getInstance().getDb();
        System.out.println("graphDb = " + graphDb);
    }

    @AfterClass
    public static void destroyGraph() throws SQLException {
        graphDb.shutdown();
    }


    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }
}
