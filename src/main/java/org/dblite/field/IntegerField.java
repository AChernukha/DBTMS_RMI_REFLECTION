package org.dblite.field;

import java.text.ParseException;

public class IntegerField extends Cell {

    @Override
    public String defaultVal() {
        return "0";
    }

    public IntegerField() {
        super("0");
    }

    public IntegerField(String s) {
        super(s);
    }

    public IntegerField(Integer l) {
        super(String.valueOf(l));
    }

    public IntegerField(Cell f) {
        super(f.getStringData());
    }

    @Override
    public Integer getVal() {
        return Integer.valueOf(getStringData());
    }

    @Override
    public void setVal(Object... val) {
        this.data = String.valueOf((Integer) val[0]);
    }

    @Override
    public void setStringVal(String s) throws ParseException {
        try {
            Integer.valueOf(s);
        } catch (NumberFormatException ex) {
            throw new ParseException(s, 0);
        }
        setStringData(s);
    }

}
