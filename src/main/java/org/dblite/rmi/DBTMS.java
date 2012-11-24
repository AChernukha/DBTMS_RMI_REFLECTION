package org.dblite.rmi;

import org.dblite.database.Table;
import org.dblite.field.Cell;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public interface DBTMS extends java.rmi.Remote {
    
    public void setDB(String name, String path) throws IOException;
    public ArrayList<String> getListTables(String dbName) throws IOException;
    public Table getTable(String dbName, String name) throws IOException;
    public void createTable(String dbName, String name) throws IOException;
    public void addColumn(String dbName, String table, String name, Class<? extends Cell> type) throws IOException;
    public void addCleanRow(String dbName, String tName) throws IOException;
    public void setRawValAt(String dbName, String tableName, int row, int col, String val) throws IOException, ParseException;
    public void removeTable(String dbName, String name)  throws IOException;
    public void removeRow(String dbName, String tName, int row) throws IOException;
    public Table getTablesSub(String dbName, String tName1, String tName2) throws IOException;
    public Table getIntersectionTables(String dbName, String tName1, String tName2) throws IOException;
    public void close(String dbName, String dbPath) throws IOException;

}
