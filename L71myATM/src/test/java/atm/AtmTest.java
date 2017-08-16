package atm;

import atm.cashdrawer.Cell;
import atm.cashdrawer.CellComparator;
import atm.cashdrawer.CellI;
import atm.output.OutStategyI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static org.mockito.Mockito.when;

public class AtmTest {
    AtmI atm;
    @Mock
    OutStategyI stategy;
    Map<Integer, Integer> loanMoney;

    int balanceDef;

    @Before
    public void startUP() {
        MockitoAnnotations.initMocks(this);
        loanMoney = new HashMap<>();
        loanMoney.put(100, 1);
        loanMoney.put(1000, 1);


        for (int key : loanMoney.keySet()) {
            balanceDef += loanMoney.get(key) * key;
        }


        TreeSet<CellI> cells = new TreeSet<>(CellComparator.getInstance());
        cells.add(new Cell(100, 1000));
        cells.add(new Cell(500, 1000));
        cells.add(new Cell(1000, 100));
        cells.add(new Cell(5000, 100));
        when(stategy.getMoneyFromCells(cells, 1500)).thenReturn(true);
        when(stategy.getMoneyFromCells(cells, 1520)).thenReturn(false);
        atm = new ATM(cells, stategy);
    }

    @After
    public void end() {
        atm = null;
    }

    @Test
    public void getCorrectSumMoney() {
        Assert.assertTrue(atm.withdraw(1500));
    }

    @Test
    public void getUnCorrectSumMoney() {
        Assert.assertFalse(atm.withdraw(1520));
    }

    @Test
    public void checkCorrectLoan() {
        Map<Integer, Integer> res = atm.loan(loanMoney);
        int balance = atm.getBalance();
        Assert.assertTrue(balance == balanceDef);
    }

    @Test
    public void reInitAtm() {
        Map<Integer, Integer> res = atm.loan(loanMoney);
        int balance = atm.getBalance();
        Assert.assertTrue(balance == balanceDef);
        atm.reInit();
        balance = atm.getBalance();
        Assert.assertTrue(balance == 0);
    }
}
