package abac.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
	private Long id;

	@JsonIgnoreProperties("object")
	@Relationship(type = "ASSIGNED", direction = Relationship.OUTGOING)
	private List<Role> roles;

	public User() {}

	public Long getId() {
		return id;
	}

	public List<Role> getRoles() {
		return roles;
	}
}