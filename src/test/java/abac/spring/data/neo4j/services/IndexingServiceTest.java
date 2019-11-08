package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.*;
import abac.spring.data.neo4j.repositories.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexingServiceTest {

	@InjectMocks
	private IndexingService indexingService;

	@Autowired
	ObjectRepository objectRepository;

	@Autowired
	private ObjectAttributeRepository objAttrRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAttributeRepository userAttributeRepository;

	@Autowired
	private AccessRightRepository accessRightRepository;

	ObjectAttribute pulse;
	AccessRight read;
	UserAttribute researcher;
	ObjectNode o1;
	User u1;

	@Before
	public void setUp() {
		// set up nodes
		pulse = new ObjectAttribute("type:pulse");
		read = new AccessRight("read");
		researcher = new UserAttribute("role:researcher");
		o1 = new ObjectNode();
		u1 = new User();

		// setup relationships
		o1.addObjectAttribute(pulse);

		u1.addUserAttribute(researcher);

		researcher.addUser(u1);
		researcher.addAccessRight(read);

		read.addUserAttribute(researcher);
		read.addObjectAttribute(pulse);

		pulse.addAccessRight(read);
		pulse.addObjectNode(o1);

		pulse = objAttrRepository.save(pulse);
		o1 = objectRepository.save(o1);
		read = accessRightRepository.save(read);
		researcher = userAttributeRepository.save(researcher);
		u1 = userRepository.save(u1);
		indexingService = new IndexingService(objectRepository, objAttrRepository, userRepository, userAttributeRepository, accessRightRepository);
	}

	@Test
	public void testIndexSourceNodes() {
		Iterable<User> userItr = userRepository.findAll();
		User user1 = userItr.iterator().next();

		Iterable<ObjectNode> objItr = objectRepository.findAll();
		ObjectNode obj1 = objItr.iterator().next();

		assertNull(obj1.getNodes());
		assertNull(user1.getNodes());

		indexingService.index(singletonList(user1), singletonList(obj1));

		assertNotNull(obj1.getNodes());
		assertNotNull(user1.getNodes());
		assertNotEquals(emptyList(), obj1.getNodes());
		assertNotEquals(emptyList(), user1.getNodes());

		List<SourceNode> sourceNodeList = obj1.getNodes();
		// Verify that all nodes expected from DFS are returned in the object's node list
		assertEquals(u1.getId(), sourceNodeList.get(0).getId());
		assertEquals(researcher.getId(), sourceNodeList.get(1).getId());
		assertEquals(pulse.getId(), sourceNodeList.get(2).getId());
		assertEquals(read.getId(), sourceNodeList.get(3).getId());

		List<SourceNode> userNodeList = user1.getNodes();
		// Verify that all nodes expected from DFS are returned in the user's node list
		assertEquals(researcher.getId(), userNodeList.get(0).getId());
		assertEquals(o1.getId(), userNodeList.get(1).getId());
		assertEquals(pulse.getId(), userNodeList.get(2).getId());
		assertEquals(read.getId(), userNodeList.get(3).getId());
	}

	/*
	 * Unit test to fulfill following specifications:
	 *
	 * Input: A simple Neo4j policy graph (you can use your own) + any given user node on the graph.
	 * Output: The 'permissions' hash-map at the user node, after indexing has been completed on the graph
	 *
	 */
	@Test
	public void testIndexPermissions() {
		// Find the current user and object saved in the graph
		Iterable<User> userItr = userRepository.findAll();
		User user1 = userItr.iterator().next();
		Iterable<ObjectNode> objItr = objectRepository.findAll();
		ObjectNode obj1 = objItr.iterator().next();

		assertNull(user1.getPermissions());

		indexingService.index(singletonList(user1), singletonList(obj1));

		assertNotNull(user1.getPermissions());

		Iterator<SourceNode> usr1PermissionsSourceNodes = user1.getPermissions().keySet().iterator();
		Iterator<ObjectNode> usr1PermissionsObjectNodes = user1.getPermissions().values().iterator();

		// Verify that the User permission hash map contains the following as its "nodeI" keyset
		List<String> possibleUsrSourceNodeTypes = new ArrayList<>(asList("ObjectAttribute", "UserAttribute", "AccessRight"));
		SourceNode permissionSrcNode1 = usr1PermissionsSourceNodes.next();
		assertTrue(possibleUsrSourceNodeTypes.contains(permissionSrcNode1.getSourceNodeType()));
		SourceNode permissionSrcNode2 = usr1PermissionsSourceNodes.next();
		assertTrue(possibleUsrSourceNodeTypes.contains(permissionSrcNode2.getSourceNodeType()));
		SourceNode permissionSrcNode3 = usr1PermissionsSourceNodes.next();
		assertTrue(possibleUsrSourceNodeTypes.contains(permissionSrcNode3.getSourceNodeType()));

		// Verify that the User permission hash map only contains itself as the value
		ObjectNode permissionObjNode1 = usr1PermissionsObjectNodes.next();
		assertEquals(new Long(1), permissionObjNode1.getId());
		ObjectNode permissionObjNode2 = usr1PermissionsObjectNodes.next();
		assertEquals(new Long(1), permissionObjNode2.getId());
		ObjectNode permissionObjNode3 = usr1PermissionsObjectNodes.next();
		assertEquals(new Long(1), permissionObjNode3.getId());
	}
}