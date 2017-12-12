package PO53.Ponomarenko.wdad.data.managers;

import javax.xml.transform.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Properties;
import PO53.Ponomarenko.wdad.utils.PreferencesConstantManager;
import java.util.Enumeration;

public class PreferencesManager {
    private static volatile PreferencesManager instance; //volatile для синхронизации
    private final File XML_FILE;
    private final Document XML_DOCUMENT;
    private static final String XML_PATH = "src//PO53/Ponomarenko/wdad/learn/xml/appconfig.xml";

    private PreferencesManager() throws Exception {
        XML_FILE = new File(XML_PATH);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        XML_DOCUMENT = db.parse(XML_FILE);

    }

    private void writeXml() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(XML_DOCUMENT), new StreamResult(XML_FILE));
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        }
    }

    private String trimKey(String key) {
        String[] splitedKey = key.split("\\.");
        return splitedKey[splitedKey.length - 1];
    }

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
            }
        return instance;
    }

    @Deprecated
    public String getCreateregistry() {
        return XML_DOCUMENT.getElementsByTagName("createregistry").item(0).getTextContent();
    }
    @Deprecated
    public void setCreateregistry(String createregistry) {
        XML_DOCUMENT.getElementsByTagName("createregistry").item(0).setTextContent(createregistry);
    }
    @Deprecated
    public String getRegistryaddress() {
        return XML_DOCUMENT.getElementsByTagName("registryaddress").item(0).getTextContent();
    }
    @Deprecated
    public void setRegistryaddress(String registryaddress) {
        XML_DOCUMENT.getElementsByTagName("registryaddress").item(0).setTextContent(registryaddress);
    }
    @Deprecated
    public String getRegistryport() {return XML_DOCUMENT.getElementsByTagName("registryport").item(0).getTextContent();
    }
    @Deprecated
    public void setRegistryport(String registryport) {
        XML_DOCUMENT.getElementsByTagName("registryport").item(0).setTextContent(registryport);
    }
    @Deprecated
    public String getPolicypath() {
        return XML_DOCUMENT.getElementsByTagName("policypath").item(0).getTextContent();
    }
    @Deprecated
    public void setPolicypath(String policypath) {
        XML_DOCUMENT.getElementsByTagName("policypath").item(0).setTextContent(policypath);
    }
    @Deprecated
    public String getUsecodebaseonly() {return XML_DOCUMENT.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }
    @Deprecated
    public void setUsecodebaseonly(String usecodebaseonly) {
        XML_DOCUMENT.getElementsByTagName("usecodebaseonly").item(0).setTextContent(usecodebaseonly);
    }
    @Deprecated
    public String getClassprovider() {return XML_DOCUMENT.getElementsByTagName("classprovider").item(0).getTextContent();
    }
    @Deprecated
    public void setClassprovider(String classprovider) {
        XML_DOCUMENT.getElementsByTagName("classprovider").item(0).setTextContent(classprovider);
    }

    //новые методы
    public String getProperty(String key) {
        return XML_DOCUMENT.getElementsByTagName(trimKey(key)).item(0).getTextContent();
    }
    public void setProperty(String key, String value) {
        XML_DOCUMENT.getElementsByTagName(trimKey(key)).item(0).setTextContent(value);
    }
    //TODO написать метод setProperties
    public void setProperties(Properties prop) {
        Enumeration e = prop.elements();
        Enumeration keys = prop.keys();
        while(e.hasMoreElements())
        {
            XML_DOCUMENT.getElementsByTagName(trimKey((String) keys.nextElement())).item(0).setTextContent((String) e.nextElement());
        }
    }

    public Properties getProperties(){
        Properties prop = new Properties();

        prop.put(PreferencesConstantManager.CREATEREGISTRY,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.CREATEREGISTRY)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.CREATEREGISTRY,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.CREATEREGISTRY)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.CLASSPROVIDER,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.CLASSPROVIDER)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.POLICYPATH,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.POLICYPATH)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.REGISTRYADDRESS,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.REGISTRYADDRESS)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.USECODEBASEONLY,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.USECODEBASEONLY)).item(0).getTextContent());
        prop.put(PreferencesConstantManager.REGISTRYPORT,
                XML_DOCUMENT.getElementsByTagName(trimKey(PreferencesConstantManager.REGISTRYPORT)).item(0).getTextContent());

        return prop;
    }

    public void addBindedObject(String name, String className){
        Element e = (Element) XML_DOCUMENT.createElement("bindedobject");
        e.setAttribute("name", name);
        e.setAttribute("class", className);
        XML_DOCUMENT.getElementsByTagName("server").item(0).appendChild(e);
    }
    public void removeBindedObject(String name){
        NodeList nodeList = XML_DOCUMENT.getElementsByTagName("bindedobject");
        for (int i=0; i<nodeList.getLength(); i++)
        {
            if (nodeList.item(i).getAttributes().getNamedItem("name").equals(name))
            {
                XML_DOCUMENT.getElementsByTagName("server").item(0).removeChild(nodeList.item(i));
            }
        }
    }

}
