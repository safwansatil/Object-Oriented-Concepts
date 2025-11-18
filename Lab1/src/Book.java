public class Book {
    String title;
    String author;
    String isbn;
    Number price;

    public Book() {
    }
    public Book(String title, String author, String isbn, Number price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }
    void displayInfo(){
        System.out.println("Book Details: ");
        System.out.println("Title: "+ this.getTitle());
        System.out.println("Author: " + this.getAuthor());
        System.out.println("ISBN: " + this.getIsbn());
        System.out.println("Price: " + this.getPrice());
    }

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

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }
}
