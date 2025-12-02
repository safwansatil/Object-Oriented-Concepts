package com.example;

import java.util.ArrayList;
import java.util.List;

public class Library {
    List<Book> books = new ArrayList<>();
    List<Person> persons = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
        book.isAvailable = true;
    }
    void addPerson(Person person) {
        persons.add(person);
    }

    void lendBook(Book book, Person person) {
        try {
            if(book.isAvailable) {
            person.addBorrowedBooks(book);
            book.isAvailable = false;
        } else {
            throw new BookNotAvailable("Book Unavailable");
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    void getBook(Book book, Person person){
        person.returnBook(book, this);
    }



    public List<Book> getBooks() {
        return books;
    }
    
}
