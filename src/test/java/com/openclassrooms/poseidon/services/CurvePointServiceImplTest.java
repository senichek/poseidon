package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassrooms.poseidon.domain.CurvePoint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class CurvePointServiceImplTest {

    @Autowired
    CurvePointService curvePointService;

    private static CurvePoint expected;

    @BeforeAll
    private static void setup() {
        expected = new CurvePoint();
    }

    @Test
    public void createTest() throws Exception {
        expected.setId(1);
        expected.setCurveId(5);
        expected.setTerm(12.0);
        expected.setValue(14.0);

        CurvePoint actual = curvePointService.create(new CurvePoint(1, 5, null, 12.0, 14.0, null));
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCurveId(), actual.getCurveId());
        assertEquals(expected.getTerm(), actual.getTerm());
        assertEquals(expected.getValue(), actual.getValue());
    }

    @Test
    public void createWithExceptionTest() throws Exception {
        expected.setCurveId(null);
        Exception exception = assertThrows(Exception.class, () -> curvePointService.create(expected));
        assertTrue(exception.getMessage().contains("CurveID is null."));

        expected.setCurveId(-1);
        expected.setTerm(-1.0);
        expected.setValue(-1.0);
        exception = assertThrows(Exception.class, () -> curvePointService.create(expected));
        assertTrue(exception.getMessage().contains("Curve ID, Term or Value cannot be negative."));
    }

    @Test
    public void getAllTest() throws Exception {
        expected.setId(1);
        expected.setCurveId(5);
        expected.setTerm(12.0);
        expected.setValue(14.0);
        curvePointService.create(expected);
        assertEquals(1, curvePointService.getAll().size());
        assertEquals(1, curvePointService.getAll().get(0).getId());
    }

    @Test
    public void getByIdTest() throws Exception {
        expected.setCurveId(5);
        expected.setTerm(12.0);
        expected.setValue(14.0);
        curvePointService.create(expected);

        assertEquals(1, curvePointService.getById(1).getId());
        assertEquals(5, curvePointService.getById(1).getCurveId());
        assertEquals(12.0, curvePointService.getById(1).getTerm());
        assertEquals(14.0, curvePointService.getById(1).getValue());
    }

    @Test
    public void getByIdWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> curvePointService.getById(25));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void updateTest() throws Exception {
        expected.setId(1);
        expected.setCurveId(5);
        expected.setTerm(15.0);
        expected.setValue(20.0);
        curvePointService.create(expected);

        // Modify expected and call "Update" method.
        expected.setCurveId(6);
        expected.setTerm(16.0);
        expected.setValue(21.0);
        CurvePoint actual = curvePointService.update(expected);

        assertEquals(1, actual.getId());
        assertEquals(6, actual.getCurveId());
        assertEquals(16.0, actual.getTerm());
        assertEquals(21.0, actual.getValue());
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        expected.setId(25);
        expected.setCurveId(5);
        expected.setTerm(15.0);
        expected.setValue(20.0);
        Exception exception = assertThrows(Exception.class, () -> curvePointService.update(expected));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));

        curvePointService.create(expected);

        expected.setId(1);
        expected.setCurveId(-1);
        exception = assertThrows(Exception.class, () -> curvePointService.update(expected));
        assertTrue(exception.getMessage().contains("Curve ID, Term or Value cannot be negative."));
    }

    @Test
    public void deleteTest() throws Exception {
        expected.setId(null);
        expected.setCurveId(5);
        expected.setTerm(15.0);
        expected.setValue(20.0);
        curvePointService.create(expected);
        // Check that the size of the list has changed.
        assertEquals(1, curvePointService.getAll().size());
        // Check that the entity with id=1 exists.
        assertEquals(1, curvePointService.getAll().get(0).getId());
        // Delete the item with id=1.
        curvePointService.delete(1);
        assertEquals(0, curvePointService.getAll().size());
        Exception exception = assertThrows(Exception.class, () -> curvePointService.delete(1));
        assertTrue(exception.getMessage().contains("Entity with id 1 does not exist."));
    }
}
