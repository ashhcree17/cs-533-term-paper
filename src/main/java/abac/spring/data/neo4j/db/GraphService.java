package abac.spring.data.neo4j.db;

import org.neo4j.graphdb.GraphDatabaseService;

public interface GraphService {
    public GraphDatabaseService getGraphDbService();
}
