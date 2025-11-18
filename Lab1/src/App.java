
public class App {
    public static void main(String[] args) throws Exception {
        Library library = new Library();
        Book book1 = new Book("1984", "George Orwell", "1234567890", 9.99);
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "0987654321", 7.99);
        library.addBook(book1);
        library.addBook(book2);
        Student student = new Student("Satil", "117@gmail.com");
        Faculty faculty = new Faculty("LNL ma'am", "lota@iut-dhaka.edu");
        library.addMember(student);
        library.addMember(faculty);
        student.borrowBook(book1);
        faculty.borrowBook(book2);
        library.printPersonDetails(student);
        library.printPersonDetails(faculty);

    }
}
