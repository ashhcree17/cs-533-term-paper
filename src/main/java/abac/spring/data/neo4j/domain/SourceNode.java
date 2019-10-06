package abac.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public interface SourceNode {
    List<SourceNode> nodes = new ArrayList<>();

    List<SourceNode> getNodes();
    void addNode(SourceNode sourceNode);
    void setNodes(List<SourceNode> sourceNodes);
}
