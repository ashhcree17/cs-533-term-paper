package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NodeEntity
public class User implements SourceNode {

    @Id
    @GeneratedValue
	private Long id;
	private String sourceNodeType;

	@Relationship(type = "ASSIGN", direction = Relationship.OUTGOING)
	private List<UserAttribute> userAttributes;

	@Relationship(type = "ASSOC", direction = Relationship.DIRECTION)
	private HashMap<SourceNode, ObjectNode> permissions;

	@Relationship(direction = Relationship.UNDIRECTED)
	private List<SourceNode> sourceNodes;

	public User() {}

	public Long getId() {
		return id;
	}

	public List<UserAttribute> getUserAttributes() {
		return userAttributes;
	}

	public HashMap<SourceNode, ObjectNode> getPermissions() {
		return permissions;
	}

	public void setPermissions(HashMap<SourceNode, ObjectNode> permissions) {
		this.permissions = permissions;
	}

	public void addUserAttribute(UserAttribute userAttribute) {
		if (this.userAttributes == null) {
			this.userAttributes = new ArrayList<>();
		}
		this.userAttributes.add(userAttribute);
	}

	public void addPermission(SourceNode sourceNode, ObjectNode objectNode) {
		if (this.permissions == null) {
			this.permissions = new HashMap<>();
		}
		this.permissions.put(sourceNode, objectNode);
	}

	@Override
	public List<SourceNode> getNodes() {
		return this.sourceNodes;
	}

	@Override
	public void addNode(SourceNode sourceNode) {
		if (this.sourceNodes == null) {
			this.sourceNodes = new ArrayList<>();
		}
		this.sourceNodes.add(sourceNode);
	}

	@Override
	public void setNodes(List<SourceNode> sourceNodes) {
		if (this.sourceNodes == null) {
			this.sourceNodes = new ArrayList<>();
		}
		this.sourceNodes.addAll(sourceNodes);
	}

	@Override
	public String getSourceNodeType() {
		return "User";
	}
}