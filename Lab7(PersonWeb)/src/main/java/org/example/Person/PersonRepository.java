package org.example.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PersonRepository {
    private static final Map<Integer, Person> persons = new ConcurrentHashMap<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    static {
        // Sample data
        addPerson(new Person(4, "LNL", "hello@iut-dhaka.edu", "Professor"));
        addPerson(new Person(2, "satil", "satil@iut-dhaka.edu", "Student"));
        addPerson(new Person(3, "chocolate milk", "aarong@brac.com", "Staff"));
        addPerson(new Person(1, "safwan", "hi@iut-dhaka.edu", "Student"));
    }

    public static Person addPerson(Person person) {
        if (person.getId() <= 0) {
            person.setId(idGenerator.getAndIncrement());
        }
        persons.put(person.getId(), person);
        return person;
    }

    public static Person getPerson(int id) {
        return persons.get(id);
    }

    public static List<Person> getAllPersons() {
        return new ArrayList<>(persons.values());
    }

    public static Person updatePerson(Person person) {
        if (persons.containsKey(person.getId())) {
            persons.put(person.getId(), person);
            return person;
        }
        return null;
    }

    public static boolean deletePerson(int id) {
        return persons.remove(id) != null;
    }
}
