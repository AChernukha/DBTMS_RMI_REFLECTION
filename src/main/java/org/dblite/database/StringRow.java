package org.dblite.database;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class StringRow implements Serializable {

    private ArrayList<String> cells = new ArrayList();

    public StringRow(Row r) {
        for (int i = 0; i < r.cellsCount(); i++) {
            cells.add(r.at(i).getStringData());
        }
    }

    public String cell(int i) {
        return cells.get(i);
    }

    public int size() {
        return cells.size();
    }

    public void addCell(String val) {
        cells.add(val);
    }

    public ArrayList<String> getCel(){
        return cells;
    }

    public void print() {
        System.out.println(cells);
    }

}
