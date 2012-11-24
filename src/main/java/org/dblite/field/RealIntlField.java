package org.dblite.field;

import java.sql.Time;
import java.text.ParseException;
import java.util.Arrays;

public class RealIntlField extends Cell {

    private static final String separator = "|";

    @Override
    public String defaultVal() {
        return "0.0" + separator + "0.0";
    }

    public RealIntlField(Cell f) {
        super(f.getStringData());
    }

    public RealIntlField(String s) {
        super(s);
    }

    public RealIntlField() {
        super("0.0" + separator + "0.0");
    }

    public RealIntlField(Double since, Double till) {
        super(since + separator + till);
    }

    @Override
    public Object getVal() {
        Double[] doubles = new Double[2];
        String[] ss = getStringData().split("\\|");
        doubles[0] = Double.valueOf(ss[0]);
        doubles[1] = Double.valueOf(ss[1]);
        return doubles;
    }

    @Override
    public void setVal(Object... values) {
        Double t1 = (Double) values[0];
        Double t2 = (Double) values[1];
        setStringData(t1 + separator + t2);
    }

    @Override
    public void setStringVal(String s) throws ParseException {
        String ss[] = s.split("\\|");
        System.out.println(Arrays.toString(ss));
        if (ss.length != 2) {
            throw new ParseException(s, 0);
        }
        try {
            Double d1 = Double.valueOf(ss[0].trim());
            Double d2 = Double.valueOf(ss[1].trim());
            if (d2 < d1) {
                throw new ParseException("Wrong interval!", 1);
            }
        } catch (IllegalArgumentException ex) {
            throw new ParseException(s, 0);
        }
        setStringData(s);
    }
}
