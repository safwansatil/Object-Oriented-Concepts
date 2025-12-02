package com.example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LibraryTest {
    @Test
    void testGetBooks(){
        Library l = new Library();
        Book b1 = new Book();
        Book b2 = new Book();

        l.addBook(b1);
        l.addBook(b2);

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(b1);
        expectedBooks.add(b2);

        assertEquals(expectedBooks, l.getBooks());
    }
}
