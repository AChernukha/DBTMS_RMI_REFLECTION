package org.dblite.database;

import org.dblite.field.Cell;
import org.dblite.field.CellType;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {

    public String name;
    private TableHeader header = new TableHeader();
    private List<StringRow> data = new ArrayList();

    public Table(String name) {
        this.name = name;
    }

    public Table(Table o) {
        this.name = o.name;
        this.header = o.header;
        this.data = o.data;
    }

    public String getName() {
        return name;
    }

    public Row getRowSkeleton() {
        return header.getSkeleton();
    }

    public void setHeader(TableHeader tableHeader) {
        this.header = tableHeader;
    }

    public TableHeader getHeader() {
        return header;
    }

    public void addValue(int ii, int jj, String val) throws ParseException, IOException {
            StringRow strRow = getStringRow(ii);
            Row row = header.getSkeleton();
            for (int j = 0; j < row.cellsCount(); j++) {
                Cell cell = row.at(j);
                if(j == jj) {
                    cell.setStringVal(val);
                } else {
                    cell.setStringVal(strRow.cell(j));
                }
                row.insert(j, cell);
            }
            data.set(ii, row.getStringRow());

    }

    public void addRow(Row row) throws IOException {
        if (!header.isRowValid(row)) {
            throw new IOException("Invalid string row!");
        }
        addStringRow(row.getStringRow());
    }

    public void addCleanRow() throws IOException {
        StringRow rr = getRowSkeleton().getStringRow();
        data.add(rr);
    }

    private void addStringRow(StringRow r) {
        data.add(r);
    }

    public void removeRow(int ind) {
        data.remove(ind);
    }

    public int size() {
        return data.size();
    }

    public String[] getColumnNames() {
        String[] res = new String[header.size()];
        for (int i = 0; i < header.size(); i++) {
            res[i] = header.getColumnName(i);
        }
        return res;
    }

    public Row row(int ind) {
        return header.makeRow(data.get(ind));
    }

    public Cell at(int row, int col) {
        return row(row).at(col);
    }

    public StringRow getStringRow(int ind) {
        return data.get(ind);
    }

    public void addColumn(String name, Class<? extends Cell> cls) throws IOException {
        header.addColumn(name, cls);

        String def = "";
        System.out.println(def);
        try {
            def = cls.newInstance().defaultVal();
        } catch (InstantiationException ex) {
            throw new IOException();
        } catch (IllegalAccessException ex) {
            throw new IOException();
        }

        for (StringRow rr : data) {
            rr.addCell(def);
        }
    }

    public void print()
    {
        System.out.println("TABLE " + name);
        for (int i=0; i<size(); i++) {
            row(i).print();
        }
    }
}
