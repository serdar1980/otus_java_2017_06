package atm;

public interface AtmManagerI {
    boolean addAtm(AtmI atm);
    boolean removeAtm(AtmI atm);

    int countAtm();

    long getBalaces();

    void reInit();
}
