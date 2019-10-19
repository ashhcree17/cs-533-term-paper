package abac.spring.data.neo4j.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserTest {

    @Test
    public void testAddUserAttribute() {
        User user = new User();
        assertNull(user.getUserAttributes());

        UserAttribute userAttribute = new UserAttribute("role:researcher");
        user.addUserAttribute(userAttribute);

        assertEquals(singletonList(userAttribute), user.getUserAttributes());
    }

    @Test
    public void testAddPermission() {
        User user = new User();
        assertNull(user.getPermissions());

        SourceNode sourceNode = new ObjectNode();
        ObjectNode objectNode = new ObjectNode();
        user.addPermission(sourceNode, objectNode);

        HashMap<SourceNode, ObjectNode> permissions = new HashMap<>();
        permissions.put(sourceNode, objectNode);
        assertEquals(permissions, user.getPermissions());
    }
}
