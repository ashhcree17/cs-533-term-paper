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
public class AccessRightTest {

    @Test
    public void testAddUserAttribute() {
        AccessRight accessRight = new AccessRight("read");
        assertNull(accessRight.getUserAttributes());

        UserAttribute userAttribute = new UserAttribute("role:researcher");
        accessRight.addUserAttribute(userAttribute);

        assertEquals(singletonList(userAttribute), accessRight.getUserAttributes());
    }

    @Test
    public void testAddObjectAttribute() {
        AccessRight accessRight = new AccessRight("read");
        assertNull(accessRight.getUserAttributes());

        ObjectAttribute objectAttribute = new ObjectAttribute("type:pulse");
        accessRight.addObjectAttribute(objectAttribute);

        assertEquals(singletonList(objectAttribute), accessRight.getObjectAttributes());
    }
}
