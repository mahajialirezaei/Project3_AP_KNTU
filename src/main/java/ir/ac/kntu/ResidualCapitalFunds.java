package ir.ac.kntu;

public class ResidualCapitalFunds extends SavingsCapitalFunds {
    public ResidualCapitalFunds(double savingBalance, String iDocument) {
        super(savingBalance, iDocument);
    }

    public ResidualCapitalFunds() {
        super();
    }

    public ResidualCapitalFunds(String line) {
        super(line);
    }

    public void newTransSetResidual(User user1, double count) {
        int result = amountOfSetResidual(count);
        if (result <= user1.getAccount().getBalance()) {
            user1.getAccount().setBalance(user1.getAccount().getBalance() - result);
            setSavingBalance(getSavingBalance() + result);
        }
    }

    public int amountOfSetResidual(double count) {
        String strCount = String.valueOf((int) count);
        if (strCount.length() == 1) {
            return 0;
        }
        int len = (int) Math.floor(strCount.length() * 0.75);
        int start = strCount.length() - len;
        return (int) (Math.pow(10, len) - Integer.parseInt(strCount.substring(start, strCount.length())));
    }

    @Override
    public String toString() {
        return super.toString() + "ResidualCapitalFunds";
    }

    public String fileToString(User user) {
        return getSavingBalance() + "," + user.getAccount().getIDocument() + "\n";
    }
}
