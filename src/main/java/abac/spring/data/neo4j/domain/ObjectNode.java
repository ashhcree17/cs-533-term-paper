package abac.spring.data.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class ObjectNode {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnoreProperties("object")
	@Relationship(type = "ASSIGN", direction = Relationship.OUTGOING)
	private List<ObjectAttribute> objectAttributes;

	public ObjectNode() {}

	public Long getId() {
		return id;
	}

	public List<ObjectAttribute> getObjectAttributes() {
		return objectAttributes;
	}

	public void addObjectAttribute(ObjectAttribute objectAttribute) {
		if (this.objectAttributes == null) {
			this.objectAttributes = new ArrayList<>();
		}
		this.objectAttributes.add(objectAttribute);
	}
}