package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassrooms.poseidon.domain.RuleName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class RuleNameServiceImplTest {

    @Autowired
    RuleNameService ruleNameService;

    private static RuleName expected;

    @BeforeAll
    private static void setup() {
        expected = new RuleName();
    }

    @Test
    public void createTest() throws Exception {
        expected.setId(1);
        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("Template");
        

        RuleName actual = ruleNameService.create(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getJson(), actual.getJson());
        assertEquals(expected.getTemplate(), actual.getTemplate());
    }

    @Test
    public void createWithExceptionTest() throws Exception {
        expected.setName("");
        Exception exception = assertThrows(Exception.class, () -> ruleNameService.create(expected));
        assertTrue(exception.getMessage().contains("Name is empty or null."));

        expected.setName("Name");
        expected.setDescription("");
        exception = assertThrows(Exception.class, () -> ruleNameService.create(expected));
        assertTrue(exception.getMessage().contains("Description is empty or null."));

        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("");
        exception = assertThrows(Exception.class, () -> ruleNameService.create(expected));
        assertTrue(exception.getMessage().contains("Json is empty or null."));

        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("");
        exception = assertThrows(Exception.class, () -> ruleNameService.create(expected));
        assertTrue(exception.getMessage().contains("Template is empty or null."));
    }

    @Test
    public void getAllTest() throws Exception {
        expected.setId(null);
        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("Template");
        ruleNameService.create(expected);
        assertEquals(1, ruleNameService.getAll().size());
        assertEquals(1, ruleNameService.getAll().get(0).getId());
    }

    @Test
    public void getByIdTest() throws Exception {
        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("Template");
        ruleNameService.create(expected);

        assertEquals(1, ruleNameService.getById(1).getId());
        assertEquals("Name", ruleNameService.getById(1).getName());
        assertEquals("Descr", ruleNameService.getById(1).getDescription());
        assertEquals("Json", ruleNameService.getById(1).getJson());
        assertEquals("Template", ruleNameService.getById(1).getTemplate());
    }

    @Test
    public void getByIdWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> ruleNameService.getById(25));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void updateTest() throws Exception {
        expected.setId(1);
        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("Template");
        ruleNameService.create(expected);

        // Modify expected and call "Update" method.
        expected.setName("Name Updated");
        expected.setDescription("Descr Updated");
        expected.setJson("Json Updated");
        expected.setTemplate("Template Updated");
        RuleName actual = ruleNameService.update(expected);

        assertEquals(1, actual.getId());
        assertEquals("Name Updated", actual.getName());
        assertEquals("Descr Updated", actual.getDescription());
        assertEquals("Json Updated", actual.getJson());
        assertEquals("Template Updated", actual.getTemplate());
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        expected.setId(25);
        Exception exception = assertThrows(Exception.class, () -> ruleNameService.update(expected));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));

        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("Template");

        ruleNameService.create(expected);

        expected.setId(1);
        expected.setName("");
        exception = assertThrows(Exception.class, () -> ruleNameService.update(expected));
        assertTrue(exception.getMessage().contains("Name is empty or null."));

        expected.setName(null);
        exception = assertThrows(Exception.class, () -> ruleNameService.update(expected));
        assertTrue(exception.getMessage().contains("Name is empty or null."));

        expected.setName("Name");
        expected.setDescription("");
        exception = assertThrows(Exception.class, () -> ruleNameService.update(expected));
        assertTrue(exception.getMessage().contains("Description is empty or null."));

        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("");
        exception = assertThrows(Exception.class, () -> ruleNameService.update(expected));
        assertTrue(exception.getMessage().contains("Json is empty or null."));

        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("");
        exception = assertThrows(Exception.class, () -> ruleNameService.update(expected));
        assertTrue(exception.getMessage().contains("Template is empty or null."));
    }

    @Test
    public void deleteTest() throws Exception {
        expected.setId(null);
        expected.setName("Name");
        expected.setDescription("Descr");
        expected.setJson("Json");
        expected.setTemplate("Template");
        ruleNameService.create(expected);
        // Check that the size of the list has changed.
        assertEquals(1, ruleNameService.getAll().size());
        // Check that the entity with id=1 exists.
        assertEquals(1, ruleNameService.getAll().get(0).getId());
        // Delete the item with id=1.
        ruleNameService.delete(1);
        assertEquals(0, ruleNameService.getAll().size());
        Exception exception = assertThrows(Exception.class, () -> ruleNameService.delete(1));
        assertTrue(exception.getMessage().contains("Entity with id 1 does not exist."));
    }
}
