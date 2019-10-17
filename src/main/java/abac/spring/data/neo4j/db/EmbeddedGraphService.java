package abac.spring.data.neo4j.db;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class EmbeddedGraphService implements GraphService {

    private String pathToDb = null;

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    public void setPathToDb(String pathToDb) {
        this.pathToDb = pathToDb;
    }

    @Override
    /**
     * Get an instance of an EmbeddedGraphDatabaseService
     */
    public GraphDatabaseService getGraphDbService() {

        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(pathToDb).newGraphDatabase();
        registerShutdownHook(graphDb);
        return graphDb;
    }
}
