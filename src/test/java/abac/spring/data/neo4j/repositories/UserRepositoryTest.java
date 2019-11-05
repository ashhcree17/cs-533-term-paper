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
public class UserRepositoryTest {
    @Autowired
    ObjectRepository objectRepository;

    @Autowired
    private ObjectAttributeRepository objAttrRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAttributeRepository userAttributeRepository;

    @Before
    public void setUp() {
        // set up nodes
        ObjectAttribute pulse = new ObjectAttribute("type:pulse");
        Permission read = new Permission("read");
        UserAttribute researcher = new UserAttribute("role:researcher");
        ObjectNode o1 = new ObjectNode();
        User u1 = new User();

        // setup relationships
        o1.addObjectAttribute(pulse);

        u1.addUserAttribute(researcher);

        researcher.addUser(u1);
        researcher.addPermission(read);

        read.setUserAttribute(researcher);
        read.setObjectAttribute(pulse);

        pulse.addPermission(read);
        pulse.addObjectNode(o1);

        objAttrRepository.save(pulse);
        objectRepository.save(o1);
        userAttributeRepository.save(researcher);
        userRepository.save(u1);
    }

    @Test
    public void testFindById() {
        Optional<User> resultOpt = userRepository.findById(0L);
        assertTrue(resultOpt.isPresent());
        User result = resultOpt.get();
        assertEquals(1, result.getUserAttributes().size());
        assertEquals("role:researcher", result.getUserAttributes().get(0).getType());
    }

    @Test
    public void testFindAll() {
        Iterable<User> result = userRepository.findAll();
        assertEquals(1, ((Collection<?>) result).size());
    }
}