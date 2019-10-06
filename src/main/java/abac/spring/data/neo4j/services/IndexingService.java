package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.repositories.ObjectRepository;
import abac.spring.data.neo4j.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IndexingService {
    private final static Logger LOG = LoggerFactory.getLogger(IndexingService.class);

	private final ObjectRepository objectRepository;
	private final UserRepository userRepository;

	public IndexingService(ObjectRepository objectRepository, UserRepository userRepository) {
		this.objectRepository = objectRepository;
		this.userRepository = userRepository;
	}

//	@Transactional(readOnly = true)
	public void index(int limit) {
//		Collection<ObjectNode> result = objectRepository.index(limit);


		// pseudo code

		// input: graph of users, objects, operations
		// output: set of permissions for each user node, i.e., u.permissions where u is a user
		// output: set of permissions for each object node, i.e., o.permissions where o is an obj


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
