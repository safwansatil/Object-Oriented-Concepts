package com.example;

public class Book {
    String title;
    String author;
    String isbn;
    boolean isAvailable = false;
    String whoGetsBook;

    public String getWhoGetsBook() {
        return whoGetsBook;
    }


    public Book() {
    }

    
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }
    


    // getter and setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setWhoGetsBook(String whoGetsBook) {
        this.whoGetsBook = whoGetsBook;
    }
}
