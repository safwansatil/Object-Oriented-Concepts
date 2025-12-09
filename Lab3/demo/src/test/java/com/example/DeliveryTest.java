package com.example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DeliveryTest {
    @Test
    void testProcessDelivery(){
        Customers c1 = new Customers();
        Items i1 = new Items("Breakfast");
        Items i2 = new Items("Lunch");
        List<Items> items = new java.util.ArrayList<>();
        items.add(i1);
        items.add(i2);
        Order order = new Order(c1, items);
        Delivery d = new Delivery(order);
        assertEquals(false, d.processDelivery());
    }
}
