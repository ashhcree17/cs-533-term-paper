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
public class ObjectNodeTest {

    @Test
    public void testAddObjectAttribute() {
        ObjectNode objectNode = new ObjectNode();
        assertNull(objectNode.getObjectAttributes());

        ObjectAttribute objectAttribute = new ObjectAttribute("type:pulse");
        objectNode.addObjectAttribute(objectAttribute);

        assertEquals(singletonList(objectAttribute), objectNode.getObjectAttributes());
    }

    @Test
    public void testAddPermission() {
        ObjectNode objectNode = new ObjectNode();
        assertNull(objectNode.getPermissions());

        SourceNode sourceNode = new User();
        User user = new User();
        objectNode.addPermission(sourceNode, user);

        HashMap<SourceNode, User> permissions = new HashMap<>();
        permissions.put(sourceNode, user);
        assertEquals(permissions, objectNode.getPermissions());
    }
}
