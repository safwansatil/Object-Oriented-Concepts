package org.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.model.Student;

/**
 * Implementation of the StudentService providing operations for student data.
 */
public class StudentServiceImpl implements StudentService {

    /**
     * Sorts a list of students by GPA in ascending order.
     * Does not mutate the input list.
     *
     * @param students the list of students to sort
     * @return a new list of students sorted by GPA
     */
    @Override
    public List<Student> sortByGpa(List<Student> students) {
        List<Student> sortedList = new ArrayList<>(students);
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * Searches for a student by name (case-insensitive).
     *
     * @param students the list of students to search
     * @param name     the name to search for
     * @return an Optional containing the found student, or empty if not found
     */
    @Override
    public Optional<Student>
     searchByName(List<Student> students, String name) {
        if (name == null) {
            return Optional.empty();
        }
        return students.stream()
                .filter(student -> student.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Groups a list of students by gender.
     * The keys are normalized to lowercase.
     *
     * @param students the list of students to group
     * @return a map where the keys are normalized genders and the values are lists of students
     */
    @Override
    public Map<String, List<Student>> groupByGender(List<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(
                        student -> student.getGender().trim().toLowerCase()
                ));
    }
}
