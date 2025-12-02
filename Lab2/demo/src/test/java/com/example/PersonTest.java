package com.example;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PersonTest {
    @Test
    void testAddBorrowedBooksStudent() {
        Student p = new Student("117");
        Book b1 = new Book();
        Book b2 = new Book();
        Book b3 = new Book();
        
        try {
            p.addBorrowedBooks(b1);
            p.addBorrowedBooks(b2);
            p.addBorrowedBooks(b3);
        } catch (Exception e) {
            System.out.println("Error");
        }

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(b1);
        expectedBooks.add(b2);
        expectedBooks.add(b3);
        assertEquals("Student", b1.whoGetsBook);
        assertEquals(expectedBooks, p.getBorrowedBooks());

    }

    @Test
    void testAddBorrowedBooksFaculty() {
        Faculty p = new Faculty("999");
        Book b1 = new Book();
        Book b2 = new Book();
        Book b3 = new Book();
        
            p.addBorrowedBooks(b1);
            p.addBorrowedBooks(b2);
            p.addBorrowedBooks(b3);
        

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(b1);
        expectedBooks.add(b2);
        expectedBooks.add(b3);

        assertEquals("Faculty", b1.whoGetsBook);
        assertEquals(expectedBooks, p.getBorrowedBooks());
    }
    @Test
    void testGetId(){
        Student s = new Student("117");
        assertEquals("117", s.getId());
    }
    @Test
    void testType(){
        Student s = new Student("117");
        Faculty f = new Faculty("999");
        assertEquals("Student", s.getType());
        assertEquals("Faculty", f.getType());
    }
}
