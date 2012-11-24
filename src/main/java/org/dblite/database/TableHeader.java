package org.dblite.database;


import org.dblite.field.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class TableHeader implements Serializable {

    private ArrayList<String> columnNames = new ArrayList();
    private ArrayList<Class<? extends Cell>> types = new ArrayList();

    public TableHeader() {

    }

    public TableHeader(TableHeader tm) {
        this.columnNames = new ArrayList<String>(tm.columnNames);
        this.types = new ArrayList<Class<? extends Cell>>(tm.types);
    }

    public void addColumn(String name, Class<? extends Cell> cls) throws IOException {
        if (columnNames.contains(name)) {
            throw new IOException();
        }
        columnNames.add(name);
        types.add(cls);
    }

    public void setColumnNames(String[] names) {
        columnNames = new ArrayList(Arrays.asList(names));
    }

    public void setColumnTypes(String[] types) {
        for (String s : types) {
            Class<? extends Cell> type = CharField.class;
            if (s.equals("Integer")) type = IntegerField.class;
            if (s.equals("Date")) type = DateField.class;
            if (s.equals("DateIntl")) type = DateIntlField.class;
            if (s.equals("Real")) type = RealField.class;
            if (s.equals("RealIntl")) type = RealIntlField.class;
            if (s.equals("ImgField")) type = ImgField.class;
            this.types.add(type);
        }
    }

    public boolean isRowValid(Row row) {
        if (row.cellsCount() != types.size())
            return false;

        for (int i = 0; i < types.size(); i++)
            if (types.get(i) != row.at(i).getClass())
                return false;

        return true;
    }

    public void appendTableModel(TableHeader tm) {
        columnNames.addAll(new ArrayList<String>(tm.columnNames));
        types.addAll(new ArrayList<Class<? extends Cell>>(tm.types));
    }

    public Row getSkeleton() {
        Row r = new Row();
        for (Class<? extends Cell> cls : types) {
            Cell c;
            try {
                c = cls.newInstance();
            } catch (Exception ex) {
                c = new CharField();
            }
            r.addCell(c);
        }
        return r;
    }

    public Row makeRow(StringRow rr) {
        Row r = new Row();
        for (int i = 0; i < rr.size(); i++) {
            if (types.get(i) == CharField.class)
                r.addCell(new CharField(rr.cell(i)));
            if (types.get(i) == DateField.class)
                r.addCell(new DateField(rr.cell(i)));
            if (types.get(i) == DateIntlField.class)
                r.addCell(new DateIntlField(rr.cell(i)));
            if (types.get(i) == RealField.class)
                r.addCell(new RealField(rr.cell(i)));
            if (types.get(i) == RealIntlField.class)
                r.addCell(new RealIntlField(rr.cell(i)));
            if (types.get(i) == IntegerField.class)
                r.addCell(new IntegerField(rr.cell(i)));
        }
        return r;
    }

    public int size() {
        return types.size();
    }

    public Class<? extends Cell> getTypeAt(int i) {
        return types.get(i);
    }

    public String getColumnName(int i) {
        return columnNames.get(i);
    }

    public boolean equalsModel(TableHeader tm) {
        return columnNames.equals(tm.columnNames) &&
                types.equals(tm.types);
    }
}
