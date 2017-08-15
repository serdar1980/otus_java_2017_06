package atm.cashdrawer;

public interface CellI extends Comparable, Cloneable {
    int count = 0;

    int getNote();

    int put(int numberOfNote);

    int get(int numberOfNote);

    int getCount();
}
