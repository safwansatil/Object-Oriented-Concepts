package com.example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    public void testProcessOrder() {
        Customers c1 = new Customers();
        Items i1 = new Items("Breakfast");
        Items i2 = new Items("Lunch");
        List<Items> items = new java.util.ArrayList<>();
        items.add(i1);
        items.add(i2);
        Order order = new Order(c1, items);
        assertEquals(false, order.processOrder());
    }
}
