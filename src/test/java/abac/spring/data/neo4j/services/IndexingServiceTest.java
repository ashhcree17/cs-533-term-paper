package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.*;
import abac.spring.data.neo4j.repositories.ObjectAttributeRepository;
import abac.spring.data.neo4j.repositories.ObjectRepository;
import abac.spring.data.neo4j.repositories.UserAttributeRepository;
import abac.spring.data.neo4j.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	private ObjectAttribute pulse;
	private ObjectAttribute steps;
	private ObjectAttribute rem;
	private ObjectAttribute abcDate;
	private ObjectAttribute xyzDate;
	private ObjectAttribute patient556;
	private ObjectAttribute patient557;

	private Permission read;
	private Permission write;

	private UserAttribute researcher;
	private UserAttribute emergencyPersonnel;
	private UserAttribute physician;

	private ObjectNode o1;
	private ObjectNode o2;
	private ObjectNode o3;
	private ObjectNode o4;
	private ObjectNode o5;
	private ObjectNode o6;

	private User u1;
	private User u2;
	private User u3;
	private User u4;
	private User u5;
	private User u6;
	private User u7;
	private User u8;

	@Test
	public void testIndexSourceNodes() {
		// Start of Neo4J setup:
		pulse = new ObjectAttribute("type:pulse");
		read = new Permission("read");
		researcher = new UserAttribute("role:researcher");
		o1 = new ObjectNode();
		u1 = new User();

		// setup relationships
		researcher.addUser(u1);
		researcher.addPermission(read);

		pulse.addPermission(read);
		pulse.addObjectNode(o1);

		read.setUserAttribute(researcher);
		read.setObjectAttribute(pulse);

		o1.addObjectAttribute(pulse);

		u1.addUserAttribute(researcher);

		// Saving nodes and relationships creates a simple Neo4j policy graph
		pulse = objAttrRepository.save(pulse);
		o1 = objectRepository.save(o1);
		researcher = userAttributeRepository.save(researcher);
		u1 = userRepository.save(u1);
		indexingService = new IndexingService(objectRepository, objAttrRepository, userRepository, userAttributeRepository);

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
		assertEquals(read.getType(), ((ObjectAttribute) sourceNodeList.get(2)).getPermissions().get(0).getType());

		List<SourceNode> userNodeList = user1.getNodes();
		// Verify that all nodes expected from DFS are returned in the user's node list
		assertEquals(researcher.getId(), userNodeList.get(0).getId());
		assertEquals(o1.getId(), userNodeList.get(1).getId());
		assertEquals(pulse.getId(), userNodeList.get(2).getId());
		assertEquals(read.getType(), ((ObjectAttribute) userNodeList.get(2)).getPermissions().get(0).getType());
	}

	/*
	* Input: A simple Neo4j policy graph + any given user node on the graph.
	* Output: The 'permissions' hash-map at the user node, after indexing has been completed on the graph
	* */
	@Test
	public void testIndexPermissionsForUser() {
		// Start of Neo4J setup:

		// 1. Node creation

		// Object attributes
		pulse = new ObjectAttribute("type:pulse");
		steps = new ObjectAttribute("type:steps");
		rem = new ObjectAttribute("type:REM");
		abcDate = new ObjectAttribute("date:aa/bb/cc");
		xyzDate = new ObjectAttribute("date:xx/yy/zz");
		patient556 = new ObjectAttribute("patient:P556");
		patient557 = new ObjectAttribute("patient:P557");

		// Access rights
		read = new Permission("read");
		write = new Permission("write");

		// User attributes
		researcher = new UserAttribute("role:researcher");
		emergencyPersonnel = new UserAttribute("role:emergency response personnel");
		physician = new UserAttribute("role:physician");

		// Object nodes
		o1 = new ObjectNode();
		o2 = new ObjectNode();
		o3 = new ObjectNode();
		o4 = new ObjectNode();
		o5 = new ObjectNode();
		o6 = new ObjectNode();

		// User nodes
		u1 = new User();
		u2 = new User();
		u3 = new User();
		u4 = new User();
		u5 = new User();
		u6 = new User();
		u7 = new User();
		u8 = new User();

		// 2. Setup relationships

		// Object to Object attribute
		o1.addObjectAttribute(patient556);
		o1.addObjectAttribute(xyzDate);
		o1.addObjectAttribute(rem);

		o2.addObjectAttribute(patient556);
		o2.addObjectAttribute(xyzDate);
		o2.addObjectAttribute(pulse);

		o3.addObjectAttribute(patient556);
		o3.addObjectAttribute(xyzDate);
		o3.addObjectAttribute(steps);

		o4.addObjectAttribute(patient557);
		o4.addObjectAttribute(abcDate);
		o4.addObjectAttribute(rem);

		o5.addObjectAttribute(patient557);
		o5.addObjectAttribute(abcDate);
		o5.addObjectAttribute(pulse);

		o6.addObjectAttribute(patient557);
		o6.addObjectAttribute(abcDate);
		o6.addObjectAttribute(steps);

		// User to User attribute
		u1.addUserAttribute(physician);
		u2.addUserAttribute(physician);
		u3.addUserAttribute(physician);
		u4.addUserAttribute(researcher);
		u5.addUserAttribute(researcher);
		u6.addUserAttribute(researcher);
		u7.addUserAttribute(emergencyPersonnel);
		u8.addUserAttribute(emergencyPersonnel);

		// User attribute to User and to Access Right
		physician.addUser(u1);
		physician.addUser(u2);
		physician.addUser(u3);
		physician.addPermission(read);
		physician.addPermission(write);

		researcher.addUser(u4);
		researcher.addUser(u5);
		researcher.addUser(u6);
		researcher.addPermission(read);

		emergencyPersonnel.addUser(u7);
		emergencyPersonnel.addUser(u8);
		emergencyPersonnel.addPermission(read);

		// Access right to User attribute and to Object attribute
		read.setUserAttribute(physician);
		read.setUserAttribute(researcher);
		read.setUserAttribute(emergencyPersonnel);
		read.setObjectAttribute(pulse);
		read.setObjectAttribute(patient556);
		read.setObjectAttribute(patient557);

		write.setUserAttribute(physician);
		write.setObjectAttribute(patient556);
		write.setObjectAttribute(patient557);

		// Object attribute to Object and to Access Right (if applicable)
		rem.addObjectNode(o1);
		rem.addObjectNode(o4);

		pulse.addPermission(read);
		pulse.addObjectNode(o2);
		pulse.addObjectNode(o5);

		steps.addObjectNode(o3);
		steps.addObjectNode(o6);

		abcDate.addObjectNode(o1);
		abcDate.addObjectNode(o2);
		abcDate.addObjectNode(o3);

		xyzDate.addObjectNode(o4);
		xyzDate.addObjectNode(o5);
		xyzDate.addObjectNode(o6);

		patient556.addPermission(read);
		patient556.addPermission(write);
		patient556.addObjectNode(o1);
		patient556.addObjectNode(o2);
		patient556.addObjectNode(o3);

		patient557.addPermission(read);
		patient557.addPermission(write);
		patient557.addObjectNode(o4);
		patient557.addObjectNode(o5);
		patient557.addObjectNode(o6);

		// 3. Saving nodes and relationships creates a simple Neo4j policy graph
		//pulse = objAttrRepository.save(pulse);
		//		o1 = objectRepository.save(o1);
		//		researcher = userAttributeRepository.save(researcher);
		//		u1 = userRepository.save(u1);



		o1 = objectRepository.save(o1);
		o2 = objectRepository.save(o2);
		o3 = objectRepository.save(o3);
		o4 = objectRepository.save(o4);
		o5 = objectRepository.save(o5);
		o6 = objectRepository.save(o6);

		u1 = userRepository.save(u1);
		u2 = userRepository.save(u2);
		u3 = userRepository.save(u3);
		u4 = userRepository.save(u4);
		u5 = userRepository.save(u5);
		u6 = userRepository.save(u6);
		u7 = userRepository.save(u7);
		u8 = userRepository.save(u8);

		researcher = userAttributeRepository.save(researcher);
		emergencyPersonnel = userAttributeRepository.save(emergencyPersonnel);
		physician = userAttributeRepository.save(physician);
		patient556 = objAttrRepository.save(patient556);
		patient557 = objAttrRepository.save(patient557);

		pulse = objAttrRepository.save(pulse);
		steps = objAttrRepository.save(steps);
		rem = objAttrRepository.save(rem);
		abcDate = objAttrRepository.save(abcDate);
		xyzDate = objAttrRepository.save(xyzDate);

		indexingService = new IndexingService(objectRepository, objAttrRepository, userRepository, userAttributeRepository);

		Iterable<User> userItr = userRepository.findAll();
		User user1 = userItr.iterator().next();

		List<User> users = new ArrayList<>();
		userItr.forEach(users::add);

		Iterable<ObjectNode> objItr = objectRepository.findAll();
//		ObjectNode obj1 = objItr.iterator().next();

		List<ObjectNode> objectNodes = new ArrayList<>();
		objItr.forEach(objectNodes::add);

		assertNull(user1.getPermissions());

		indexingService.index(users, objectNodes);

//		HashMap<SourceNode, ObjectNode> permissionsMap = new HashMap<>();
//		permissionsMap.put(u1, o1);
		assertNotNull(user1.getPermissions());
		assertTrue(user1.getPermissions().containsKey(u1));
		assertTrue(user1.getPermissions().containsValue(o1));
	}
}