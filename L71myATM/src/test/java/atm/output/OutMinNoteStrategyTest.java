package atm.output;

import atm.cashdrawer.Cell;
import atm.cashdrawer.CellComparator;
import atm.cashdrawer.CellI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

public class OutMinNoteStrategyTest {
    OutMinNoteStrategy strategy = new OutMinNoteStrategy();
    TreeSet<CellI> cells = new TreeSet<>(CellComparator.getInstance());

    @Before
    public void startup() {
        Cell cell = new Cell(500, 1000);
        cell.put(1);
        cells.add(cell);

        cell = new Cell(100, 1000);
        cell.put(1);
        cells.add(cell);

        cell = new Cell(1000, 1000);
        cell.put(1);
        cells.add(cell);

        cells.add(new Cell(5000, 100));
    }

    @After
    public void end() {
        cells = null;
    }

    @Test
    public void ShouldGetTrueAndReduceCells() {
        Assert.assertTrue(strategy.getMoneyFromCells(cells, 1600));
        for (CellI cell : cells) {
            Assert.assertTrue(cell.getCount() == 0);
        }

    }

    @Test
    public void ShouldGetFalseAndNotReduceCells() {
        Assert.assertFalse(strategy.getMoneyFromCells(cells, 5100));
        for (CellI cell : cells) {
            if (cell.getNote() == 5000) {
                Assert.assertTrue(cell.getCount() == 0);
            } else {
                Assert.assertTrue(cell.getCount() == 1);
            }
        }
    }
}

