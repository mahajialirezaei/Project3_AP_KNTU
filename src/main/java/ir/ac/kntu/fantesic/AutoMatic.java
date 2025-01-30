package ir.ac.kntu.fantesic;

import ir.ac.kntu.Bank;
import ir.ac.kntu.BonusCapitalFunds;
import ir.ac.kntu.PayaTrans;

public class AutoMatic extends Thread {
    private Bank bank = new Bank();

    private boolean finished = false;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public AutoMatic(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        while (true) {
            if (isFinished()){
                return;
            }
            try {
                new PayaTrans().doAllPayaTrans();
                new BonusCapitalFunds().doAllBonus(bank);
                sleep(20000);
            } catch (Exception e) {
                return;
            }
        }
    }

}
