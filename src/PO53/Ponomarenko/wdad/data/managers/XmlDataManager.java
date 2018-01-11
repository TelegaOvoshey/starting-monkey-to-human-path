package PO53.Ponomarenko.wdad.learn.rmi.server;

import java.util.Date;
import PO53.Ponomarenko.wdad.utils.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

public interface XmlDataManager  extends Remote {
    public int earningsTotal(Officiant officiant, Date date) throws Exception, RemoteException;
    public void removeDay(Date date) throws Exception, RemoteException;
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws Exception, RemoteException;
    public List<Order> getOrders(Date date) throws Exception;
    public Date lastOfficiantWorkDate(Officiant officiant) throws NoSuchOfficiantException, ParseException, RemoteException;
}