package atm.output;

import atm.cashdrawer.CellI;

import java.util.TreeSet;

public interface OutStategyI {
    boolean getMoneyFromCells(TreeSet<CellI> cells, int money);
}
