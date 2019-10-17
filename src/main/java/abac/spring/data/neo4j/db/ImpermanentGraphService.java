package abac.spring.data.neo4j.db;

import org.neo4j.graphdb.GraphDatabaseService;

public class ImpermanentGraphService implements GraphService {

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    @Override
    /**
     * Get an instance of an ImpermanentGraphService
     */
    public GraphDatabaseService getGraphDbService() {
        GraphDatabaseService graphDb = new ImpermanentGraphService().getGraphDbService();
        registerShutdownHook(graphDb);
        return graphDb;
    }
}