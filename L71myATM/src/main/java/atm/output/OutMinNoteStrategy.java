package atm.output;

import atm.cashdrawer.Cell;
import atm.cashdrawer.CellI;

import java.util.*;

public class OutMinNoteStrategy implements OutStategyI {
    @Override
    public boolean getMoneyFromCells(TreeSet<CellI> cells, int money) {
        Iterator<CellI> iter = cells.descendingIterator();
        Map<CellI, Integer> cellsNeedReduce = new HashMap<>();
        while (iter.hasNext()) {
            Cell cell = (Cell) iter.next();
            int needNote = money / cell.getNote();
            if (needNote > 0) {
                if (cell.getCount() >= needNote) {
                    cellsNeedReduce.put(cell, needNote);
                    money -= needNote * cell.getNote();
                } else {
                    return false;
                }
            }
        }
        if (cellsNeedReduce.size() > 0) {
            for (CellI cell : cellsNeedReduce.keySet()) {
                cell.get(cellsNeedReduce.get(cell).intValue());
            }
            return true;
        } else {
            return false;
        }
    }
}
