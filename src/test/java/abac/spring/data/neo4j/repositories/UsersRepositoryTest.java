package abac.spring.data.neo4j.repositories;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pdtyreus
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UsersRepositoryTest {

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private UserRepository userRepository;

//	@Before
//	public void setUp() {
//		Object matrix = new Object("The Matrix", 1999, "Welcome to the Real World");
//
//		objectRepository.save(matrix);
//
//		User keanu = new User("Keanu Reeves", 1964);
//
//		userRepository.save(keanu);
//
//		Role neo = new Role(matrix, keanu);
//		neo.addRoleType("Neo");
//
//		matrix.addObjectAttribute(neo);
//
//		objectRepository.save(matrix);
//	}
//
//	/**
//	 * Test of findByTitle method, of class ObjectRepository.
//	 */
//	@Test
//	public void testFindByTitle() {
//
//		String title = "The Matrix";
//		Object result = objectRepository.findByTitle(title);
//		assertNotNull(result);
//		assertEquals(1999, result.getReleased());
//	}
//
//	/**
//	 * Test of findByTitleContaining method, of class ObjectRepository.
//	 */
//	@Test
//	public void testFindByTitleContaining() {
//		String title = "*Matrix*";
//		Collection<Object> result = objectRepository.findByTitleLike(title);
//		assertNotNull(result);
//		assertEquals(1, result.size());
//	}
//
//	/**
//	 * Test of graph method, of class ObjectRepository.
//	 */
//	@Test
//	public void testGraph() {
//		Collection<Object> graph = objectRepository.graph(5);
//
//		assertEquals(1, graph.size());
//
//		Object object = graph.iterator().next();
//
//		assertEquals(1, object.getObjectAttributes().size());
//
//		assertEquals("The Matrix", object.getTitle());
//		assertEquals("Keanu Reeves", object.getObjectAttributes().iterator().next().getUser().getName());
//	}
}