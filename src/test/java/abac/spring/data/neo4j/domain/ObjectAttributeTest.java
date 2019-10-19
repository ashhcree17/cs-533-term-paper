package abac.spring.data.neo4j.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ObjectAttributeTest {

    @Test
    public void testAddAccessRight() {
        ObjectAttribute objectAttribute = new ObjectAttribute("type:pulse");
        assertNull(objectAttribute.getAccessRights());

        AccessRight accessRight = new AccessRight("read");
        objectAttribute.addAccessRight(accessRight);

        assertEquals(singletonList(accessRight), objectAttribute.getAccessRights());
    }

    @Test
    public void testAddObjectNode() {
        ObjectAttribute objectAttribute = new ObjectAttribute("type:pulse");
        assertNull(objectAttribute.getAccessRights());

        ObjectNode objectNode = new ObjectNode();
        objectAttribute.addObjectNode(objectNode);

        assertEquals(singletonList(objectNode), objectAttribute.getObjectNodes());
    }
}
