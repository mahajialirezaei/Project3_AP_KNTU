package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PayaTrans {
    private User userSource;
    private double money;
    private String iDocumentDest;
    private User userDest;

    private static List<PayaTrans> payaTransList = new ArrayList<PayaTrans>();

    public void add() {
        payaTransList.add(this);
    }

    public User getUserSource() {
        return userSource;
    }

    public void setUserSource(User userSource) {
        this.userSource = userSource;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getiDocumentDest() {
        return iDocumentDest;
    }

    public void setiDocumentDest(String iDocumentDest) {
        this.iDocumentDest = iDocumentDest;
    }

    public User getUserDest() {
        return userDest;
    }

    public void setUserDest(User userDest) {
        this.userDest = userDest;
    }

    public static List<PayaTrans> getPayaTransList() {
        return payaTransList;
    }

    public static void setPayaTransList(List<PayaTrans> payaTransList) {
        PayaTrans.payaTransList = payaTransList;
    }

    public PayaTrans(User userSource, double money, String iDocumentDest, User userDest) {
        this.userSource = userSource;
        this.money = money;
        this.iDocumentDest = iDocumentDest;
        this.userDest = userDest;
    }

    public PayaTrans() {

    }

    public void doAllPayaTrans() {
        try {
            for (PayaTrans payaTrans : payaTransList) {
                payaTrans.getUserSource().getAccount().setBalance(payaTrans.userSource.getAccount().getBalance()
                        - new MethodTransfer().moneyAfterIncome(TransferTypeOther.PAYA, payaTrans.getMoney()));
                payaTrans.userDest.getAccount()
                        .setBalance(payaTrans.userDest.getAccount().getBalance() + payaTrans.getMoney());
                Transaction transaction = new MethodTransfer().transactionTransfer(
                        TransferType.valueOf(TransferTypeOther.PAYA.toString()),
                        payaTrans.userSource, payaTrans.userDest,
                        payaTrans.getMoney());
                payaTrans.userSource.getAccount().getLastAcountNumbers().add(payaTrans.getiDocumentDest());
                Comparator<Transaction> transComp = (trans1, trans2) -> trans1.compareTo(trans2);
                payaTrans.userSource.getAccount().getTransactions().sort(transComp);
                System.out.println("succesfully transfered");
                System.out.println(transaction);
                payaTransList.remove(payaTrans);
                userDest.getAccount().doCapitalFundsTrans(payaTrans.userSource, payaTrans.userDest,
                        payaTrans.getMoney());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
