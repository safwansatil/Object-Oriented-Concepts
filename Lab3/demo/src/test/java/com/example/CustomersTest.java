package com.example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CustomersTest {
    @Test
    public void testGetPastOrders() {
        Order o1 = new Order();
        Order o2 = new Order();
        Order o3 = new Order();
        Customers c = new Customers();
        c.addOrder(o1);
        c.addOrder(o3);
        c.addOrder(o2);

        List<Order> expected = new java.util.ArrayList<>();
        expected.add(o1);
        expected.add(o3);
        expected.add(o2);

        assertEquals(expected, c.getPastOrders());
    }
}
