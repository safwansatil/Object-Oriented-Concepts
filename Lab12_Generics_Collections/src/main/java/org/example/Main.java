package org.example;

import org.example.model.Student;
import org.example.service.StudentService;
import org.example.service.StudentServiceImpl;
import org.example.store.DataStore;
import org.example.store.Store;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Main application class to demonstrate the Student Record Management System.
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Store<Student> store = new DataStore<>();

        store.add(new Student("230042101", "Alice Smith", 3.8, "Female"));
        store.add(new Student("S002", "Bob Johnson", 3.2, "Male"));
        store.add(new Student("S003", "Charlie Brown", 2.9, "Male"));
        store.add(new Student("S004", "Diana Prince", 4.0, "Female"));
        store.add(new Student("S006", "Fiona Gallagher", 3.1, "Female"));

        StudentService service = new StudentServiceImpl();
        List<Student> allStudents = store.getAll();

        printSection("Feature 1: Store Data & Feature 2: Get All");
        for (Student s : allStudents) {
            System.out.println(s);
        }

        printSection("Feature 3: Sort by GPA (Ascending)");
        List<Student> sortedStudents = service.sortByGpa(allStudents);
        for (Student s : sortedStudents) {
            System.out.println(s);
        }

        printSection("Feature 4: Search by Name ('diana prince')");
        Optional<Student> foundStudent = service.searchByName(allStudents, "diana prince");
        foundStudent.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Student not found.")
        );

        printSection("Feature 4: Search by Name ('Unknown User')");
        Optional<Student> missingStudent = service.searchByName(allStudents, "Unknown User");
        missingStudent.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Student not found.")
        );

        printSection("Feature 5: Group by Gender");
        Map<String, List<Student>> groupedByGender = service.groupByGender(allStudents);
        for (Map.Entry<String, List<Student>> entry : groupedByGender.entrySet()) {
            System.out.println("Gender: " + entry.getKey());
            for (Student s : entry.getValue()) {
                System.out.println("  - " + s);
            }
        }
    }

    /**
     * Prints a section title with a separator line.
     *
     * @param title the title to print
     */
    private static void printSection(String title) {
        System.out.println("\n--------------------------------------------------");
        System.out.println(title);
        System.out.println("--------------------------------------------------");
    }
}