package abac.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class UserAttribute {

    @Id
    @GeneratedValue
	private Long id;
    private String type;

	@JsonIgnoreProperties("role")
	@Relationship(type = "ASSIGN", direction = Relationship.INCOMING)
	private List<User> users;

	@JsonIgnoreProperties("role")
	@Relationship(type = "ASSOC", direction = Relationship.OUTGOING)
	private List<AccessRight> accessRights;

	public UserAttribute() {}

	public UserAttribute(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public List<User> getUsers() {
		return users;
	}

	public List<AccessRight> getAccessRights() {
		return accessRights;
	}

	public void addUser(User user) {
		if (this.users == null) {
			this.users = new ArrayList<>();
		}
		this.users.add(user);
	}

	public void addAccessRight(AccessRight accessRight) {
		if (this.accessRights == null) {
			this.accessRights = new ArrayList<>();
		}
		this.accessRights.add(accessRight);
	}
}