package abac.spring.data.neo4j.repositories;

import abac.spring.data.neo4j.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AccessRightRepositoryTest {
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

    @Before
    public void setUp() {
        // set up nodes
        ObjectAttribute pulse = new ObjectAttribute("type:pulse");
        AccessRight read = new AccessRight("read");
        UserAttribute researcher = new UserAttribute("role:researcher");
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

    @Test
    public void testFindByType() {
        AccessRight result = accessRightRepository.findByType("read");
        assertNotNull(result);
        assertEquals("read", result.getType());
        assertEquals("type:pulse", result.getObjectAttributes().get(0).getType());
        assertEquals("role:researcher", result.getUserAttributes().get(0).getType());
    }

    @Test
    public void testFindByTypeLike() {
        Collection<AccessRight> resultList = accessRightRepository.findByTypeLike("read");
        assertEquals(1, resultList.size());
        AccessRight result = resultList.iterator().next();
        assertEquals("read", result.getType());
        assertEquals("type:pulse", result.getObjectAttributes().get(0).getType());
        assertEquals("role:researcher", result.getUserAttributes().get(0).getType());
    }

    @Test
    public void testFindAll() {
        Iterable<AccessRight> result = accessRightRepository.findAll();
        assertEquals(1, ((Collection<?>) result).size());
    }
}