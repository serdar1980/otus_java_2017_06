package atm.cashdrawer;

public class Cell implements CellI {

    private Integer note;
    private int count;
    private int maxCount;

    public Cell(int note, int maxCount) {
        this.note = note;
        this.maxCount = maxCount;
    }


    @Override
    public int getNote() {
        return note;
    }

    @Override
    public int put(int numberOfNote) {
        if (count + numberOfNote > maxCount) {
            int ret = (count + numberOfNote) - maxCount;
            count = maxCount;
            return ret;
        } else {
            count += numberOfNote;
            return 0;
        }
    }

    @Override
    public int get(int numberOfNote) {
        if (count - numberOfNote > 0) {
            count -= numberOfNote;
            return 0;
        } else {
            int ret = numberOfNote - count;
            count = 0;
            return ret;
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(Object o) {
        Cell tmp = (Cell) o;
        return this.note.compareTo(tmp.note);
    }

    @Override
    public Cell clone() {
        Cell clone = null;
        try {
            clone = (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
