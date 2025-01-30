package ir.ac.kntu;

import java.util.List;
import java.util.Objects;

import ir.ac.kntu.fantesic.ScannerWrapper;
import ir.ac.kntu.util.Calendar;

public class SavingsCapitalFunds {
    private double savingBalance;
    private boolean isActive = false;

    private String iDocument;

    public SavingsCapitalFunds(String line) {
        String[] fields = line.split(",");
        setSavingBalance(Double.parseDouble(fields[0]));
        setIdocument(fields[1]);
        setActive(true);
    }

    public String getIDocument() {
        return iDocument;
    }

    public void setIdocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public SavingsCapitalFunds(double savingBalance, String iDocument) {
        this.savingBalance = savingBalance;
        this.isActive = true;
        this.iDocument = iDocument;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SavingsCapitalFunds() {
    }

    public double getSavingBalance() {
        return savingBalance;
    }

    public void setSavingBalance(double savingBalance) {
        this.savingBalance = savingBalance;
    }

    @Override
    public String toString() {
        return "SavingsCapitalFunds{" +
                "savingBalance=" + savingBalance +
                ", isActive=" + isActive +
                ", iDocument='" + iDocument + '\'' +
                '}';
    }

    public String fileToString() {
        return savingBalance + "," + iDocument + "\n";
    }

    public void capitalFunds(User user1) {
        while (true) {
            System.out.println("1.SavingFunds\n2.BonusFund\n3.ResidualFund\n4.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> savingFundsUser(user1);
                case "2" -> bonusFundsUser(user1);
                case "3" -> residualFundsUser(user1);
                case "4" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }
        }

    }

    public void residualFundsUser(User user1) {
        while (true) {
            System.out.println("1.see residual fund\n2.edit activate residual fund user or take back money\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> seeResidualFund(user1);
                case "2" -> setActivationResidual(user1);
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }
    }

    private void setActivationResidual(User user1) {
        while (true) {
            System.out.println("1.active resiual Fund\n2.deactive resiual fund\n3.take back money\n4.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> user1.getAccount().getResidualFunds().setActive(true);
                case "2" -> deactivateResidualFunds(user1);
                case "3" -> takeBackResidualFunds(user1);
                case "4" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }
        }
    }

    private void seeResidualFund(User user1) {
        try {
            System.out.println(user1.getAccount().getResidualFunds());
        } catch (Exception e) {
            System.out.println("you have not residual fund");
        }
    }

    private void takeBackResidualFunds(User user1) {
        user1.getAccount()
                .setBalance(user1.getAccount().getBalance() + user1.getAccount().getResidualFunds().getSavingBalance());
        user1.getAccount().getTransactions().add(new Transaction(TransactionType.WITHDRAW, user1.getAccount().getResidualFunds().getSavingBalance(), user1.getAccount().getResidualFunds().getIDocument(), user1.getAccount().getiDocument(), Calendar.now(), user1.getFirstName(), user1.getLastName()));
        user1.getAccount().getTransactions().sort(Transaction::compareTo);
        user1.getAccount().getResidualFunds().setSavingBalance(0);
    }

    private void deactivateResidualFunds(User user1) {
        takeBackResidualFunds(user1);
        user1.getAccount().getResidualFunds().setActive(false);
    }

    public void bonusFundsUser(User user1) {
        while (true) {
            System.out.println("1.see Bonus funds\n2. make a new bonus fund\n3. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> seeBonusFunds(user1);
                case "2" -> makeBonusFund(user1);
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }
    }

    private void makeBonusFund(User user1) {
        System.out.println("enter your money");
        double amount = setHowMuchSavingFund(user1);
        int month = setHowLongMonthBonus();
        user1.getAccount().setBalance(user1.getAccount().getBalance() - amount);
        BonusCapitalFunds bon = new BonusCapitalFunds(month, amount, false,
                user1.getAccount().getIDocument() + Objects.hash(user1.getAccount()) + (int) (Math.random() * 470) + 11);
        user1.getAccount().getBonusFunds().add(bon);
        System.out.println("this is your bonus funds");
        System.out.println(bon);
    }

    private int setHowLongMonthBonus() {
        while (true) {
            System.out.println("enter month");
            String month = ScannerWrapper.nextLine();
            if (!month.matches("\\d+")) {
                System.out.println("enter a valid number");
                continue;
            }
            return Integer.parseInt(month);
        }
    }

    public void seeBonusFunds(User user1) {
        printBonusFund(user1.getAccount().getBonusFunds());
        while (true) {
            System.out.println("1.close account and take bonus\n2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> takeBonus(user1);
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public void takeBonus(User user1) {
        if (user1.getAccount().getBonusFunds().size() == 0) {
            System.out.println("you have no bonus fund");
            return;
        }
        System.out.println("Enter your bonus Fund iDocument");
        String iDocument = ScannerWrapper.nextLine();
        for (BonusCapitalFunds bon : user1.getAccount().getBonusFunds()) {
            if (iDocument.equals(bon.getIDocument())) {
                if (bon.getRemovable()) {
                    user1.getAccount().setBalance(user1.getAccount().getBalance() + bon.getSavingBalance());
                    user1.getAccount().getTransactions().add(new Transaction(TransactionType.WITHDRAW, bon.getSavingBalance(), bon.getIDocument(), user1.getAccount().getiDocument(), Calendar.now(), user1.getFirstName(), user1.getLastName()));
                    user1.getAccount().getTransactions().sort(Transaction::compareTo);
                    user1.getAccount().doCapitalFundsTrans(user1, user1, bon.getSavingBalance());
                    bon.setSavingBalance(0);
                    bon.setActive(false);
                    user1.getAccount().getBonusFunds().remove(bon);
                    return;
                } else {
                    System.out.println("the manager has not checked it");
                    return;
                }
            }
        }
        System.out.println("no bon capital fund found");
    }

    private void printBonusFund(List<BonusCapitalFunds> bonusFunds) {
        int start = 0, end = start + 10;
        while (true) {
            bonusFunds.stream().skip(start).limit(end - start + 1)
                    .forEach(bonusFund -> System.out.println(bonusFunds.indexOf(bonusFund) + ": " + bonusFund));
            System.out.println("1.previous page\n2.next page\n3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> {
                    if (start >= 10) {
                        start -= 10;
                        end -= 10;
                    }
                }
                case "2" -> {
                    start += 10;
                    end += 10;
                }
                case "3" -> {
                    return;
                }
                default -> {
                    System.out.println("you should choose 1-3");
                }
            }
        }
    }

    public void savingFundsUser(User user1) {
        while (true) {
            System.out.println("1.see saving funds\n2. make a new saving fund\n3. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> seeSavingFunds(user1);
                case "2" -> makeSavingFund(user1);
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }
    }

    public void makeSavingFund(User user1) {
        System.out.println("enter your money");
        double amount = setHowMuchSavingFund(user1);
        user1.getAccount().setBalance(user1.getAccount().getBalance() - amount);
        SavingsCapitalFunds sav = new SavingsCapitalFunds(amount,
                user1.getAccount().getIDocument() + Objects.hash(user1.getAccount()) + (int) (Math.random() * 470) + 11);
        user1.getAccount().getSavingFunds().add(sav);
        System.out.println("this is your saved funds");
        System.out.println(sav);
    }

    private double setHowMuchSavingFund(User user1) {
        while (true) {
            String amount = ScannerWrapper.nextLine();
            if (!amount.matches("\\d+.*\\d*")) {
                System.out.println("enter a valid number");
                continue;
            }
            if (Double.parseDouble(amount) > user1.getAccount().getBalance()) {
                System.out.println("your balance is not enough");
                return 0;
            }

            return Double.parseDouble(amount);
        }
    }

    public void seeSavingFunds(User user1) {
        printSavingFund(user1.getAccount().getSavingFunds());
        while (true) {
            System.out.println("1.take back money from any SavingFund\n\n2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> takeBackMoney(user1);
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public void takeBackMoney(User user1) {
        while (true) {
            System.out.println("1.take back all money\n2.enter amount of money to back\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> takeBackAllMoney(user1);
                case "2" -> takeBackAmountMoney(user1);
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }

    }

    private void takeBackAmountMoney(User user1) {
        while (true) {
            System.out.println("Enter your idocument of saving fund");
            String iDocument = ScannerWrapper.nextLine();
            for (SavingsCapitalFunds sav : user1.getAccount().getSavingFunds()) {
                if (iDocument.equals(sav.getIDocument())) {
                    System.out.println("Enter your amount of money");
                    double amount = amountTakeBack(sav);
                    user1.getAccount().setBalance(user1.getAccount().getBalance() + amount);
                    user1.getAccount().getTransactions().add(new Transaction(TransactionType.WITHDRAW, amount, sav.getIDocument(), user1.getAccount().getiDocument(), Calendar.now(), user1.getFirstName(), user1.getLastName()));
                    user1.getAccount().getTransactions().sort(Transaction::compareTo);
                    user1.getAccount().doCapitalFundsTrans(user1, user1, amount);
                    sav.setSavingBalance(sav.getSavingBalance() - amount);
                    return;
                }
            }
            System.out.println("no sav capital fund found");
        }
    }

    private double amountTakeBack(SavingsCapitalFunds sav) {
        while (true) {
            System.out.println("enter your money");
            String amount = ScannerWrapper.nextLine();
            if (!amount.matches("\\d+.*\\d*")) {
                System.out.println("enter a valid number");
                continue;
            }
            if (Double.parseDouble(amount) > sav.getSavingBalance()) {
                System.out.println("your balance in savingbalance is not enough");
                continue;
            }
            return Double.parseDouble(amount);
        }
    }

    private void takeBackAllMoney(User user1) {
        while (true) {
            System.out.println("1.Enter your idocument of saving fund\n2.quit");
            if ("2".equals(ScannerWrapper.nextLine())) {
                return;
            }
            String iDocument = ScannerWrapper.nextLine();
            for (SavingsCapitalFunds sav : user1.getAccount().getSavingFunds()) {
                if (iDocument.equals(sav.getIDocument())) {
                    user1.getAccount().setBalance(user1.getAccount().getBalance() + sav.getSavingBalance());
                    user1.getAccount().doCapitalFundsTrans(user1, user1, sav.getSavingBalance());
                    user1.getAccount().getTransactions().add(new Transaction(TransactionType.WITHDRAW, sav.getSavingBalance(), sav.getIDocument(), user1.getAccount().getiDocument(), Calendar.now(), user1.getFirstName(), user1.getLastName()));
                    user1.getAccount().getTransactions().sort(Transaction::compareTo);
                    sav.setSavingBalance(0);
                    sav.setActive(false);
                    user1.getAccount().getSavingFunds().remove(sav);
                    return;
                }
            }
            System.out.println("no sav capital fund found");
        }
    }

    private void printSavingFund(List<SavingsCapitalFunds> savingFunds) {
        int start = 0, end = start + 10;
        while (true) {
            savingFunds.stream().skip(start).limit(end - start + 1)
                    .forEach(savingFund -> System.out.println(savingFunds.indexOf(savingFund) + ": " + savingFund));
            System.out.println("1.previous page\n2.next page\n3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> {
                    if (start >= 10) {
                        start -= 10;
                        end -= 10;
                    }
                }
                case "2" -> {
                    start += 10;
                    end += 10;
                }
                case "3" -> {
                    return;
                }
                default -> {
                    System.out.println("you should choose 1-3");
                }
            }
        }
    }

}
