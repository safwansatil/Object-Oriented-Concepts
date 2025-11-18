
import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    String name;
    String email;
    List<Book> borrowedBooks = new ArrayList<>();

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    boolean borrowBook(Book book){
        return this.borrowedBooks.add(book);
    }
    abstract void displayDetails();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    
}
