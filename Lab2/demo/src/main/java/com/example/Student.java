package com.example;

public class Student extends Person {

    public Student(String id) {
        super("Student", id);
    }
    @Override
    public void addBorrowedBooks(Book borrowBooks){
        try{
            if(this.borrowedBooks.size()+ 1 > 3) {
                throw new BorrowLimitExceed("Students borrow 3 books");
        }
        this.borrowedBooks.add(borrowBooks);
        borrowBooks.setWhoGetsBook("Student");
        } catch(BorrowLimitExceed e){
            System.out.println("Error");
        }
        
    }
    
    
}
