package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class AccessRight {

    @Id
    @GeneratedValue
    private Long id;
    private String type;

    @Relationship(type = "ASSOC", direction = Relationship.INCOMING)
    private List<Role> roles;

    @Relationship(type = "ASSOC", direction = Relationship.OUTGOING)
    private List<ObjectAttribute> objectAttributes;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<ObjectAttribute> getObjectAttributes() {
        return objectAttributes;
    }
}
