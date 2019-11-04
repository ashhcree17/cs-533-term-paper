package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class UserAttribute implements SourceNode {

    @Id
    @GeneratedValue
	private Long id;
    private String type;
	private String sourceNodeType;

	@Relationship(type = "ASSIGN", direction = Relationship.INCOMING)
	private List<User> users;

	@Relationship(type = "ASSOC")
	private List<ObjectAttribute> permissions;

//	@Relationship(direction = Relationship.UNDIRECTED)
	private List<SourceNode> sourceNodes;

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

	public List<ObjectAttribute> getPermissions() {
		return permissions;
	}

	public void addUser(User user) {
		if (this.users == null) {
			this.users = new ArrayList<>();
		}
		this.users.add(user);
	}

	public void addPermission(ObjectAttribute permission) {
		if (this.permissions == null) {
			this.permissions = new ArrayList<>();
		}
		this.permissions.add(permission);
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
		return "UserAttribute";
	}
}