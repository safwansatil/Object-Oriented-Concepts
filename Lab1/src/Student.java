public class Student extends Person {

    public Student(String name, String email) {
        super(name, email);
    }
    void displayDetails(){
        System.out.println("Student Details: ");
        System.out.println("Name: "+ this.getName());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Books Borrowed: ");
        for (Book book : this.getBorrowedBooks()) {
            book.displayInfo();
        }
    }
    
}
