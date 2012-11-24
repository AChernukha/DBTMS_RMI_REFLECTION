package org.dblite.field;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateIntlField extends Cell {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static final String separator = "|";

    public DateIntlField(Cell f) {
        super(f.getStringData());
    }

    public DateIntlField(String s) {
        super(s);
    }

    public DateIntlField() {
        super("");
    }

    public DateIntlField(Date since, Date till) {
        super(sdf.format(since) + separator + sdf.format(till));
    }

    @Override
    public Object getVal() {
        Date[] dates = new Date[2];
        String[] ss = getStringData().split("\\|");

        try {
            dates[0] = sdf.parse(ss[0]);
            dates[1] = sdf.parse(ss[1]);
        } catch (ParseException ex) {
            return null;
        }

        return dates;
    }

    @Override
    public void setVal(Object... values) {
        Date date1 = (Date) values[0];
        Date date2 = (Date) values[1];
        setStringData(sdf.format(date1) + separator + sdf.format(date2));
    }

    @Override
    public void setStringVal(String s) throws ParseException {
        if ((s != null) && (s.length() > 0)) {
            String ss[] = s.split("\\|");
            if (ss.length != 2) {
                throw new ParseException(s, 0);
            }
            Date date1 = sdf.parse(ss[0]); // throws ParseException
            Date date2 = sdf.parse(ss[1]); // throws ParseException
            if (date2.before(date1)) {
               throw new ParseException("Wrong interval!", 1); 
            }
            setStringData(s);
        }
    }
}
