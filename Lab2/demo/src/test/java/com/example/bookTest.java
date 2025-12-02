package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class bookTest {
    @Test
    void testGetterAndSetter() {
        Book b = new Book();
        b.setTitle("1984");
        b.setAuthor("Orwell");
        b.setIsbn("1234567890");

        assertEquals("1984", b.getTitle());
        assertEquals("Orwell", b.getAuthor());
        assertEquals("1234567890", b.getIsbn());
    }
}


