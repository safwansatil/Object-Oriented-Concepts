package com.example;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Library library = new Library();
        Person student = new Student("117");
        Person faculty = new Faculty("999");
        Book book1 = new Book();
        Book book2 = new Book();
        book1.setTitle("Hi");
        book2.setTitle("hello");
    }
}
