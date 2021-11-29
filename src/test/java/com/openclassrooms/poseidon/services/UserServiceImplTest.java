package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.openclassrooms.poseidon.domain.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    private static User expected;

    @BeforeAll
    private static void setup() {
        expected = new User();
    }

    @Test
    public void createTest() throws Exception {
        expected.setUsername("test");
        expected.setFullname("test user");
        expected.setRole("ADMIN");
        expected.setRawPassword("Pass11111#");
        User actual = userService.create(expected);
        // There are 2 users already in DB, this one is going to be the 3rd one.
        assertEquals(3, actual.getId());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getFullname(), actual.getFullname());
        assertEquals(expected.getRole(), actual.getRole());
    }

    @Test
    public void createWithExceptionTest() throws Exception {
        expected.setUsername("");
        Exception exception = assertThrows(Exception.class, () -> userService.create(expected));
        assertTrue(exception.getMessage().contains("Username is empty or null."));

        expected.setUsername("Username");
        expected.setFullname("");
        exception = assertThrows(Exception.class, () -> userService.create(expected));
        assertTrue(exception.getMessage().contains("Fullname is empty or null."));

        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("");
        exception = assertThrows(Exception.class, () -> userService.create(expected));
        assertTrue(exception.getMessage().contains("Role is empty or null."));

        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("USER");
        expected.setRawPassword("");
        exception = assertThrows(Exception.class, () -> userService.create(expected));
        assertTrue(exception.getMessage().contains("Password is empty or null."));

        expected.setUsername("Admin");
        assertNull(userService.create(expected));
    }

    @Test
    public void getAllTest() throws Exception {
        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("USER");
        expected.setRawPassword("Pass11111#");
        userService.create(expected);
        assertEquals(3, userService.getAll().size());
        assertEquals(3, userService.getAll().get(2).getId());
    }

    @Test
    public void getByIdTest() throws Exception {
        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("USER");
        expected.setRawPassword("Pass11111#");
        userService.create(expected);

        assertEquals(3, userService.getById(3).getId());
        assertEquals("Username", userService.getById(3).getUsername());
        assertEquals("Fullname", userService.getById(3).getFullname());
        assertEquals("USER", userService.getById(3).getRole());
        assertNotNull(userService.getById(3).getEncodedPassword());
    }

    @Test
    public void getByIdWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> userService.getById(25));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void updateTest() throws Exception {
        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("USER");
        expected.setRawPassword("Pass11111#");
        userService.create(expected);

        // Modify expected and call "Update" method.
        expected.setId(3);
        expected.setUsername("Username updated");
        expected.setFullname("Fullname updated");
        expected.setRole("ADMIN");
        User actual = userService.update(expected);

        assertEquals(3, actual.getId());
        assertEquals("Username updated", actual.getUsername());
        assertEquals("Fullname updated", actual.getFullname());
        assertEquals("ADMIN", actual.getRole());
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        expected.setId(25);
        Exception exception = assertThrows(Exception.class, () -> userService.update(expected));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));

        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("USER");
        expected.setRawPassword("Pass11111#");

        userService.create(expected);

        expected.setId(3);
        expected.setUsername("");
        exception = assertThrows(Exception.class, () -> userService.update(expected));
        assertTrue(exception.getMessage().contains("Username is empty or null."));

        expected.setUsername("Username");
        expected.setFullname("");
        exception = assertThrows(Exception.class, () -> userService.update(expected));
        assertTrue(exception.getMessage().contains("Fullname is empty or null."));

        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("");
        exception = assertThrows(Exception.class, () -> userService.update(expected));
        assertTrue(exception.getMessage().contains("Role is empty or null."));

        // Admin exists in DB, we cannot use this name again.
        expected.setUsername("Admin");
        expected.setRole("ADMIN");
        expected.setUsername("Admin");
        assertNull(userService.update(expected));
    }

    @Test
    public void deleteTest() throws Exception {
        expected.setUsername("Username");
        expected.setFullname("Fullname");
        expected.setRole("USER");
        expected.setRawPassword("Pass11111#");
        userService.create(expected);
        // Check that the size of the list has changed. It should become 3.
        assertEquals(3, userService.getAll().size());
        // Check that the entity with id=3 exists.
        assertEquals(3, userService.getAll().get(2).getId());
        // Delete the item with id=3.
        // The list should be back to 2 elements.
        userService.delete(3);
        assertEquals(2, userService.getAll().size());
        Exception exception = assertThrows(Exception.class, () -> userService.delete(3));
        assertTrue(exception.getMessage().contains("Entity with id 3 does not exist."));
    }
}
