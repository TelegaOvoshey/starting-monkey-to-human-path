package PO53.Ponomarenko.wdad.learn.xml;

import javax.xml.transform.TransformerException;
import java.util.GregorianCalendar;

public class TestXmlTask {
    public static void main(String[] args) {
        try {
            XmlTask xml = new XmlTask("src//PO53/Ponomarenko/wdad/learn/xml/validXML1.xml");
            System.out.println("TotalCost of Ivanov (9/17/2000): " + xml.earningsTotal("Ivanov", new GregorianCalendar(2000, 9, 17))+ "$");
           //xml.removeDay(new GregorianCalendar(2017, 12, 12));
           xml.changeOfficiantName("Ivan","Ivanov","Piotr","Ivanov");
           //xml.changeOfficiantName("Piotr","Ivanov","Ivan","Ivanov");
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        }
        catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}