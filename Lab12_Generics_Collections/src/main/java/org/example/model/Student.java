package org.example.model;

import java.util.Objects;

/**
 * Represents a student with an ID, name, GPA, and gender.
 * Instances of this class are immutable.
 */
public final class Student implements Comparable<Student> {

    private final String studentId;
    private final String name;
    private final double gpa;
    private final String gender;

    /**
     * Constructs a new Student instance.
     *
     * @param studentId the student's unique identifier
     * @param name      the student's full name
     * @param gpa       the student's grade point average
     * @param gender    the student's gender
     * @throws IllegalArgumentException if the GPA is not between 0.0 and 4.0, or if studentId or name is null or blank
     */
    public Student(String studentId, String name, double gpa, String gender) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or blank");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
        this.gender = gender;
    }

    /**
     * Retrieves the student's unique identifier.
     *
     * @return the student ID
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Retrieves the student's full name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the student's grade point average.
     *
     * @return the GPA
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Retrieves the student's gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Compares this student with another student based on GPA in ascending order.
     *
     * @param other the other student to compare to
     * @return a negative integer, zero, or a positive integer as this student's GPA is less than, equal to, or greater than the specified student's GPA
     */
    @Override
    public int compareTo(Student other) {
        return Double.compare(this.gpa, other.gpa);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Equality is based solely on the student ID.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student student = (Student) obj;
        return Objects.equals(studentId, student.studentId);
    }

    /**
     * Returns a hash code value for the object based solely on the student ID.
     *
     * @return a hash code value for this student
     */
    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    /**
     * Returns a string representation of the student.
     *
     * @return a string containing the student's details
     */
    @Override
    public String toString() {
        return String.format("Student(ID='%s', Name='%s', GPA=%.2f, Gender='%s')", studentId, name, gpa, gender);
    }
}
