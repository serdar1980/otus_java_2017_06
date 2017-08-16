package atm.cashdrawer;

import java.util.Comparator;

public class CellComparator implements Comparator {
    private static final CellComparator CELL_COMPARATOR = new CellComparator();

    private CellComparator() {
    }

    public static CellComparator getInstance() {
        return CELL_COMPARATOR;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Cell cell1 = (Cell) o1;
        Cell cell2 = (Cell) o2;
        return Integer.valueOf(cell1.getNote())
                .compareTo(Integer.valueOf(cell2.getNote()));
    }

}
