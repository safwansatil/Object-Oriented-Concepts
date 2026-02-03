package org.example.Person;

import jakarta.jws.WebService;
import java.util.List;

@WebService(
        endpointInterface = "org.example.Person.PersonInterface",
        targetNamespace = "http://person.example.org/"
)
public class PersonService implements PersonInterface {

    @Override
    public Person createPerson(Person person) {
        return PersonRepository.addPerson(person);
    }

    @Override
    public Person getPerson(int id) {
        return PersonRepository.getPerson(id);
    }

    @Override
    public List<Person> getAllPersons() {
        return PersonRepository.getAllPersons();
    }

    @Override
    public Person updatePerson(Person person) {
        return PersonRepository.updatePerson(person);
    }

    @Override
    public boolean deletePerson(int id) {
        return PersonRepository.deletePerson(id);
    }
}
