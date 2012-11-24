package org.dblite.field;

import java.text.ParseException;

public interface CellType {

    Object getVal();

    void setVal(Object... val);

    void setStringVal(String s) throws ParseException;
} 
