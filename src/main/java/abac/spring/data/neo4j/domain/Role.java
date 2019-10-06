package abac.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Role {

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

	public Role() {}

	public Role(String type) {
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
}