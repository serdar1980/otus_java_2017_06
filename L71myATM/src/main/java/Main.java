import atm.ATM;
import atm.AtmI;
import atm.AtmManager;
import atm.AtmManagerI;
import atm.cashdrawer.Cell;
import atm.cashdrawer.CellComparator;
import atm.cashdrawer.CellI;
import atm.output.OutMinNoteStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Main {
    public static void main(String... args) {
        System.out.println("Start Application");
        AtmManagerI atmManager = new AtmManager();

        TreeSet<CellI> cells = new TreeSet<>(CellComparator.getInstance());
        cells.add(new Cell(100, 1000));
        cells.add(new Cell(500, 1000));
        cells.add(new Cell(1000, 100));
        cells.add(new Cell(5000, 100));

        AtmI amt = new ATM(cells, new OutMinNoteStrategy());

        TreeSet<CellI> cells1 = new TreeSet<>(CellComparator.getInstance());
        Cell cell = new Cell(100, 10);
        cell.put(9);
        cells1.add(cell);
        cells1.add(new Cell(500, 10));
        cell = new Cell(1000, 10);
        cell.put(9);
        cells1.add(cell);
        cells1.add(new Cell(5000, 10));
        AtmI amt1 = new ATM(cells1, new OutMinNoteStrategy());

        atmManager.addAtm(amt1);

        Map<Integer, Integer> loanMoney = new HashMap<>();
        loanMoney.put(100, 1);
        loanMoney.put(1000, 1);
        loanMoney.put(5000, 1);
        System.out.println("баланс всех ATM " + atmManager.getBalaces());
        System.out.println("баланс до того как положили денег " + amt1.getBalance());
        amt1.loan(loanMoney);
        System.out.println("баланс после того как положили денег " + amt1.getBalance());
        amt1.withdraw(5100);
        System.out.println("баланс после того как сняли денег " + amt1.getBalance());
        amt1.withdraw(5100);
        System.out.println("баланс после того как попробовали снять денег больше чем есть " + amt1.getBalance());
        atmManager.reInit();
        System.out.println("баланс до того как востоновили состояние " + amt1.getBalance());
        System.out.println("баланс всех ATM после востановления " + atmManager.getBalaces());
        System.out.println("Stop Application");
    }
}
