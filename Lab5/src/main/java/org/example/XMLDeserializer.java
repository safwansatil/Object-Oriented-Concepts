package org.example;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.StringReader;
import java.lang.reflect.Field;
import org.xml.sax.InputSource;

public class XMLDeserializer {
    public static <T> void deserializeAndPrint(String xml, Class <T> clazz) throws Exception {
        // Parse XML (DOM)
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                new InputSource(new StringReader(xml))
        );
        Element root = doc.getDocumentElement();
        System.out.println("Class: "+ clazz.getName());
        for(Field field: clazz.getDeclaredFields()){
            field.setAccessible(true);
            //todo: get field name

            String fieldname = field.getName();
            String val = null;
            if(root.getElementsByTagName(fieldname).getLength()>0){
                // todo get val
                val = root.getElementsByTagName(fieldname).item(0).getTextContent();
//                if(root.getElementsByTagName(fieldname).item(1).getTextContent()!=null){
//                    val = val + root.getElementsByTagName(fieldname).item(1).getTextContent();
//                }
            }
            System.out.println(
                    // todo print field name
                    fieldname + ": " + val
            );

        }
    }
}
