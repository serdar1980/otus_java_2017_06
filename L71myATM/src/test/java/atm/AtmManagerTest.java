package atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AtmManagerTest {
    private static final int BALACE_IN_ATM = 6600;
    AtmManagerI atmManager = new AtmManager();
    @Mock
    AtmI atm;

    @Before
    public void startup() {
        MockitoAnnotations.initMocks(this);
        when(atm.getBalance()).thenReturn(BALACE_IN_ATM);
    }

    @Test
    public void ShouldAddNewATMToList() {
        atmManager.addAtm(atm);
        Assert.assertTrue(atmManager.countAtm() == 1);
    }

    @Test
    public void ShouldRemoveATMFromList() {
        atmManager.addAtm(atm);
        atmManager.removeAtm(atm);
        Assert.assertTrue(atmManager.countAtm() == 0);
    }

    @Test
    public void ShouldGetBalance() {
        atmManager.addAtm(atm);
        atmManager.addAtm(atm);
        Assert.assertTrue(atmManager.getBalaces() == BALACE_IN_ATM * 2);
    }

    @Test
    public void ShouldReinitAtm() {
        atmManager.addAtm(atm);
        atmManager.addAtm(atm);
        atmManager.reInit();
        verify(atm, times(2)).reInit();
    }
}
