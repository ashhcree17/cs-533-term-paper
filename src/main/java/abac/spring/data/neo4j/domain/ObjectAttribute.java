package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class ObjectAttribute implements SourceNode {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String sourceNodeType;

    @Relationship(type = "ASSIGN", direction = Relationship.INCOMING)
    private List<ObjectNode> objectNodes;

    @Relationship(type = "ASSOC", direction = Relationship.INCOMING)
    private List<AccessRight> accessRights;

    @Relationship(direction = Relationship.UNDIRECTED)
    private List<SourceNode> sourceNodes;

    public ObjectAttribute() {}

    public ObjectAttribute(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<ObjectNode> getObjectNodes() {
        return objectNodes;
    }

    public List<AccessRight> getAccessRights() {
        return accessRights;
    }

    public void addAccessRight(AccessRight accessRight) {
        if (this.accessRights == null) {
            this.accessRights = new ArrayList<>();
        }
        this.accessRights.add(accessRight);
    }

    public void addObjectNode(ObjectNode objectNode) {
        if (this.objectNodes == null) {
            this.objectNodes = new ArrayList<>();
        }
        this.objectNodes.add(objectNode);
    }

    @Override
    public List<SourceNode> getNodes() {
        return this.sourceNodes;
    }

    @Override
    public void addNode(SourceNode sourceNode) {
        if (this.sourceNodes == null) {
            this.sourceNodes = new ArrayList<>();
        }
        this.sourceNodes.add(sourceNode);
    }

    @Override
    public void setNodes(List<SourceNode> sourceNodes) {
        if (this.sourceNodes == null) {
            this.sourceNodes = new ArrayList<>();
        }
        this.sourceNodes.addAll(sourceNodes);
    }

    @Override
    public String getSourceNodeType() {
        return "ObjectAttribute";
    }
}
