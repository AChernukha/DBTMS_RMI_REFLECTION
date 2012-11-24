package org.dblite.field;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;

public class Cell implements CellType {
    public String defaultVal() {
        return "";
    }

    protected String data;

    public Cell(String stringData) {
        data = stringData;
    }

    public String getStringData() {
        return data;
    }

    public void setStringData(String stringData) {
        data = stringData;
    }


    @Override
    public Object getVal() {
        return getStringData();
    }

    @Override
    public void setVal(Object... val) {
        setStringData((String) val[0]);
    }

    static public DateField makeCell(Date date) {
        DateField res = new DateField(date);
        return res;
    }

    static public DateIntlField makeCell(Date d1, Date d2) {
        DateIntlField res = new DateIntlField(d1, d2);
        return res;
    }

    static public RealField makeCell(Double t) {
        RealField res = new RealField(t);
        return res;
    }

    static public RealIntlField makeCell(Double t1, Double t2) {
        RealIntlField res = new RealIntlField(t1, t2);
        return res;
    }

    static public CharField makeCell(String s) {
        CharField res = new CharField(s);
        return res;
    }

    static public IntegerField makeCell(Integer l) {
        IntegerField res = new IntegerField(l);
        return res;
    }

    static public ImgField makeCell(int[] val) {
        ImgField res = new ImgField(val);
        return res;
    }

    public void setStringVal(String s) throws ParseException {
        setVal(s);
    }

    public String toString() {
        return data;
    }
}
