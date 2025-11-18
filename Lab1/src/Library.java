
import java.util.ArrayList;
import java.util.List;

public class Library {
    List<Person> members;
    List<Book> books;
    static int totalBooksIssued = 0;
    public Library(){
        this.members = new ArrayList<>();
        this.books = new ArrayList<>();
    }
    static void borrowBook(Person person, Book book){
        if(person.borrowBook(book)){
            System.out.println(person.getName() + " successfully borrowed " + book.getTitle());
            totalBooksIssued++;
        }else{
            System.out.println("Failed to borrow book.");
        }
    }
    void addBook(Book book){
        this.books.add(book);
    }
    void addMember(Person person){
        this.members.add(person);
    }
    void printPersonDetails(Student student){
        student.displayDetails();
    }
    void printPersonDetails(Faculty faculty){
        faculty.displayDetails();
    }
}
