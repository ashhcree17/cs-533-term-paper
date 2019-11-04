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
public class UserAttributeTest {

    @Test
    public void testAddAccessRight() {
        UserAttribute userAttribute = new UserAttribute("role:researcher");
        assertNull(userAttribute.getPermissions());

        ObjectAttribute objectAttribute = new ObjectAttribute("type:pulse");
        userAttribute.addPermission(objectAttribute);

        assertEquals(singletonList(objectAttribute), userAttribute.getPermissions());
    }

    @Test
    public void testAddUser() {
        UserAttribute userAttribute = new UserAttribute("role:researcher");
        assertNull(userAttribute.getUsers());

        User user = new User();
        userAttribute.addUser(user);

        assertEquals(singletonList(user), userAttribute.getUsers());
    }
}
