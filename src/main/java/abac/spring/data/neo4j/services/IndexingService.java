package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.ObjectAttribute;
import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.domain.User;
import abac.spring.data.neo4j.domain.UserAttribute;
import abac.spring.data.neo4j.repositories.ObjectRepository;
import abac.spring.data.neo4j.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexingService {
    private final static Logger LOG = LoggerFactory.getLogger(IndexingService.class);

	private final ObjectRepository objectRepository;
	private final UserRepository userRepository;

	public IndexingService(ObjectRepository objectRepository, UserRepository userRepository) {
		this.objectRepository = objectRepository;
		this.userRepository = userRepository;
	}

	public void index(
			List<User> users,
			List<ObjectNode> objectNodes,
			List<UserAttribute> userAttributes,
			List<ObjectAttribute> objectAttributes
	) {
		// *** pseudo code of Algorithm 4 Indexing ***

		// input: users, objects, and operations
		// output: set of permissions for each user node, i.e., u.permissions where u is a user
		// output: set of permissions for each object node, i.e., o.permissions where o is an object
		// permission := an ASSOC from a user attribute to an object attribute (permission)


		// for all users set u.permissions to an empty object --> {}
		// for all objects set o.permissions to an empty object --> {}

		// set source_nodes equal to the union of users and objects

		// for all source_nodes set s.nodes to set of all nodes visited during DFS traversal from s

		// for all users do
		// for all objects do
		// for all nodeI that belong to u.nodes do
		// for all nodeJ that belong to o.nodes do
		// if nodeI = nodeJ
		// nodeI is an op node
		// set u.permissions equal to union of u.permissions and <nodeI, o>
		// set o.permissions equal to union of o.permissions and <u, nodeI>
	}
}
