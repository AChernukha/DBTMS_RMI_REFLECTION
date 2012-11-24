package org.dblite.rmi;

import org.dblite.database.Database;
import org.dblite.database.Table;
import org.dblite.field.Cell;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DBTMSImpl implements DBTMS{
    private Map<String, Database> dbs = new ConcurrentHashMap<String, Database>();

    public ArrayList<String> getListTables(String dbName) {
        Database db = dbs.get(dbName);
        return db.getListTables();
    }

    public synchronized void setDB(String name, String path) throws IOException {
        if (dbs.get(name) == null) {
            Database db = new Database(name, path);
            db.loadFromStorage();
            dbs.put(name, db);
        }
    }


    public synchronized Table getTable(String dbName, String name) throws IOException {
        Database db = dbs.get(dbName);
        return db.getTable(name);  // returns copy
    }


    public synchronized void createTable(String dbName, String name) throws IOException {
        Database db = dbs.get(dbName);
        if (db.hasTable(name)) return;
        db.addTable(new Table(name));
    }


    public synchronized void removeTable(String dbName, String name) throws IOException {
        Database db = dbs.get(dbName);
        db.removeTable(name);

    }


    public synchronized void removeRow(String dbName, String tName, int row) {
        Database db = dbs.get(dbName);
        try {
            db.removeRow(tName, row);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public Table getTablesSub(String dbName, String tName1, String tName2) throws IOException {
        Database db = dbs.get(dbName);
        return db.getTablesSub(tName1, tName2);

    }


    public Table getIntersectionTables(String dbName, String tName1, String tName2) throws IOException {
        Database db = dbs.get(dbName);
        return db.getIntersectionTables(tName1, tName2);

    }


    public synchronized void addColumn(String dbName, String table, String name, Class<? extends Cell> type) throws IOException {
        Database db = dbs.get(dbName);
        db.getTable(table).addColumn(name, type);

    }


    public synchronized void addCleanRow(String dbName, String tableName) throws IOException {
        Database db = dbs.get(dbName);
        db.getTable(tableName).addCleanRow();
    }


    public synchronized void setRawValAt(String dbName, String tableName, int row, int col, String val) throws IOException, ParseException {
        Database db = dbs.get(dbName);
        db.getTable(tableName).addValue(row, col, val);
    }


    public synchronized void close(String dbName, String dbPath) throws IOException {
        Database db = dbs.get(dbName);
        db.setPath(dbPath);
        db.saveToStorage();
    }


}
