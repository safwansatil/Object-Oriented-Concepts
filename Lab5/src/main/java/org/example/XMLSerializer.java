package org.example;
import java.lang.reflect.Field;
import java.util.List;

public class XMLSerializer {
    public static String parse(Object obj, int level) throws IllegalAccessException{
        Class <?> c = obj.getClass();
        String startTag = "<" + c.getName() + ">\n";
        String endTag = "</" + c.getName() + ">";

        // todo sth
        if(level!=0){
            startTag = obj.toString();
            endTag = obj.toString();
        }

        StringBuilder s = new StringBuilder(startTag);
        Field[] fields = c.getDeclaredFields(); //INFO: gets *all* fields declared in this class
        if(obj instanceof  List<?> objectList){
            StringBuilder b = new StringBuilder();
            for (Object parseObj : objectList){
                // TODO: sth
                b.append("<"+ fields[0].getName() +">");

                b.append(parse(parseObj, level+1));
                b.append("</"+fields[0].getName()+">");
            }

            return b.toString();
        }
        if((obj instanceof  String) || (obj instanceof  Float)){
            //TODO: return stringified obj

//            s.insert(level, obj);
//            s.append(obj);
            return s.toString();
        }

        for(Field f: fields){
            // todo: sth
            f.setAccessible(true);
            s.append("<"+f.getName()+">");
            Object val = f.get(obj);
            s.append(parse(val, level+1));
            s.append("</"+f.getName()+">");
        }
        s.append(endTag);
        return s.toString();
    }
}
