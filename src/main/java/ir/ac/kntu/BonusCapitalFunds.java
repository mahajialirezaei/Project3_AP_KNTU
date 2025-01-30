package ir.ac.kntu;

import java.time.Instant;

import ir.ac.kntu.util.Calendar;

public class BonusCapitalFunds extends SavingsCapitalFunds {
    private boolean removable = true;
    private Date dateExpire = new Date();
    private Date dateModified = new Date();

    public BonusCapitalFunds(String line) {
        String[] fields = line.split(",");
        setSavingBalance(Double.parseDouble(fields[0]));
        setIdocument(fields[1]);
        setDateExpire(new Date(Instant.parse(fields[2])));
        setDateModified(new Date(Instant.parse(fields[3])));
        setRemovable(Boolean.parseBoolean(fields[4]));
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public boolean getRemovable() {
        return this.removable;
    }

    public Date getDateExpire() {
        return this.dateExpire;
    }

    public void setDateExpire(Date dateExpire) {
        this.dateExpire = new Date(dateExpire);
    }

    public BonusCapitalFunds(int month, double savingBalance, boolean removable, String iDocument) {
        super(savingBalance, iDocument);
        this.removable = removable;
        Date date = new Date(Calendar.now());
        setDateExpire(new Date(date.getYear(), date.getMonth() + month, date.getDay()));
        dateExpire.setInstantDate(dateExpire.dateToInstant(dateExpire));
        System.out.println(dateExpire);
        setDateModified(new Date(date.getYear(), date.getMonth(), date.getDay()));
        dateModified.setInstantDate(dateModified.dateToInstant(dateModified));
        System.out.println(dateModified);
    }

    public BonusCapitalFunds() {
        setRemovable(true);
    }

    public boolean isRemovable() {
        return this.removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void remove(User user1) {
        Date now = new Date(Calendar.now());
        if (now.sub() >= dateExpire.sub()) {
            user1.getAccount().setBalance(user1.getAccount().getBalance() + this.getSavingBalance());
            user1.getAccount().getBonusFunds().remove(this);
        } else {
            System.out.println(
                    "The time to withdraw money has not come or the bonus has not the reward has not been deposited");
        }
    }

    public void depositeReward(User user1) {
        Date now = new Date(Calendar.now());
        if (now.sub() >= dateExpire.sub() && !removable) {
            double reward = (dateExpire.getMonth() - dateModified.getMonth()) * this.getSavingBalance();
            user1.getAccount().setBalance(user1.getAccount().getBalance() + reward);
            setRemovable(true);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "BonusCapitalFunds{" +
                "removable=" + removable +
                ", dateExpire=" + dateExpire +
                ", dateModified=" + dateModified +
                '}';
    }

    @Override
    public String fileToString() {
        return getSavingBalance() + "," + getIDocument() + "," + getDateExpire().getInstantDate() + ","
                + getDateModified().getInstantDate() + "," + getRemovable() + "\n";
    }

    public void doAllBonus(Bank bank) {
        for (User user1 : bank.getUsers().keySet()) {
            user1.getAccount().getBonusFunds().stream().filter(a -> !a.isRemovable()).forEach(b -> b.depositeReward(user1));
        }
    }
}
