package abac.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

public interface SourceNode {
//    List<SourceNode> nodes = new ArrayList<>();
//    String sourceNodeType = null;

    List<SourceNode> getNodes();
    void addNode(SourceNode sourceNode);
    void setNodes(List<SourceNode> sourceNodes);
    Long getId();
    String getSourceNodeType();
}
