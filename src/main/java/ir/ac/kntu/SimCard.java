package ir.ac.kntu;

import static ir.ac.kntu.TransactionType.*;

import java.util.Map;

import ir.ac.kntu.fantesic.ScannerWrapper;
import ir.ac.kntu.util.Calendar;

public class SimCard {
    private String phoneNumber = new String();
    private double chargeBalance = 0.0;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getChargeBalance() {
        return chargeBalance;
    }

    public void setChargeBalance(double chargeBalance) {
        this.chargeBalance = chargeBalance;
    }

    public SimCard(String phoneNumber, double chargeBalance) {
        this.phoneNumber = phoneNumber;
        this.chargeBalance = chargeBalance;
    }

    public SimCard() {
    }

    public void chargeThisPhoneNumber(String phoneNumber, Map<User, String> users, User userSource) {
        double amount = userSource.enterAmountCharge();
        for (User userDest : users.keySet()) {
            if (userDest.getPhoneNumber().equals(phoneNumber)) {
                userDest.getSimCard().setChargeBalance(userDest.getSimCard().getChargeBalance() + amount);
                userSource.getAccount().setBalance(userSource.getAccount().getBalance() - (1+Amounts.getInChargePhoneNum() * amount));
                System.out.println("youe object simcard has charged" + " " + amount);
                userSource.getAccount().getTransactions()
                        .add(new Transaction(CHARGESIMCARD, amount, userSource.getAccount().getIDocument(),
                                phoneNumber, Calendar.now(), "girandeCharge", "girandeCharge"));
                userSource.getAccount().doCapitalFundsTrans(userSource, userDest, amount);
                return;
            }
        }
        SimCard simCard = new SimCard(phoneNumber, amount);
        DisconnectSimCards.add(simCard);
    }

    public void connectSimCardToNewUser(User tmp) {
        for (SimCard simCard : DisconnectSimCards.getSimcards()) {
            if (simCard.getPhoneNumber().equals(tmp.getPhoneNumber())) {
                tmp.setSimCard(simCard);
                return;
            }
        }
        tmp.setSimCard(new SimCard(tmp.getPhoneNumber(), 0));
    }

    public void buySimCardRecharge(Map<User, String> users, User userSource) {
        while (true) {
            System.out.println("1.Enter phoneNumber\n2.Choose from your contact\n3.Charge your Simcard\n4.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> userSource.enterPhoneNumber(users);
                case "2" -> userSource.showContactsCharge(users);
                case "3" -> new SimCard().chargeThisPhoneNumber(userSource.getPhoneNumber(), users, userSource);
                case "4" -> {
                    return;
                }
                default -> System.out.println("you should enter a number between 1-4");
            }
        }
    }

    @Override
    public String toString() {
        return "SimCard{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", chargeBalance=" + chargeBalance +
                '}';
    }
}
