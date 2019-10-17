package abac.spring.data.neo4j.db;

import abac.spring.data.neo4j.exception.DbException;
import org.neo4j.graphdb.GraphDatabaseService;

public class ConnectionFactory {
    private final static ConnectionFactory instance = new ConnectionFactory();

    private static GraphDatabaseService db;


    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }

    /**
     * Get a handle to the graph database
     *
     * @return GraphDatabaseService
     */
    public GraphDatabaseService getDb() {
        return db;
    }

    public void shutdownDb() {
        db.shutdown();
    }

    /**
     * Set the handle to the graph database (injected)
     *
     * @param graphService an instance of GraphDatabaseService
     */
    public void setGraphService(GraphService graphService) {
        try {
            db = graphService.getGraphDbService();
        } catch (Exception e) {
            throw new DbException("Could not obtain a connection ", e);
        }
    }
}
