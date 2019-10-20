package abac.spring.data.neo4j.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NodeEntity
public class ObjectNode implements SourceNode {

	@Id
	@GeneratedValue
	private Long id;
	private String sourceNodeType;

	@Relationship(type = "ASSIGN", direction = Relationship.OUTGOING)
	private List<ObjectAttribute> objectAttributes;

	@Relationship(type = "ASSOC", direction = Relationship.DIRECTION)
	private HashMap<SourceNode, User> permissions;

	@Relationship(direction = Relationship.UNDIRECTED)
	private List<SourceNode> sourceNodes;

	public ObjectNode() {}

	public Long getId() {
		return id;
	}

	public List<ObjectAttribute> getObjectAttributes() {
		return objectAttributes;
	}

	public HashMap<SourceNode, User> getPermissions() {
		return permissions;
	}

	public void setPermissions(HashMap<SourceNode, User> permissions) {
		this.permissions = permissions;
	}

	public void addObjectAttribute(ObjectAttribute objectAttribute) {
		if (this.objectAttributes == null) {
			this.objectAttributes = new ArrayList<>();
		}
		this.objectAttributes.add(objectAttribute);
	}

	public void addPermission(SourceNode sourceNode, User user) {
		if (this.permissions == null) {
			this.permissions = new HashMap<>();
		}
		this.permissions.put(sourceNode, user);
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
	public void setSourceNodeType(String sourceNodeType) {
		this.sourceNodeType = sourceNodeType;
	}

	@Override
	public String getSourceNodeType() {
		return this.sourceNodeType;
	}
}