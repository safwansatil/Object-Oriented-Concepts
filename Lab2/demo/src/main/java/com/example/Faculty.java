package com.example;

public class Faculty extends Person {

    public Faculty(String id) {
        super("Faculty", id);
    }
    @Override
    public void addBorrowedBooks(Book borrowBooks){
        try {
            if(borrowedBooks.size() + 1 > 10) {
                throw new BorrowLimitExceed("Faculty borrow 10 books");
            }
            this.borrowedBooks.add(borrowBooks);
            borrowBooks.setWhoGetsBook("Faculty");
        } catch(BorrowLimitExceed e){
            System.out.println("Error");
        }
        
    }
    
}
