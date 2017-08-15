package atm.cashdrawer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CellTest {
    CellI cell;

    @Before
    public void startUp() {
        cell = new Cell(5, 10);
    }

    @After
    public void close() {
        cell = null;
    }

    @Test
    public void getNoteValue() {
        Assert.assertTrue(cell.getNote() == 5);
    }

    @Test
    public void putNoteOfCell() {
        int returnNote = cell.put(5);
        Assert.assertTrue(returnNote == 0);
        Assert.assertTrue(cell.getCount() == 5);
    }

    @Test
    public void putMoreNoteThanMax() {
        int returnNote = cell.put(15);
        Assert.assertTrue(returnNote == 5);
        Assert.assertTrue(cell.getCount() == 10);
    }

    @Test
    public void getCountNoteInCell() {
        int returnNote = cell.put(1);
        Assert.assertTrue(returnNote == 0);
        Assert.assertTrue(cell.getCount() == 1);
    }

    @Test
    public void getNoteFromCell() {
        int needToGet = 1;
        int returnNote = cell.put(2);
        returnNote = cell.get(1);
        Assert.assertTrue(returnNote == 0);
        Assert.assertTrue(cell.getCount() == 1);
    }

    @Test
    public void getMoreNoteThanHaveInCell() {
        int needToGet = 3;
        int returnNote = cell.put(2);
        returnNote = cell.get(needToGet);
        Assert.assertTrue(returnNote == 1);
        Assert.assertTrue(returnNote != needToGet);
        Assert.assertTrue(cell.getCount() == 0);
    }
}
