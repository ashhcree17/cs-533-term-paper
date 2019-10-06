package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.*;
import abac.spring.data.neo4j.services.IndexingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexingServiceTest {

	@Autowired
	private IndexingService indexingService;

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private ObjectAttributeRepository objAttrRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAttributeRepository userAttributeRepository;

	@Autowired
	private AccessRightRepository accessRightRepository;

	@Before
	public void setUp() {
		// set up nodes
		ObjectAttribute pulse = new ObjectAttribute("type:pulse");
		ObjectNode o1 = new ObjectNode();
		AccessRight read = new AccessRight("read");
		UserAttribute researcher = new UserAttribute("role:researcher");
		User u1 = new User(singletonList(researcher));

		// setup relationships
		o1.addObjectAttribute(pulse);

		u1.addUserAttribute(researcher);

		researcher.addUser(u1);
		researcher.addAccessRight(read);

		read.addUserAttribute(researcher);
		read.addObjectAttribute(pulse);

		pulse.addAccessRight(read);
		pulse.addObjectNode(o1);

		objAttrRepository.save(pulse);
		objectRepository.save(o1);
		accessRightRepository.save(read);
		userAttributeRepository.save(researcher);
		userRepository.save(u1);
	}

	/**
	 * Test of index method, of class IndexingService.
	 */
	@Test
	public void testIndex() {
		Iterable<User> userItr = userRepository.findAll();
		User user1 = userItr.iterator().next();

		Iterable<ObjectNode> objItr = objectRepository.findAll();
		ObjectNode obj1 = objItr.iterator().next();

		Iterable<UserAttribute> userAttrItr = userAttributeRepository.findAll();
		UserAttribute userAttr = userAttrItr.iterator().next();

		Iterable<ObjectAttribute> objAttrItr = objAttrRepository.findAll();
		ObjectAttribute objectAttr = objAttrItr.iterator().next();

		indexingService.index(singletonList(user1),
				singletonList(obj1),
				singletonList(userAttr),
				singletonList(objectAttr));

		assertNotNull(obj1.getPermissions());
		assertNotNull(user1.getPermissions());
	}
//
//	/**
//	 * Test of findByTitleContaining method, of class ObjectRepository.
//	 */
//	@Test
//	public void testFindByTitleContaining() {
//		String title = "*Matrix*";
//		Collection<ObjectNode> result = objectRepository.findByTitleLike(title);
//		assertNotNull(result);
//		assertEquals(1, result.size());
//	}
//
//	/**
//	 * Test of graph method, of class ObjectRepository.
//	 */
//	@Test
//	public void testGraph() {
//		Collection<ObjectNode> graph = objectRepository.graph(5);
//
//		assertEquals(1, graph.size());
//
//		ObjectNode object = graph.iterator().next();
//
//		assertEquals(1, object.getObjectAttributes().size());
//
//		assertEquals("The Matrix", object.getTitle());
//		assertEquals("Keanu Reeves", object.getObjectAttributes().iterator().next().getUser().getName());
//	}
}