package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.*;
import abac.spring.data.neo4j.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class IndexingService {
	@Autowired
	ObjectRepository objectRepository;

	@Autowired
	ObjectAttributeRepository objAttrRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAttributeRepository userAttributeRepository;

	@Autowired
	AccessRightRepository accessRightRepository;

	public IndexingService(ObjectRepository objectRepository,
						   ObjectAttributeRepository objAttrRepository,
						   UserRepository userRepository,
						   UserAttributeRepository userAttributeRepository,
						   AccessRightRepository accessRightRepository) {
		this.objectRepository = objectRepository;
		this.objAttrRepository = objAttrRepository;
		this.userRepository = userRepository;
		this.userAttributeRepository = userAttributeRepository;
		this.accessRightRepository = accessRightRepository;
	}

	public IndexingService() {}

	void DFS(SourceNode v) {
//	 GraphDatabaseService database = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
//		RelationshipType association = () -> "ASSOC";
//		TraversalDescription traversalDescription = database.traversalDescription()
//				.depthFirst()
//				.sort(new PathComparatorByName())
//				.relationships(association, Direction.OUTGOING);

//		String sourceNodeType = v.getSourceNodeType();
//		switch(sourceNodeType) {
//			case "ObjectNode":
//				ObjectNode objectNode = (ObjectNode) v;
//				List<ObjectAttribute> objectAttributes = objectNode.getObjectAttributes();
//				for (ObjectAttribute objectAttribute : objectAttributes) {
//					v.addNode(objectAttribute);
//				}
//				break;
//			case "User":
//				User user = (User) v;
//				List<UserAttribute> userAttributes = user.getUserAttributes();
//				for (UserAttribute userAttribute : userAttributes) {
//					v.addNode(userAttribute);
//				}
//				break;
//			case "ObjectAttribute":
//				ObjectAttribute objectAttribute = (ObjectAttribute) v;
//				List<AccessRight> accessRights = objectAttribute.getAccessRights();
//				for (AccessRight accessRight : accessRights) {
//					v.addNode(accessRight);
//				}
//				List<ObjectNode> objectNodes = objectAttribute.getObjectNodes();
//				for (ObjectNode objectNode1 : objectNodes) {
//					v.addNode(objectNode1);
//				}
//				break;
//			case "UserAttribute":
//				UserAttribute userAttribute = (UserAttribute) v;
//				List<AccessRight> accessRights1 = userAttribute.getAccessRights();
//				for (AccessRight accessRight : accessRights1) {
//					v.addNode(accessRight);
//				}
//				List<User> users = userAttribute.getUsers();
//				for (User user1 : users) {
//					v.addNode(user1);
//				}
//				break;
//			case "AccessRight":
//				AccessRight accessRight = (AccessRight) v;
//				List<UserAttribute> userAttributes1 = accessRight.getUserAttributes();
//				for (UserAttribute userAttribute1 : userAttributes1) {
//					v.addNode(userAttribute1);
//				}
//				List<ObjectAttribute> objectAttributes1 = accessRight.getObjectAttributes();
//				for (ObjectAttribute objectAttribute1 : objectAttributes1) {
//					v.addNode(objectAttribute1);
//				}
//				break;
//
//		}
		System.out.println("start of DFS");

		Collection<SourceNode> sourceNodes = objectRepository.dfs();

		System.out.println("end of DFS");
		System.out.print("object source nodes: ");
		System.out.print(sourceNodes);
	}

	/*
	Algorithm 4 Indexing

	input: users, objects, and operations
	output: set of permissions for each user node, i.e., u.permissions where u is a user
	output: set of permissions for each object node, i.e., o.permissions where o is an object
	permission := an ASSOC from a user attribute to an object attribute (permission)
	*/
	public void index(List<User> users, List<ObjectNode> objectNodes) {
		// for all users set u.permissions to an empty object --> {}
		for (User user : users) {
			HashMap<SourceNode, ObjectNode> emptyHashMap = new HashMap<>();
			user.setPermissions(emptyHashMap);
		}
		// for all objects set o.permissions to an empty object --> {}
		for (ObjectNode objectNode : objectNodes) {
			HashMap<SourceNode, User> emptyHashMap = new HashMap<>();
			objectNode.setPermissions(emptyHashMap);
		}

		// set source_nodes equal to the union of users and objects
		List<SourceNode> sourceNodes = new ArrayList<>(users);
		sourceNodes.addAll(objectNodes);

		// for all source_nodes s set s.nodes to set of all nodes visited during DFS traversal from s
		for (SourceNode sourceNode : sourceNodes) {
			DFS(sourceNode);
		}

		// for all users do
		for (User user : users) {
			// for all objects do
			for (ObjectNode objectNode : objectNodes) {
				// for all nodeI that belong to u.nodes do
				for (SourceNode nodeI : user.getNodes()) {
					// for all nodeJ that belong to o.nodes do
					for (SourceNode nodeJ : objectNode.getNodes()) {
						// if nodeI = nodeJ
						if (nodeI.equals(nodeJ)) {
							// nodeI is an op node
							// set u.permissions equal to union of u.permissions and <nodeI, o>
							user.setPermissions(user.getPermissions());
							user.addPermission(nodeI, objectNode);

							// set o.permissions equal to union of o.permissions and <u, nodeI>
							objectNode.setPermissions(objectNode.getPermissions());
							objectNode.addPermission(nodeI, user);
						}
					}
				}
			}
		}
	}
}
