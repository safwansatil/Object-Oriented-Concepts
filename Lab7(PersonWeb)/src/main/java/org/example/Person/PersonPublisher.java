package org.example.Person;

import jakarta.xml.ws.Endpoint;

public class PersonPublisher {
    public static void main(String[] args) {
        String url = "http://localhost:8081/person";
        Endpoint.publish(url, new PersonService());
        System.out.println("Person SOAP Web Service is running at: " + url);
    }
    
    public static void publish() {
        String url = "http://localhost:8081/person";
        Endpoint.publish(url, new PersonService());
        System.out.println("Person SOAP Web Service is running at: " + url);
    }
}
