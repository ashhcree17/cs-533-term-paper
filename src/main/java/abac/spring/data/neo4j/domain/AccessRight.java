package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class AccessRight {

    @Id
    @GeneratedValue
    private Long id;
    private String type;

    @Relationship(type = "ASSOC", direction = Relationship.INCOMING)
    private List<UserAttribute> userAttributes;

    @Relationship(type = "ASSOC", direction = Relationship.OUTGOING)
    private List<ObjectAttribute> objectAttributes;

    public AccessRight() {}

    public AccessRight(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<UserAttribute> getUserAttributes() {
        return userAttributes;
    }

    public List<ObjectAttribute> getObjectAttributes() {
        return objectAttributes;
    }

    public void addUserAttribute(UserAttribute userAttribute) {
        if (this.userAttributes == null) {
            this.userAttributes = new ArrayList<>();
        }
        this.userAttributes.add(userAttribute);
    }

    public void addObjectAttribute(ObjectAttribute objectAttribute) {
        if (this.objectAttributes == null) {
            this.objectAttributes = new ArrayList<>();
        }
        this.objectAttributes.add(objectAttribute);
    }
}
