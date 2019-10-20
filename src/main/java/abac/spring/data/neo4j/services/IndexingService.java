package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.domain.SourceNode;
import abac.spring.data.neo4j.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class IndexingService {
	public IndexingService() {}

	void DFS(SourceNode v) {
//	 GraphDatabaseService database = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
//		RelationshipType association = () -> "ASSOC";
//		TraversalDescription traversalDescription = database.traversalDescription()
//				.depthFirst()
//				.sort(new PathComparatorByName())
//				.relationships(association, Direction.OUTGOING);

		System.out.print("traversal description: ");
//		System.out.print(traversalDescription);
//		v.addNode(v);
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
