package atm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AtmManager implements AtmManagerI {
    private List<AtmI> atms;

    public AtmManager() {
        atms = new ArrayList<>();
    }

    @Override
    public boolean addAtm(AtmI atm) {
        return atms.add(atm);
    }

    @Override
    public boolean removeAtm(AtmI atm) {
        return atms.remove(atm);
    }

    @Override
    public int countAtm() {
        return atms.size();
    }

    @Override
    public long getBalaces() {
        long balance = 0;
        Iterator<AtmI> itr = atms.iterator();
        while (itr.hasNext()) {
            AtmI atm = itr.next();
            balance += atm.getBalance();
        }
        return balance;
    }

    @Override
    public void reInit() {
        Iterator<AtmI> itr = atms.iterator();
        while (itr.hasNext()) {
            AtmI atm = itr.next();
            atm.reInit();
        }
    }
}
