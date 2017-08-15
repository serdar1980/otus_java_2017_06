package atm.output;

import atm.cashdrawer.CellI;

import java.util.TreeSet;

public class OutMixNoteStrategy implements OutStategyI {
    @Override
    public boolean getMoneyFromCells(TreeSet<CellI> cells, int money) {
        return false;
    }
}
