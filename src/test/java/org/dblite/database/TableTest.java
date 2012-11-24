package org.dblite.database;

import org.dblite.field.Cell;
import org.dblite.field.CharField;
import org.dblite.field.IntegerField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class TableTest {

    private Table t1;
    @Before
    public void before() throws Exception {
        t1 = new Table("tname1");

        t1.addColumn("A", CharField.class);
        t1.addColumn("B", IntegerField.class);

        Row r1 = t1.getRowSkeleton();
        r1.insert(0, Cell.makeCell("text1"));
        r1.insert(1, Cell.makeCell(11));
        t1.addRow(r1);

        r1.insert(0, Cell.makeCell("text2"));
        r1.insert(1, Cell.makeCell(12));
        t1.addRow(r1);

        r1.insert(0, Cell.makeCell("text3"));
        r1.insert(1, Cell.makeCell(13));
        t1.addRow(r1);
    }

    @Test
    public void addValueTest() throws IOException, ParseException {
        t1.addValue(0,1, "22");

        assertEquals("text1", t1.getStringRow(0).getCel().get(0));
        assertEquals("22", t1.getStringRow(0).getCel().get(1));

        t1.addValue(1,0, "new text");
        assertEquals("new text", t1.getStringRow(1).getCel().get(0));

    }

    @Test(expected = Exception.class)
    public void addWrongValueTest() throws Exception {
        t1.addValue(1,1, "new text");
        assertEquals("new text", t1.getStringRow(1).getCel().get(0));

    }

}
