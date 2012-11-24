package org.dblite.database;


import org.dblite.field.Cell;

import java.io.IOException;
import java.util.ArrayList;

public class Row{

    private ArrayList<Cell> cells = new ArrayList();
    private StringRow stringRow;

    public void insert(int at, Cell cell) throws IOException {
        if (cell.getClass() != cells.get(at).getClass()) {
            throw new IOException("Illegal cell format!");
        }
        cells.set(at, cell);
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public Cell at(int i) {
        try {
            return cells.get(i);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public int cellsCount() {
        return cells.size();
    }

    public StringRow getStringRow() {
        return new StringRow(this);
    }

    public void print() {
        getStringRow().print();
        System.out.println("");
    }

}
