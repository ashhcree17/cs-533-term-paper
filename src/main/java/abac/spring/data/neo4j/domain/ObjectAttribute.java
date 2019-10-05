package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class ObjectAttribute {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String patientId;
    private String date;

    @Relationship(type = "ASSOC", direction = Relationship.INCOMING)
    private List<AccessRight> accessRights;

    @Relationship(type = "ASSIGN", direction = Relationship.INCOMING)
    private List<ObjectNode> objectNodes;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public List<AccessRight> getAccessRights() {
        return accessRights;
    }

    public List<ObjectNode> getObjectNodes() {
        return objectNodes;
    }
}
