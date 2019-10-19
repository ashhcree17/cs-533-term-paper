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
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ObjectAttributeRepositoryTest {
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
        ObjectAttribute result = objAttrRepository.findByType("type:pulse");
        assertNotNull(result);
        assertEquals("type:pulse", result.getType());
        assertEquals("read", result.getAccessRights().get(0).getType());
    }

    @Test
    public void testFindByTypeLike() {
        Collection<ObjectAttribute> resultList = objAttrRepository.findByTypeLike("type:pulse");
        assertEquals(1, resultList.size());
        ObjectAttribute result = resultList.iterator().next();
        assertEquals("type:pulse", result.getType());
        assertEquals("read", result.getAccessRights().get(0).getType());
    }

    @Test
    public void testFindAll() {
        Iterable<ObjectAttribute> result = objAttrRepository.findAll();
        assertEquals(1, ((Collection<?>) result).size());
    }
}