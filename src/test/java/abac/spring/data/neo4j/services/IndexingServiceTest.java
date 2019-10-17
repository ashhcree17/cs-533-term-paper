package abac.spring.data.neo4j.services;

import abac.spring.data.neo4j.db.ConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexingServiceTest {

	static GraphDatabaseService graphDb = null;

	@BeforeClass
	public static void setupGraphDb() {
		new ClassPathXmlApplicationContext("test-graph-context.xml");
		graphDb = ConnectionFactory.getInstance().getDb();
		System.out.println("graphDb = " + graphDb);
	}

	@AfterClass
	public static void destroyGraph() throws SQLException {
		graphDb.shutdown();
	}


	public GraphDatabaseService getGraphDb() {
		return graphDb;
	}


//	@Autowired
//	private IndexingService indexingService;

//	@Autowired
//	private ObjectRepository objectRepository;
//
//	@Autowired
//	private ObjectAttributeRepository objAttrRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private UserAttributeRepository userAttributeRepository;
//
//	@Autowired
//	private AccessRightRepository accessRightRepository;

//	@Before
//	public void setUp() {
//		// set up nodes
//		ObjectAttribute pulse = new ObjectAttribute();
//		AccessRight read = new AccessRight();
//		UserAttribute researcher = new UserAttribute();
//		ObjectNode o1 = new ObjectNode();
//		User u1 = new User();
//
//		// setup relationships
//		o1.addObjectAttribute(pulse);
//
//		u1.addUserAttribute(researcher);
//
//		researcher.addUser(u1);
//		researcher.addAccessRight(read);
//
//		read.addUserAttribute(researcher);
//		read.addObjectAttribute(pulse);
//
//		pulse.addAccessRight(read);
//		pulse.addObjectNode(o1);
//
//		objAttrRepository.save(pulse);
//		objectRepository.save(o1);
//		accessRightRepository.save(read);
//		userAttributeRepository.save(researcher);
//		userRepository.save(u1);
//	}

//	@Before
//	public void setupTestDatabase() {
//		graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase( testDirectory.directory() );
//	}

//	/**
//	 * Test of index method, of class IndexingService.
//	 */
//	@Test
//	public void testIndex() {
//		Iterable<User> userItr = userRepository.findAll();
//		User user1 = userItr.iterator().next();
//
//		Iterable<ObjectNode> objItr = objectRepository.findAll();
//		ObjectNode obj1 = objItr.iterator().next();
//
//		Iterable<UserAttribute> userAttrItr = userAttributeRepository.findAll();
//		UserAttribute userAttr = userAttrItr.iterator().next();
//
//		Iterable<ObjectAttribute> objAttrItr = objAttrRepository.findAll();
//		ObjectAttribute objectAttr = objAttrItr.iterator().next();
//
//		indexingService.index(singletonList(user1),
//				singletonList(obj1));
//
//		assertNotNull(obj1.getPermissions());
//		assertNotNull(user1.getPermissions());
//	}
}