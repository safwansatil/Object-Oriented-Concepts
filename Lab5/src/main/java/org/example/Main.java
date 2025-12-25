package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
            Course c1 = new Course("math", "CSE4301", 4);
            Course c2 = new Course("math2", "CSE4401", 3);
            Person p = new Person("tumi");
            p.addCourse(c1);
            p.addCourse(c2);

            String parsed = XMLSerializer.parse(p, 0);
            System.out.println(parsed);

            XMLDeserializer.deserializeAndPrint(parsed,p.getClass());
    }
}