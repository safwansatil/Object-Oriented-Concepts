package org.example.service;

import org.example.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Defines the operations available for the student service.
 */
public interface StudentService {

    /**
     * Sorts a list of students by GPA in ascending order.
     *
     * @param students the list of students to sort
     * @return a new list of students sorted by GPA
     */
    List<Student> sortByGpa(List<Student> students);

    /**
     * Searches for a student by name (case-insensitive).
     *
     * @param students the list of students to search
     * @param name     the name to search for
     * @return an Optional containing the found student, or empty if not found
     */
    Optional<Student> searchByName(List<Student> students, String name);

    /**
     * Groups a list of students by gender.
     *
     * @param students the list of students to group
     * @return a map where the keys are normalized genders and the values are lists of students
     */
    Map<String, List<Student>> groupByGender(List<Student> students);
}
