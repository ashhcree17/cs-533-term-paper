package movies.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "ASSIGN")
public class Role {

    @Id
    @GeneratedValue
	private Long id;
	private List<String> roles = new ArrayList<>();

	@StartNode
	private User user;

	@EndNode
	private Object object;

	public Role() {
	}

	public Role(Object object, User actor) {
		this.object = object;
		this.user = actor;
	}

	public Long getId() {
	    return id;
	}

	public List<String> getRoles() {
	    return roles;
	}

	public User getUser() {
	    return user;
	}

	public Object getObject() {
	    return object;
	}

    public void addRoleName(String name) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(name);
    }
}