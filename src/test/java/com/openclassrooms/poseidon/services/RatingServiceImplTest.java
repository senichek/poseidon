package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassrooms.poseidon.domain.Rating;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class RatingServiceImplTest {

    @Autowired
    RatingService ratingService;

    private static Rating expected;

    @BeforeAll
    private static void setup() {
        expected = new Rating();
    }

    @Test
    public void createTest() throws Exception {
        expected.setId(1);
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        expected.setOrderNumber(5);
        

        Rating actual = ratingService.create(new Rating(null, "Good", "Good", "Good", 5));
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMoodysRating(), actual.getMoodysRating());
        assertEquals(expected.getSandPRating(), actual.getSandPRating());
        assertEquals(expected.getOrderNumber(), actual.getOrderNumber());
    }

    @Test
    public void createWithExceptionTest() throws Exception {
        expected.setOrderNumber(-1);
        Exception exception = assertThrows(Exception.class, () -> ratingService.create(expected));
        assertTrue(exception.getMessage().contains("Order Number cannot be negative or empty."));

        expected.setOrderNumber(1);
        expected.setMoodysRating("");
        exception = assertThrows(Exception.class, () -> ratingService.create(expected));
        assertTrue(exception.getMessage().contains("One of the fields [Moody's Rating, Sand&P Rating or Fitch Rating] is empty or null."));
    }

    @Test
    public void getAllTest() throws Exception {
        expected.setId(null);
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        expected.setOrderNumber(5);
        ratingService.create(expected);
        assertEquals(1, ratingService.getAll().size());
        assertEquals(1, ratingService.getAll().get(0).getId());
    }

    @Test
    public void getByIdTest() throws Exception {
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        expected.setOrderNumber(5);
        ratingService.create(expected);

        assertEquals(1, ratingService.getById(1).getId());
        assertEquals("Good", ratingService.getById(1).getMoodysRating());
        assertEquals("Good", ratingService.getById(1).getSandPRating());
        assertEquals("Good", ratingService.getById(1).getFitchRating());
    }

    @Test
    public void getByIdWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> ratingService.getById(25));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void updateTest() throws Exception {
        expected.setId(1);
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        expected.setOrderNumber(5);
        ratingService.create(expected);

        // Modify expected and call "Update" method.
        expected.setMoodysRating("Good Updated");
        expected.setSandPRating("Good Updated");
        expected.setFitchRating("Good Updated");
        expected.setOrderNumber(10);
        Rating actual = ratingService.update(expected);

        assertEquals(1, actual.getId());
        assertEquals("Good Updated", actual.getMoodysRating());
        assertEquals("Good Updated", actual.getSandPRating());
        assertEquals("Good Updated", actual.getFitchRating());
        assertEquals(10, actual.getOrderNumber());
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        expected.setId(25);
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        expected.setOrderNumber(5);
        Exception exception = assertThrows(Exception.class, () -> ratingService.update(expected));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));

        ratingService.create(expected);

        expected.setId(1);
        expected.setOrderNumber(1);
        expected.setMoodysRating("");
        expected.setSandPRating("");
        expected.setFitchRating("");
        exception = assertThrows(Exception.class, () -> ratingService.update(expected));
        assertTrue(exception.getMessage().contains("One of the fields [Moody's Rating, Sand&P Rating or Fitch Rating] is empty or null."));
    
        expected.setOrderNumber(-1);
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        exception = assertThrows(Exception.class, () -> ratingService.update(expected));
        assertTrue(exception.getMessage().contains("Order Number cannot be negative or empty."));
    }

    @Test
    public void deleteTest() throws Exception {
        expected.setId(null);
        expected.setMoodysRating("Good");
        expected.setSandPRating("Good");
        expected.setFitchRating("Good");
        expected.setOrderNumber(5);
        ratingService.create(expected);
        // Check that the size of the list has changed.
        assertEquals(1, ratingService.getAll().size());
        // Check that the entity with id=1 exists.
        assertEquals(1, ratingService.getAll().get(0).getId());
        // Delete the item with id=1.
        ratingService.delete(1);
        assertEquals(0, ratingService.getAll().size());
        Exception exception = assertThrows(Exception.class, () -> ratingService.delete(1));
        assertTrue(exception.getMessage().contains("Entity with id 1 does not exist."));
    }
}
