package com.example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ItemsTest {
    @Test
    void testCalculateTotal(){
        Items items = new Items();
        items.addItem("i1");
        items.addItem("i2");
        assertEquals(20.0, items.calculateTotal());
    }
    @Test
    void testGetItemList(){
        Items items = new Items();
        items.addItem("i1");
        items.addItem("i2");
        List<String> expected = new java.util.ArrayList<>();
        expected.add("i1");
        expected.add("i2");
        assertEquals(expected, items.getItemList());
    }
}
