package atm;

import atm.cashdrawer.CellI;

import java.util.TreeSet;

public class MementoStateAtm {
    private TreeSet<CellI> state;

    public MementoStateAtm(TreeSet<CellI> state) {
        this.state = state;
    }

    public TreeSet<CellI> getState() {
        return state;
    }
}
