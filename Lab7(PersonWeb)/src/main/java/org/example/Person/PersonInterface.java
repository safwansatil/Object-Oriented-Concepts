package org.example.Person;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService(targetNamespace = "http://person.example.org/")
public interface PersonInterface {

    @WebMethod
    Person createPerson(@WebParam(name = "person") Person person);

    @WebMethod
    Person getPerson(@WebParam(name = "id") int id);

    @WebMethod
    List<Person> getAllPersons();

    @WebMethod
    Person updatePerson(@WebParam(name = "person") Person person);

    @WebMethod
    boolean deletePerson(@WebParam(name = "id") int id);
}
