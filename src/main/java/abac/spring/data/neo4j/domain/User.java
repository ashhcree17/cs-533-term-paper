package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
	private Long id;

	@Relationship(type = "ASSIGN", direction = Relationship.OUTGOING)
	private List<UserAttribute> userAttributes;

	@Relationship(type = "ASSOC", direction = Relationship.DIRECTION)
	private List<Permission> permissions;

	public User() {}

	public User(List<UserAttribute> userAttributes) {
		this.userAttributes = userAttributes;
	}

	public Long getId() {
		return id;
	}

	public List<UserAttribute> getUserAttributes() {
		return userAttributes;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void addUserAttribute(UserAttribute userAttribute) {
		if (this.userAttributes == null) {
			this.userAttributes = new ArrayList<>();
		}
		this.userAttributes.add(userAttribute);
	}

	public void addPermission(Permission permission) {
		if (this.permissions == null) {
			this.permissions = new ArrayList<>();
		}
		this.permissions.add(permission);
	}
}