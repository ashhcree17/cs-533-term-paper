package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.ObjectAttribute;
import abac.spring.data.neo4j.domain.ObjectNode;
import abac.spring.data.neo4j.domain.Role;
import abac.spring.data.neo4j.domain.User;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
public class IndexingServiceTest {

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private ObjectAttributeRepository objAttrRepository;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setUp() {
		ObjectNode o1 = new ObjectNode();
		objectRepository.save(o1);

		ObjectAttribute pulse = new ObjectAttribute("type:pulse");
		objAttrRepository.save(pulse);

		Role researcher = new Role("role:researcher");
		o1.addObjectAttribute(pulse);

		objectRepository.save(o1);
	}
//
//	/**
//	 * Test of findByTitle method, of class ObjectRepository.
//	 */
//	@Test
//	public void testFindByTitle() {
//
//		String title = "The Matrix";
//		ObjectNode result = objectRepository.findByTitle(title);
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