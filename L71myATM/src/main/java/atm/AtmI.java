package atm;

import java.util.Map;

public interface AtmI {

    boolean withdraw(int money);

    void reInit();

    Map<Integer, Integer> loan(Map<Integer, Integer> money);

    int getBalance();


}
