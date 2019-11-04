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
    public void testFindByType() {
        ObjectAttribute result = objAttrRepository.findByType("type:pulse");
        assertNotNull(result);
        assertEquals("type:pulse", result.getType());
        assertNotNull(result.getPermissions().get(0));
    }

    @Test
    public void testFindByTypeLike() {
        Collection<ObjectAttribute> resultList = objAttrRepository.findByTypeLike("type:pulse");
        assertEquals(1, resultList.size());
        ObjectAttribute result = resultList.iterator().next();
        assertEquals("type:pulse", result.getType());
        assertNotNull(result.getPermissions().get(0));
    }

    @Test
    public void testFindAll() {
        Iterable<ObjectAttribute> result = objAttrRepository.findAll();
        assertEquals(1, ((Collection<?>) result).size());
    }
}