package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RelationshipEntity(type = "ASSOC")
public class Permission {

    @Id
    @GeneratedValue
    private Long id;
    private List<String> permissions = new ArrayList<>();

    @StartNode
    private UserAttribute userAttribute;

    @EndNode
    private ObjectAttribute objectAttribute;

    public Permission() {}

    public Long getId() {
        return id;
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
