package org.dblite.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateField extends Cell {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DateField(Cell f) {
        super(f.getStringData());
    }

    public DateField(String s) {
        super(s);
    }

    public DateField(Date date) {
        super(sdf.format(date));
    }

    public DateField() {
        super("");
    }

    @Override
    public Object getVal() {
        Date dateWithoutTime;
        try {
            dateWithoutTime = sdf.parse(getStringData());
        } catch (ParseException ex) {
            return null;
        }
        return dateWithoutTime;
    }

    @Override
    public void setVal(Object... date) {
        Date d = (Date) date[0];
        setStringData(sdf.format(d));
    }

    @Override
    public void setStringVal(String s) throws ParseException {
        if ((s != null) && (s.length() > 0)) {
            sdf.parse(s); // throws ParseException
            setStringData(s);
        }
    }
}
