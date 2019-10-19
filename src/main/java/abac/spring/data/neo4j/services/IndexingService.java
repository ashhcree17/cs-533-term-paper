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
	// Array  of lists for Adjacency List Representation
	private LinkedList<SourceNode> adj;
	public IndexingService() {}

	// Recursive function used by DFS function
	void DFSUtil(SourceNode v, boolean[] visited) {
		// Mark current node as visited and print it
		visited[v.getId().intValue()] = true;
		System.out.print(v+" ");

		for (int i = 0; i < adj.size(); i++) {
			if (!visited[i]) {
				DFSUtil(adj.get(i), visited);
			}
		}
	}

	// Function to perform DFS traversal
	void DFS(SourceNode v) {
		// Mark all vertices as not visited (set as false by default in Java)
		boolean[] visited = new boolean[v.getId().intValue()];

		// Call recursive helper function to print DFS traversal
		DFSUtil(v, visited);
	}

	public void index(
			List<User> users,
			List<ObjectNode> objectNodes
	) {
		// *** pseudo code of Algorithm 4 Indexing ***

		// input: users, objects, and operations
		// output: set of permissions for each user node, i.e., u.permissions where u is a user
		// output: set of permissions for each object node, i.e., o.permissions where o is an object
		// permission := an ASSOC from a user attribute to an object attribute (permission)

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
			// todo update how this is determined
			sourceNode.setNodes(emptyList());
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
