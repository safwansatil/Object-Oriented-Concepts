package org.example.Person;

import org.example.SimpleSoapClient;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonSoapClient {
    private static final String ENDPOINT = "http://localhost:8081/person";
    private static final String NAMESPACE = "http://person.example.org/";

    public static List<Person> getAllPersons() {
        String body = "<per:getAllPersons xmlns:per=\"" + NAMESPACE + "\"/>";
        String envelope = SimpleSoapClient.createSoapEnvelope(body);
        String response = SimpleSoapClient.sendSoapRequest(ENDPOINT, NAMESPACE + "getAllPersons", envelope);
        return parsePersons(response);
    }

    public static Person createPerson(String name, String email, String role) {
        String body = "<per:createPerson xmlns:per=\"" + NAMESPACE + "\">\n" +
                "  <person>\n" +
                "    <email>" + email + "</email>\n" +
                "    <name>" + name + "</name>\n" +
                "    <role>" + role + "</role>\n" +
                "  </person>\n" +
                "</per:createPerson>";
        String envelope = SimpleSoapClient.createSoapEnvelope(body);
        String response = SimpleSoapClient.sendSoapRequest(ENDPOINT, NAMESPACE + "createPerson", envelope);
        List<Person> list = parsePersons(response);
        return list.isEmpty() ? null : list.get(0);
    }

    public static Person updatePerson(int id, String name, String email, String role) {
        String body = "<per:updatePerson xmlns:per=\"" + NAMESPACE + "\">\n" +
                "  <person>\n" +
                "    <id>" + id + "</id>\n" +
                "    <email>" + email + "</email>\n" +
                "    <name>" + name + "</name>\n" +
                "    <role>" + role + "</role>\n" +
                "  </person>\n" +
                "</per:updatePerson>";
        String envelope = SimpleSoapClient.createSoapEnvelope(body);
        String response = SimpleSoapClient.sendSoapRequest(ENDPOINT, NAMESPACE + "updatePerson", envelope);
        List<Person> list = parsePersons(response);
        return list.isEmpty() ? null : list.get(0);
    }

    public static boolean deletePerson(int id) {
        String body = "<per:deletePerson xmlns:per=\"" + NAMESPACE + "\">\n" +
                "  <id>" + id + "</id>\n" +
                "</per:deletePerson>";
        String envelope = SimpleSoapClient.createSoapEnvelope(body);
        String response = SimpleSoapClient.sendSoapRequest(ENDPOINT, NAMESPACE + "deletePerson", envelope);
        return response.contains("<return>true</return>");
    }

    private static List<Person> parsePersons(String xml) {
        List<Person> persons = new ArrayList<>();
        // Simple manual parsing using regex for demonstration as per 'manual implementation' spirit
        // Updated regex to handle optional namespace prefixes (e.g., <ns2:return>)
        Pattern pattern = Pattern.compile("<(?:\\w+:)?return>(.*?)</(?:\\w+:)?return>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(xml);
        while (matcher.find()) {
            String personXml = matcher.group(1);
            Person p = new Person();
            p.setId(getValueORZero(personXml, "id"));
            p.setName(getValue(personXml, "name"));
            p.setEmail(getValue(personXml, "email"));
            p.setRole(getValue(personXml, "role"));
            persons.add(p);
        }
        return persons;
    }

    private static String getValue(String xml, String tag) {
        Pattern p = Pattern.compile("<(?:\\w+:)?" + tag + ">(.*?)</(?:\\w+:)?" + tag + ">");
        Matcher m = p.matcher(xml);
        return m.find() ? m.group(1).trim() : "";
    }

    private static int getValueORZero(String xml, String tag) {
        String val = getValue(xml, tag);
        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            return 0;
        }
    }
}
