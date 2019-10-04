package abac.spring.data.neo4j.domain;


import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
	private Long id;

	@Relationship(type = "ACTED_IN")
	private List<Object> roles = new ArrayList<>();

	public User() {}

	public Long getId() {
		return id;
	}

	public List<Object> getRoles() {
		return roles;
	}
}