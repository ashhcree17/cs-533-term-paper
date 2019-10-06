package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.domain.*;
import abac.spring.data.neo4j.repositories.*;
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
		ObjectAttribute pulse = new ObjectAttribute();
		AccessRight read = new AccessRight();
		UserAttribute researcher = new UserAttribute();
		ObjectNode o1 = new ObjectNode();
		User u1 = new User();

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
}