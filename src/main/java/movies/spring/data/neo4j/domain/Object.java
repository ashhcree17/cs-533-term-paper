package movies.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NodeEntity
public class Object {

	@Id
	@GeneratedValue
	private Long id;
	private String type;
	private int patientId;
	private String date;

	@JsonIgnoreProperties("object")
	@Relationship(type = "ASSIGN", direction = Relationship.INCOMING)
	private List<Role> roles;

	public Object() {
	}

	public Object(String type, int patientId, String date) {
		this.type = type;
		this.patientId = patientId;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public int getPatientId() {
		return patientId;
	}

	public String getDate() {
		return date;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		this.roles.add(role);
	}
}