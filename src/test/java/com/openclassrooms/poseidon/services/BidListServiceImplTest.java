package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import com.openclassrooms.poseidon.domain.BidList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class BidListServiceImplTest {

    @Autowired
    BidListService bidListService;

    @Test
    public void createTest() throws Exception {
        BidList expected = new BidList();
        expected.setBidListId(1);
        expected.setAccount("Test account");
        expected.setType("Test type");
        expected.setBidQuantity(20.0);
        expected.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        BidList actual = bidListService.create(new BidList(null, "Test account", "Test type", 20.0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
        expected.setBidQuantity(20.0);

        assertEquals(expected.getBidListId(), actual.getBidListId());
        assertEquals(expected.getAccount(), actual.getAccount());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getBidQuantity(), actual.getBidQuantity());
       
        LocalDateTime expectedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(expected.getCreationDate().getTime()), TimeZone
        .getDefault().toZoneId()); 

        LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(actual.getCreationDate().getTime()), TimeZone
        .getDefault().toZoneId());
        // Comparing dates without seconds
        assertEquals(expectedDateTime.getYear(), actualDateTime.getYear());
        assertEquals(expectedDateTime.getDayOfYear(), actualDateTime.getDayOfYear());
        assertEquals(expectedDateTime.getHour(), actualDateTime.getHour());
        assertEquals(expectedDateTime.getMinute(), actualDateTime.getMinute());
    }

    @Test
    public void createWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> bidListService.create(new BidList()));
        exception = assertThrows(Exception.class, () -> bidListService.create(new BidList(null, "", "Test type", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)));
        exception = assertThrows(Exception.class, () -> bidListService.create(new BidList(null, "Test account", "", 20.0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)));
        
        assertTrue(exception.getMessage().contains("One of the fields [Account, Type, bidQuantity] is empty or null."));

        exception = assertThrows(Exception.class, () -> bidListService.create(new BidList(null, "Test account", "Test type", -20.0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)));
        assertTrue(exception.getMessage().contains("Bid Quantity cannot be negative."));
    }

    @Test
    public void getAllTest() throws Exception {
        BidList expected_1 = new BidList();
        expected_1.setBidListId(1);
        expected_1.setAccount("Test account 1");
        expected_1.setType("Test type 1");
        expected_1.setBidQuantity(20.0);

        BidList expected_2 = new BidList();
        expected_2.setBidListId(2);
        expected_2.setAccount("Test account 2");
        expected_2.setType("Test type 2");
        expected_2.setBidQuantity(20.0);

        bidListService.create(expected_1);
        bidListService.create(expected_2);

        assertEquals(2, bidListService.getAll().size());
        assertEquals(1, bidListService.getAll().get(0).getBidListId());
        assertEquals(2, bidListService.getAll().get(1).getBidListId());
        assertEquals("Test account 1", bidListService.getAll().get(0).getAccount());
        assertEquals("Test account 2", bidListService.getAll().get(1).getAccount());
        assertEquals("Test type 1", bidListService.getAll().get(0).getType());
        assertEquals("Test type 2", bidListService.getAll().get(1).getType());
        assertEquals(20.0, bidListService.getAll().get(0).getBidQuantity());
        assertEquals(20.0, bidListService.getAll().get(1).getBidQuantity());
    }

    @Test
    public void updateTest() throws Exception {
        // Create bidList
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Test account 1");
        bidList.setType("Test type 1");
        bidList.setBidQuantity(20.0);

        BidList updated = new BidList();
        updated.setBidListId(1);
        updated.setAccount("Test account updated");
        updated.setType("Test type updated");
        updated.setBidQuantity(30.0);

        // Create and then update it.
        bidListService.create(bidList);
        BidList updatedFromService = bidListService.update(updated);
        assertEquals(updated.getBidListId(), updatedFromService.getBidListId());
        assertEquals(updated.getAccount(), updatedFromService.getAccount());
        assertEquals(updated.getType(), updatedFromService.getType());
        assertEquals(updated.getBidQuantity(), updatedFromService.getBidQuantity());
    }

    @Test
    public void updateNotFound() throws Exception {
        BidList updated = new BidList();
        updated.setBidListId(25);
        Exception exception = assertThrows(Exception.class, () -> bidListService.update(updated));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Test account 1");
        bidList.setType("Test type 1");
        bidList.setBidQuantity(20.0);
        bidListService.create(bidList);

        BidList updated = new BidList();
        updated.setBidListId(1);
        updated.setAccount("Test account updated");
        updated.setType("Test type updated");
        updated.setBidQuantity(-1.0);
        
        Exception exception = assertThrows(Exception.class, () -> bidListService.update(updated));
        assertTrue(exception.getMessage().contains("Bid Quantity cannot be negative."));

    }

    @Test
    public void deleteTest() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Test account 1");
        bidList.setType("Test type 1");
        bidList.setBidQuantity(20.0);

        bidListService.create(bidList);
        // Making sure the item was saved.
        assertEquals(1, bidListService.getAll().size());
        bidListService.delete(bidList.getBidListId());
        // Making sure the item was deleted.
        assertEquals(0, bidListService.getAll().size());
    }

    @Test
    public void deleteNotFound() {
        BidList bidList = new BidList();
        bidList.setBidListId(25);

        Exception exception = assertThrows(Exception.class, () -> bidListService.delete(bidList.getBidListId()));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void getByIDTest() throws Exception {
        BidList expected = new BidList();
        expected.setBidListId(1);
        expected.setAccount("Test account 1");
        expected.setType("Test type 1");
        expected.setBidQuantity(20.0);

        bidListService.create(expected);
        BidList actual = bidListService.getById(1);
        assertEquals(expected.getBidListId(), actual.getBidListId());
        assertEquals(expected.getAccount(), actual.getAccount());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getBidQuantity(), actual.getBidQuantity());
    }

    @Test
    public void getByIDWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> bidListService.getById(25));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }
}
