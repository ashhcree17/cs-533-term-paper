package abac.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
	private Long id;

	@JsonIgnoreProperties("object")
	@Relationship(type = "ASSIGNED", direction = Relationship.OUTGOING)
	private List<UserAttribute> userAttributes;

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

	public void addUserAttribute(UserAttribute userAttribute) {
		if (this.userAttributes == null) {
			this.userAttributes = new ArrayList<>();
		}
		this.userAttributes.add(userAttribute);
	}
}