package atm.cashdrawer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.TreeSet;

public class CellComparatorTest {
    private static final String RES_SORT = "[Note: 100 Count: 0, Note: 500 Count: 0, Note: 1000 Count: 0, Note: 5000 Count: 0]";

    @Test
    public void createRandomCellShouldGetSortTree() {
        TreeSet<CellI> cells = new TreeSet<>(CellComparator.getInstance());
        cells.add(new Cell(1000, 1000));
        cells.add(new Cell(5000, 1000));
        cells.add(new Cell(100, 100));
        cells.add(new Cell(500, 100));
        Assert.assertTrue(RES_SORT.equals(cells.toString()));

    }


}
