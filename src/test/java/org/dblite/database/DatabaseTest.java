package org.dblite.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.dblite.field.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class DatabaseTest {
    

    @Test
    public void testLoadSave() throws IOException
    {
        Database db = new Database("test", "test.dbl");
        Table t1 = new Table("tname");
        
        t1.addColumn("A", CharField.class);
        t1.addColumn("B", DateField.class);
        Row r1 = t1.getRowSkeleton();

        Calendar calendar = Calendar.getInstance();
        calendar.set(70,0,0);
        Date date = calendar.getTime();

        r1.insert(1, Cell.makeCell(date));
        r1.insert(0, Cell.makeCell("text0"));

        t1.addRow(r1);
        db.addTable(t1);

        db.saveToStorage();
        db = new Database("test", "test.dbl");
        db.loadFromStorage();

        db.loadFromStorage();
        System.out.println(db);
        System.out.println(t1);

        assertTrue(db.getTable("tname").getHeader().equalsModel(t1.getHeader()));
        assertTrue(db.getTable("tname").row(0).at(0).getStringData().equals("text0"));
        assertEquals(db.getTable("tname").row(0).at(1).getStringData(), Cell.makeCell(date).getStringData());
        assertEquals(1, t1.size());
    }
    

    @Test
    public void getDifferenceTablesTest() throws Exception {
        Database db = new Database("test");
        Table t1 = new Table("tname1");

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

        Table t2 = new Table("tname2");
        t2.addColumn("A", CharField.class);
        t2.addColumn("B", IntegerField.class);
        Row r3 = t2.getRowSkeleton();
        r3.insert(0, Cell.makeCell("text2"));
        r3.insert(1, Cell.makeCell(12));
        t2.addRow(r3);
        
        Table resTable = db.getIntersectionTables(t1, t2);
        
        assertEquals(1, resTable.size());
        assertEquals(2, resTable.getHeader().size());
        assertEquals(CharField.class, resTable.getHeader().getTypeAt(0) );
        assertEquals(IntegerField.class, resTable.getHeader().getTypeAt(1));
        assertEquals(1, resTable.size());

        assertEquals("text2", resTable.getStringRow(0).getCel().get(0));
        assertEquals("12", resTable.getStringRow(0).getCel().get(1));
    }

    @Test
    public void getTablesSubTest() throws Exception {
        Database db = new Database("test");
        Table t1 = new Table("tname1");

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

        Table t2 = new Table("tname2");
        t2.addColumn("A", CharField.class);
        t2.addColumn("B", IntegerField.class);
        Row r3 = t2.getRowSkeleton();
        r3.insert(0, Cell.makeCell("text2"));
        r3.insert(1, Cell.makeCell(12));
        t2.addRow(r3);

        Table resTable = db.getTablesSub(t1, t2);

        assertEquals(2, resTable.size());
        assertEquals(2, resTable.getHeader().size());
        assertEquals(CharField.class, resTable.getHeader().getTypeAt(0) );
        assertEquals(IntegerField.class, resTable.getHeader().getTypeAt(1));
        assertEquals(2, resTable.size());

        assertEquals("text1", resTable.getStringRow(0).getCel().get(0));
        assertEquals("11", resTable.getStringRow(0).getCel().get(1));

        assertEquals("text3", resTable.getStringRow(1).getCel().get(0));
        assertEquals("13", resTable.getStringRow(1).getCel().get(1));
    }

}
