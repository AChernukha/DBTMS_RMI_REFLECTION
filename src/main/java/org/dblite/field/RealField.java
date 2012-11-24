package org.dblite.field;

import java.text.ParseException;

public class RealField extends Cell{

    @Override
    public String defaultVal() {
        return "0.0";
    }

    public RealField(Cell f) {
        super(f.getStringData());
    }

    public RealField(String s) {
        super(s);
    }

    public RealField(Double real) {
        super(Double.toString(real));
    }

    public RealField() {
        super("0.0");
    }

    @Override
    public Object getVal() {
        return Double.valueOf(data);
    }


    @Override
    public void setVal(Object... real) {
        Double aDouble = (Double) real[0];
        setStringData(aDouble.toString());
    }

    @Override
    public void setStringVal(String s) throws ParseException {
        try {
            Double.valueOf(s);
        } catch (NumberFormatException ex) {
            throw new ParseException(s, 0);
        }
        setStringData(s);
    }

}
