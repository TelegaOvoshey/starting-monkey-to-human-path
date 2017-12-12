package PO53.Ponomarenko.wdad.learn.xml;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.File;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlTask {

    private final File XMLfile;
    private final Document XMLdocument;

    public XmlTask(String path) throws Exception {
        XMLfile = new File(path);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        XMLdocument = db.parse(XMLfile);
        checkTotalcost();
        XMLdocument.normalize(); //удаляет пустые ноды, объединяет ближние текстовые ноды и трет всякий мусор
    }

    private void writeXml() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(XMLdocument), new StreamResult(XMLfile));
            Transformer t = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(XMLdocument);
            StreamResult result = new StreamResult(XMLfile);
            t.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        }
    }

    //проверка totalcost-а //TODO: запомнить элемент в цикле, изменение вне цикла
    private void checkTotalcost() {
        int totalCost = 0;

        NodeList ordersList = XMLdocument.getElementsByTagName("order");
        NodeList elementsList;
        Node element;
        boolean isTotalcost = false;

        for (int i = 0; i < ordersList.getLength(); i++) {
            elementsList = ordersList.item(i).getChildNodes();

            for (int j = 0; j < elementsList.getLength(); j++) {
                element = elementsList.item(j);
                if (element.getNodeName().equals("item")) {
                    totalCost += Integer.parseInt(element.getAttributes().getNamedItem("cost").getTextContent());
                }
                if (element.getNodeName().equals("totalcost")) {
                    isTotalcost = true;
                    if (!element.getTextContent().equals(totalCost)) element.setTextContent(String.valueOf(totalCost));
                }
            }

            if (!isTotalcost) {
                element = XMLdocument.createElement("totalcost");
                element.setTextContent(String.valueOf(totalCost));
                ordersList.item(i).appendChild(element);
            }

            isTotalcost = false;
            totalCost = 0;
        }
        writeXml();
    }

    //Методы по заданию:
    //возвращающий суммарную выручку заданного официанта в заданный день
    public int earningsTotal(String officiantSecondName, Calendar calendar) {
        int officiantTotalRevenue = 0;
        Calendar parsedCalendar = new GregorianCalendar();
        NodeList datesList = XMLdocument.getElementsByTagName("date"),
                orders,
                elements;
        Node date;
        boolean isFound;

        for (int i = 0; i < datesList.getLength(); i++) {
            date = datesList.item(i);
            parsedCalendar.clear();
            parsedCalendar.set(
                    Integer.parseInt(date.getAttributes().getNamedItem("year").getTextContent()),
                    Integer.parseInt(date.getAttributes().getNamedItem("month").getTextContent()),
                    Integer.parseInt(date.getAttributes().getNamedItem("day").getTextContent())
            );

            if (calendar.equals(parsedCalendar)) {
                orders = date.getChildNodes();//TODO: каст к элементу

                for (int j = 0; j < orders.getLength(); j++) {
                    isFound = false;
                    elements = orders.item(j).getChildNodes();

                    for (int k = 0; k < elements.getLength(); k++) {
                        if (elements.item(k).getNodeName().equals("officiant") &&
                                elements.item(k).getAttributes().getNamedItem("secondname").getTextContent().equals(officiantSecondName))
                            isFound = true;
                        if (isFound && elements.item(k).getNodeName().equals("totalcost"))
                            officiantTotalRevenue += Integer.parseInt(elements.item(k).getTextContent());
                    }

                }
            }
        }
        return officiantTotalRevenue;
    }

    // удаляющий информацию по заданному дню.
    public void removeDay(Calendar calendar) {
        Node root = XMLdocument.getDocumentElement();
        NodeList datesList = XMLdocument.getElementsByTagName("date");
        Node date;
        Calendar Calendar;
        for (int i = 0; i < datesList.getLength(); i++) {
            date = datesList.item(i);
            Calendar = new GregorianCalendar(Integer.parseInt(date.getAttributes().getNamedItem("year").getTextContent()),
                    Integer.parseInt(date.getAttributes().getNamedItem("month").getTextContent()),
                    Integer.parseInt(date.getAttributes().getNamedItem("day").getTextContent()));
            if (Calendar.equals(calendar)) {
                root.removeChild(date);

            }
            LocalDate lDate = LocalDate.of(2017, 10, 1);
            lDate.getDayOfMonth();
            lDate.getMonthValue();
            lDate.getYear();//легче
        }
        writeXml();
    }

    //заменяющий имя и фамилию официанта во всех днях и записывающий результат в этот же xml-файл
    public void changeOfficiantName(String oldFirstName, String oldSecondName, String newFirstName, String newSecondName) {
        Element officiant;
        NodeList officiantList = XMLdocument.getElementsByTagName("officiant");
        for (int i=0; i<officiantList.getLength(); i++) {
            officiant = (Element) officiantList.item(i);
            if (officiant.getAttribute("secondname").equals(oldSecondName) &&
                    officiant.getAttribute("firstname").equals(oldFirstName)) {
                officiant.setAttribute("secondname", newSecondName);
                officiant.setAttribute("firstname", newFirstName);
            }
        }
        writeXml();
    }
}