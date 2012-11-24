package org.dblite.field;

public class CharField extends Cell {

    public CharField(Cell f) {
        super(f.getStringData());
    }

    public CharField(String val) {
        super(val);
    }

    public CharField() {
        super("");
    }

    @Override
    public String getVal() {
        return getStringData();
    }

    @Override
    public void setVal(Object... val) {
        setStringData((String) val[0]);
    }
}
