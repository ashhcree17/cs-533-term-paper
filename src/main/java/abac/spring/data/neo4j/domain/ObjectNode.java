package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class ObjectNode {

	@Id
	@GeneratedValue
	private Long id;

	@Relationship(type = "ASSIGN", direction = Relationship.OUTGOING)
	private List<ObjectAttribute> objectAttributes;

	@Relationship(type = "ASSOC", direction = Relationship.DIRECTION)
	private List<Permission> permissions;

	public ObjectNode() {}

	public Long getId() {
		return id;
	}

	public List<ObjectAttribute> getObjectAttributes() {
		return objectAttributes;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void addObjectAttribute(ObjectAttribute objectAttribute) {
		if (this.objectAttributes == null) {
			this.objectAttributes = new ArrayList<>();
		}
		this.objectAttributes.add(objectAttribute);
	}

	public void addPermission(Permission permission) {
		if (this.permissions == null) {
			this.permissions = new ArrayList<>();
		}
		this.permissions.add(permission);
	}
}