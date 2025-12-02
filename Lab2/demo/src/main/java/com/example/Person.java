package com.example;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {

    String type; // "Student" or "Faculty"
    List<Book> borrowedBooks = new ArrayList<>();
    String id;


    Person(String type, String id) {
        this.type = type;
        this.id = id;
    }
    Person(String id){
        this.id = id;
        this.type = "Student";
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    abstract void addBorrowedBooks(Book borrowBooks);
    void returnBook(Book book, Library library){
        if(borrowedBooks.contains(book)){
            borrowedBooks.remove(book);
            library.books.add(book);
            book.isAvailable = true;
        } else {
            System.out.println("Wrong book");
        }
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

}
