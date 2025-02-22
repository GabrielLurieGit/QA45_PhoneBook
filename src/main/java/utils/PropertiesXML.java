package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesXML {
    public static void main(String[] args) {
        setProperty("name", "Alex", true, "src/main/resources/properties_xml/fil1.xml");
        setProperty("lastName", "Med", false, "src/main/resources/properties_xml/fil1.xml");
        setProperty("email", "alex@mail.com", false, "src/main/resources/properties_xml/fil1.xml");
        setProperty("password", "Pass123!", false, "src/main/resources/properties_xml/fil1.xml");
        System.out.println(getProperties("name", "src/main/resources/properties_xml/fil1.xml"));
    }

    public static void setProperty(String key, String value, boolean clearFile, String path) {
        Properties properties = new Properties();
        if (!clearFile) {
            try (FileInputStream fileInputStream = new FileInputStream(path)) {
                properties.loadFromXML(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties.setProperty(key, value);
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            properties.storeToXML(fileOutputStream, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String getProperties(String key, String path){
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(path)){
            properties.loadFromXML(fis);
            return properties.getProperty(key);
        }catch (IOException e){e.printStackTrace();
            return null;}
    }
}
