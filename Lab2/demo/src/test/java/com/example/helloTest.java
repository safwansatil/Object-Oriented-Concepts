package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class helloTest {
    @Test
    void testAdd() {
        hello h = new hello();
        int result = h.add(2, 3);
        assertEquals(5, result);
    }
}
