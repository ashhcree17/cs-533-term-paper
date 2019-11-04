package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RelationshipEntity(type = "ASSOC")
public class Permission {

    @Id
    @GeneratedValue
    private Long id;
    @Property
    private String type;

    @StartNode
    private UserAttribute userAttribute;

    @EndNode
    private ObjectAttribute objectAttribute;

    public Permission() {}

    public Permission(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public UserAttribute getUserAttribute() {
        return userAttribute;
    }

    public ObjectAttribute getObjectAttribute() {
        return objectAttribute;
    }

    public void setUserAttribute(UserAttribute userAttribute) {
        this.userAttribute = userAttribute;
    }

    public void setObjectAttribute(ObjectAttribute objectAttribute) {
        this.objectAttribute = objectAttribute;
    }
}
