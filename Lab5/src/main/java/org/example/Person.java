package org.example;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private List<Course> courses;
    public Person(String name){
        this.name = name;
        courses = new ArrayList<Course>();
    }
    public void addCourse(Course course){
        courses.add(course);
    }
}
