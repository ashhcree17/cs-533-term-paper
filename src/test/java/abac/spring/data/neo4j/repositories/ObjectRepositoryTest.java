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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ObjectRepositoryTest {
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

//    @Autowired
//    private PermissionRepository permissionRepository;

    @Before
    public void setUp() {
        // set up nodes
        ObjectAttribute pulse = new ObjectAttribute("type:pulse");
        Permission read = new Permission();
        UserAttribute researcher = new UserAttribute("role:researcher");
        ObjectNode o1 = new ObjectNode();
        User u1 = new User();

        // setup relationships
        o1.addObjectAttribute(pulse);

        u1.addUserAttribute(researcher);

        researcher.addUser(u1);
        researcher.addPermission(pulse);

        read.setUserAttribute(researcher);
        read.setObjectAttribute(pulse);

        pulse.addPermission(read);
        pulse.addObjectNode(o1);

        objAttrRepository.save(pulse);
        objectRepository.save(o1);
//        permissionRepository.save(read);
        userAttributeRepository.save(researcher);
        userRepository.save(u1);
    }

    @Test
    public void testFindById() {
        Optional<ObjectNode> resultOpt = objectRepository.findById(1L);
        assertTrue(resultOpt.isPresent());
        ObjectNode result = resultOpt.get();
        assertEquals(1, result.getObjectAttributes().size());
        assertEquals("type:pulse", result.getObjectAttributes().get(0).getType());
    }

    @Test
    public void testFindAll() {
        Iterable<ObjectNode> result = objectRepository.findAll();
        assertEquals(1, ((Collection<?>) result).size());
    }
}