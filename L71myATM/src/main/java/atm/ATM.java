package atm;

import atm.cashdrawer.Cell;
import atm.cashdrawer.CellComparator;
import atm.cashdrawer.CellI;
import atm.output.OutStategyI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class ATM implements AtmI {
    private TreeSet<CellI> cells;
    private TreeSet<CellI> cellsState;
    private OutStategyI strategy;

    public ATM(TreeSet<CellI> cells, OutStategyI strategy) {
        this.cells = cells;
        this.cellsState = copyCells(cells);
        this.strategy = strategy;
    }

    private TreeSet<CellI> copyCells(TreeSet<CellI> cellsFrom) {
        TreeSet<CellI> cellsTo = new TreeSet<>(CellComparator.getInstance());
        Iterator<CellI> iterator = cellsFrom.iterator();
        while (iterator.hasNext()) {
            cellsTo.add(((Cell) iterator.next()).clone());
        }
        return cellsTo;
    }

    @Override
    public Map<Integer, Integer> putMoney(Map<Integer, Integer> money) {
        return null;
    }

    public boolean withdraw(int money) {
        return strategy.getMoneyFromCells(cells, money);
    }

    public void reInit() {
        this.cells = copyCells(cellsState);
    }

    @Override
    public Map<Integer, Integer> loan(Map<Integer, Integer> money) {
        Map<Integer, Integer> res = new HashMap<>();
        for (Integer note : money.keySet()) {
            CellI cell = findCell(note);
            if (cell != null) {
                int moneyRefund = cell.put(money.get(note));
                if (moneyRefund > 0) {
                    res.put(note, moneyRefund);
                }
            } else {
                res.put(note, money.get(note));
            }
        }
        return res;
    }

    @Override
    public int getBalance() {
        int res = 0;
        for (CellI cell : cells) {
            res += cell.getNote() * cell.getCount();
        }
        return res;
    }

    private CellI findCell(int note) {
        Iterator<CellI> iterator = cells.iterator();
        while (iterator.hasNext()) {
            CellI cell = iterator.next();
            if (cell.getNote() == note)
                return cell;
        }
        return null;
    }
}
