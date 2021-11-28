package com.openclassrooms.poseidon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.openclassrooms.poseidon.domain.Trade;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD) // Tests change the collection, this is why we have to reset the collection
public class TradeServiceImplTest {

    @Autowired
    TradeService tradeService;

    private static Trade expected;

    @BeforeAll
    private static void setup() {
        expected = new Trade();
    }

    @Test
    public void createTest() throws Exception {
        expected.setTradeId(1);
        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(5.0);
        expected.setSellQuantity(7.0);
        

        Trade actual = tradeService.create(expected);
        assertEquals(expected.getTradeId(), actual.getTradeId());
        assertEquals(expected.getAccount(), actual.getAccount());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getBuyQuantity(), actual.getBuyQuantity());
        assertEquals(expected.getSellQuantity(), actual.getSellQuantity());
    }

    @Test
    public void createWithExceptionTest() throws Exception {
        expected.setAccount("");
        Exception exception = assertThrows(Exception.class, () -> tradeService.create(expected));
        assertTrue(exception.getMessage().contains("Account is empty or null."));

        expected.setAccount("Account");
        expected.setType("");
        exception = assertThrows(Exception.class, () -> tradeService.create(expected));
        assertTrue(exception.getMessage().contains("Type is empty or null."));

        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(-1.0);
        exception = assertThrows(Exception.class, () -> tradeService.create(expected));
        assertTrue(exception.getMessage().contains("Buy Quantity must not be null or negative."));

        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(1.0);
        expected.setSellQuantity(-11.0);
        exception = assertThrows(Exception.class, () -> tradeService.create(expected));
        assertTrue(exception.getMessage().contains("Sell Quantity must not be null or negative."));
    }

    @Test
    public void getAllTest() throws Exception {
        expected.setTradeId(null);
        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(5.0);
        expected.setSellQuantity(7.0);
        tradeService.create(expected);
        assertEquals(1, tradeService.getAll().size());
        assertEquals(1, tradeService.getAll().get(0).getTradeId());
    }

    @Test
    public void getByIdTest() throws Exception {
        expected.setTradeId(null);
        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(5.0);
        expected.setSellQuantity(7.0);
        tradeService.create(expected);

        assertEquals(1, tradeService.getById(1).getTradeId());
        assertEquals("Account", tradeService.getById(1).getAccount());
        assertEquals("Type", tradeService.getById(1).getType());
        assertEquals(5.0, tradeService.getById(1).getBuyQuantity());
        assertEquals(7.0, tradeService.getById(1).getSellQuantity());
    }

    @Test
    public void getByIdWithExceptionTest() {
        Exception exception = assertThrows(Exception.class, () -> tradeService.getById(25));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));
    }

    @Test
    public void updateTest() throws Exception {
        expected.setTradeId(null);
        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(5.0);
        expected.setSellQuantity(7.0);
        tradeService.create(expected);

        // Modify expected and call "Update" method.
        expected.setAccount("Account Updated");
        expected.setType("Type Updated");
        expected.setBuyQuantity(6.0);
        expected.setSellQuantity(8.0);
        Trade actual = tradeService.update(expected);

        assertEquals(1, actual.getTradeId());
        assertEquals("Account Updated", actual.getAccount());
        assertEquals("Type Updated", actual.getType());
        assertEquals(6.0, actual.getBuyQuantity());
        assertEquals(8.0, actual.getSellQuantity());
    }

    @Test
    public void updateWithExceptionTest() throws Exception {
        expected.setTradeId(25);
        Exception exception = assertThrows(Exception.class, () -> tradeService.update(expected));
        assertTrue(exception.getMessage().contains("Entity with id 25 does not exist."));

        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(5.0);
        expected.setSellQuantity(7.0);

        tradeService.create(expected);

        expected.setTradeId(1);
        expected.setAccount("");
        exception = assertThrows(Exception.class, () -> tradeService.update(expected));
        assertTrue(exception.getMessage().contains("Account is empty or null."));

        expected.setAccount("Account");
        expected.setType("");
        exception = assertThrows(Exception.class, () -> tradeService.update(expected));
        assertTrue(exception.getMessage().contains("Type is empty or null."));

        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(-1.0);
        exception = assertThrows(Exception.class, () -> tradeService.update(expected));
        assertTrue(exception.getMessage().contains("Buy Quantity must not be null or negative."));

        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(1.0);
        expected.setSellQuantity(-11.0);
        exception = assertThrows(Exception.class, () -> tradeService.update(expected));
        assertTrue(exception.getMessage().contains("Sell Quantity must not be null or negative."));
    }

    @Test
    public void deleteTest() throws Exception {
        expected.setTradeId(null);
        expected.setAccount("Account");
        expected.setType("Type");
        expected.setBuyQuantity(5.0);
        expected.setSellQuantity(7.0);
        tradeService.create(expected);
        // Check that the size of the list has changed.
        assertEquals(1, tradeService.getAll().size());
        // Check that the entity with id=1 exists.
        assertEquals(1, tradeService.getAll().get(0).getTradeId());
        // Delete the item with id=1.
        tradeService.delete(1);
        assertEquals(0, tradeService.getAll().size());
        Exception exception = assertThrows(Exception.class, () -> tradeService.delete(1));
        assertTrue(exception.getMessage().contains("Entity with id 1 does not exist."));
    }
}
