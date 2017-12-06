package PO53.Ponomarenko.wdad.data.managers;

import javax.xml.transform.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

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

    public static PreferencesManager getInstance() throws Exception {
        if (instance == null)
            synchronized (PreferencesManager.class) {
                if (instance == null)
                    instance = new PreferencesManager();
            }
        return instance;
    }

    public String getCreateregistry() {
        return XML_DOCUMENT.getElementsByTagName("createregistry").item(0).getTextContent();
    }
    public void setCreateregistry(String createregistry) {
        XML_DOCUMENT.getElementsByTagName("createregistry").item(0).setTextContent(createregistry);
    }

    public String getRegistryaddress() {
        return XML_DOCUMENT.getElementsByTagName("registryaddress").item(0).getTextContent();
    }
    public void setRegistryaddress(String registryaddress) {
        XML_DOCUMENT.getElementsByTagName("registryaddress").item(0).setTextContent(registryaddress);
    }

    public String getRegistryport() {return XML_DOCUMENT.getElementsByTagName("registryport").item(0).getTextContent();
    }
    public void setRegistryport(String registryport) {
        XML_DOCUMENT.getElementsByTagName("registryport").item(0).setTextContent(registryport);
    }

    public String getPolicypath() {
        return XML_DOCUMENT.getElementsByTagName("policypath").item(0).getTextContent();
    }
    public void setPolicypath(String policypath) {
        XML_DOCUMENT.getElementsByTagName("policypath").item(0).setTextContent(policypath);
    }

    public String getUsecodebaseonly() {return XML_DOCUMENT.getElementsByTagName("usecodebaseonly").item(0).getTextContent();
    }
    public void setUsecodebaseonly(String usecodebaseonly) {
        XML_DOCUMENT.getElementsByTagName("usecodebaseonly").item(0).setTextContent(usecodebaseonly);
    }

    public String getClassprovider() {return XML_DOCUMENT.getElementsByTagName("classprovider").item(0).getTextContent();
    }
    public void setClassprovider(String classprovider) {
        XML_DOCUMENT.getElementsByTagName("classprovider").item(0).setTextContent(classprovider);
    }
}
