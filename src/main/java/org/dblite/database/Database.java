package org.dblite.database;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import org.dblite.field.Cell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Database implements Serializable {

    private String name;
    private String path;
    protected List<Table> tables = new ArrayList();
    private boolean isSaved = false;

    public Database(String name) {
        this.name = name;
    }

    public Database(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path + ".dbl";
    }

    public boolean isIsSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
    
    

    public boolean hasTable(String name) {
        return getListTables().contains(name);
    }

    public void loadFromStorage() throws IOException {
        isSaved = true;
        if (path != null) {
            File file = new File(path);
            file.createNewFile();
            try {
                tables = (ArrayList<Table>) new XStream().fromXML(file);
            } catch (StreamException ex) {
                tables = new ArrayList<Table>();
            }
        }
    }

    public void saveToStorage() throws IOException {
        isSaved = true;
        File file = new File(path);
        file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, false);

        new XStream().toXML(tables, out);
    }

    public Table getTable(String tableName) throws IOException {
        if (tables == null) {
            loadFromStorage();
        }

        Iterator<Table> it = tables.iterator();
        while (it.hasNext()) {
            Table t = it.next();
            if (t.name.equals(tableName)) {
                return t;
            }
        }
        return null;
    }

    public void removeTable(String tableName) throws IOException {
        if (tables == null) {
            loadFromStorage();
        }

        isSaved = false;
        Iterator<Table> it = tables.iterator();
        while (it.hasNext()) {
            Table t = it.next();
            if (t.name.equals(tableName)) {
                tables.remove(t);
                break;
            }
        }
    }

    public void addTable(Table t) throws IOException {
        if (tables == null) {
            loadFromStorage();
        }

        isSaved = false;
        if ((getListTables().contains(t.getName()))
                && (!t.getName().contains("Intersection_"))
                && (!t.getName().contains("Subtraction_"))) {
            throw new IOException();
        }
        if ((t.getName().contains("Intersection_")) 
                && (t.getName().contains("Subtraction_"))){
            for (int i = 0; i < tables.size(); i++) {
                if(tables.get(i).getName().equals(t.getName())){
                    tables.add(i, t);
                    break;
                }
            }
        }else{
            tables.add(t);
        }
    }

    public Table getIntersectionTables(String t1, String t2) throws IOException {
        return getIntersectionTables(getTable(t1), getTable(t2));
    }

    public Table getIntersectionTables(Table t1, Table t2)
            throws IOException {
        if (t1.getHeader().size() != t1.getHeader().size()) {
            throw new IOException();
        }

        String resTName = "Intersection_" + t1.getName() + "_" + t2.getName();
        TableHeader th = new TableHeader(t1.getHeader());
        for (int i = 0; i < t1.getHeader().size(); i++) {
            if (t1.getHeader().getTypeAt(i) != t2.getHeader().getTypeAt(i)) {
                throw new IOException();
            }
        }

        Table resTable = new Table(resTName);
        resTable.setHeader(th);

        for (int i = 0; i < t1.size(); i++) {
            StringRow r1 = t1.row(i).getStringRow();
            boolean flag = false;

            for (int j = 0; j < t2.size(); j++) {
                StringRow r2 = t2.row(j).getStringRow();

                boolean rowsEquals = true;
                for (int k = 0; k < t1.getHeader().size(); k++) {
                    if (!r1.cell(k).equals(r2.cell(k))) {
                        rowsEquals = false;
                        break;
                    }
                }
                if (rowsEquals) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                resTable.addRow(t1.row(i));
            }
        }

//        tables.add(resTable);
        return resTable;

    }

    public Table getTablesSub(String t1, String t2) throws IOException {
        return getTablesSub(getTable(t1), getTable(t2));
    }

    public Table getTablesSub(Table t1, Table t2)
            throws IOException {
        if (t1.getHeader().size() != t1.getHeader().size()) {
            throw new IOException();
        }

        String resTName = "Subtraction_" + t1.getName() + "_" + t2.getName();
        TableHeader th = new TableHeader(t1.getHeader());
        for (int i = 0; i < t1.getHeader().size(); i++) {
            if (t1.getHeader().getTypeAt(i) != t2.getHeader().getTypeAt(i)) {
                throw new IOException();
            }
        }

        Table resTable = new Table(resTName);
        resTable.setHeader(th);

        for (int i = 0; i < t1.size(); i++) {
            StringRow r1 = t1.row(i).getStringRow();
            boolean flag = false;

            for (int j = 0; j < t2.size(); j++) {
                StringRow r2 = t2.row(j).getStringRow();

                boolean rowsEquals = true;
                for (int k = 0; k < t1.getHeader().size(); k++) {
                    if (!r1.cell(k).equals(r2.cell(k))) {
                        rowsEquals = false;
                        break;
                    }
                }
                if (rowsEquals) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                resTable.addRow(t1.row(i));
            }
        }

//        tables.add(resTable);
        return resTable;
    }

    public ArrayList<String> getListTables() {
        ArrayList<String> res = new ArrayList();
        for (Table t : tables) {
            res.add(t.name);
        }
        return res;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            saveToStorage();
        } finally {
            super.finalize();
        }
    }

    public void removeRow(String tName, int row) throws IOException {
        getTable(tName).removeRow(row);
    }




}
