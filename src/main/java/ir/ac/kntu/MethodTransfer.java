package ir.ac.kntu;

import static ir.ac.kntu.TransactionType.*;
import static ir.ac.kntu.TransferTypeOther.*;

import java.util.Map;

import ir.ac.kntu.fantesic.ScannerWrapper;
import ir.ac.kntu.util.Calendar;

public class MethodTransfer {
    public double moneyAfterIncome(TransferTypeOther transferTypeOther, double money) {
        switch (transferTypeOther) {
            case CARDTOCARD:
                return money + Amounts.getcToCardIncome();
            case POL:
                return (1 + Amounts.getPolIncome()) * money;
            case PAYA:
                return Amounts.getPayaIncome() + money;
            default:
                break;
        }
        return 0;
    }

    public void customCardTransfer(User user1, Map<User, String> users) {
        while (true) {
            String order1 = new String();
            System.out.println("1.Enter card number");
            System.out.println("2. quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    Object[] usersUser1 = { users, user1 };
                    finalNumCardTransfer(order1, users, user1, TransferType.CUSTOM);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void contactsTransfer(User user1, Map<User, String> users) {
        if (user1.getContactsFeature()) {
            printUserContacts(user1);
            while (true) {
                System.out.println(Colors.colorString() + "1. enter your object contact phonenumber\n2. quit");
                String order1 = ScannerWrapper.nextLine();
                switch (order1) {
                    case "1":
                        order1 = ScannerWrapper.nextLine();
                        if (order1.matches("\\d{11}")) {
                            if (user1.getAccount().mutualRelContact(order1, users, user1)) {
                                Object[] usersUser1 = { users, user1 };
                                Contact contact1 = user1.getAccount().getContactByPhonenumber(user1, order1);
                                finalTransfer(contact1.getIDocument(), users, user1, TransferType.CONTACT);
                            }
                        } else {
                            System.out.println("phone number structure is incorrect");
                        }
                        break;
                    case "2":
                        return;
                    default:
                        System.out.println("You should choose 1-2");
                        break;
                }
            }
        }
        System.out.println("Your contact feature is unavailable");
    }

    public void printUserContacts(User user1) {
        for (Contact contact : user1.getUserContacts()) {
            System.out.println(contact);
        }
    }

    public void customTransfer(User user1, Map<User, String> users) {
        while (true) {
            String order1 = new String();
            System.out.println("1.Enter account number");
            System.out.println("2. quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    finalTransfer(ScannerWrapper.nextLine(), users, user1, TransferType.CUSTOM);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void laAcTransfer(User userSource, Map<User, String> users) {
        while (true) {
            String order1 = new String();
            System.out.println("1.Enter account index");
            System.out.println("2. quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    if (!order1.matches("\\d+")) {
                        System.out.println("enter a number");
                        continue;
                    }
                    if (Integer.parseInt(order1) >= userSource.getAccount().getLastAcountNumbers().size()) {
                        System.out.println("enter a number between 0-"
                                + (userSource.getAccount().getLastAcountNumbers().size() - 1));
                        continue;
                    }
                    Object[] usersUser1 = { users, userSource };
                    finalTransfer((userSource.getAccount().getLastAcountNumbers().get(Integer.parseInt(order1))), users,
                            userSource, TransferType.CUSTOM);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public Transaction transactionTransferContact(User userSource, User userDest, double money) {
        for (Contact contact : userSource.getUserContacts()) {
            if (contact.getPhoneNumber().equals(userDest.getPhoneNumber())) {
                Transaction transaction = new Transaction(TRANSFERCONTACT, money,
                        userSource.getAccount().getIDocument(),
                        userDest.getAccount().getIDocument(), Calendar.now(), contact.getFirstName(),
                        contact.getLastName());
                userSource.getAccount().getTransactions()
                        .add(transaction);
                userDest.getAccount().getTransactions()
                        .add(transaction);
                return transaction;
            }
        }
        return new Transaction();
    }

    public Transaction transactionTransferCustom(User userSource, User userDest, double money) {
        Transaction transaction = new Transaction(TRANSFERCUSTOM, money, userSource.getAccount().getIDocument(),
                userDest.getAccount().getIDocument(), Calendar.now(), userDest.getFirstName(),
                userDest.getLastName());
        userSource.getAccount().getTransactions()
                .add(transaction);
        userDest.getAccount().getTransactions()
                .add(transaction);
        return transaction;
    }

    public void lastAccountNumberTransfer(User userSource, Map<User, String> users) {
        while (true) {
            userSource.getAccount().printLastAcountNumber(userSource.getAccount().getLastAcountNumber());
            // System.out.println(); // should prints Last account numbers
            // if (userSource.getAccount().getLastAcountNumbers().size() == 0) {
            // System.out.println(Colors.AQUA + "note: you have no any last account
            // number");
            // }
            System.out.println("1.continue and choose an account");
            System.out.println("2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> laAcTransfer(userSource, users);
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public Transaction transactionTransfer(TransferType transfertype, User userSource, User userDest, double money) {
        if (transfertype.equals(TransferType.CONTACT)) {
            return transactionTransferContact(userSource, userDest, money);
        }
        return transactionTransferCustom(userSource, userDest, money);
    }

    public void finalTransfer(String iDocument, Map<User, String> users, User userSource, TransferType transfertype) {
        for (User userTmp : users.keySet()) {
            if (userTmp.getAccount().getIDocument().equals(iDocument)) {
                if (!userTmp.getAccount().isFeri()) {
                    System.out.println("this account is not ferri");
                    return;
                }
                setHowMuchMoney(users, userSource, transfertype, iDocument);
                return;
            }
        }
        System.out.println("this iDocument for transfer money does not exist");
    }

    public void finalNumCardTransfer(String numberCard, Map<User, String> users, User user1,
            TransferType transfertype) {
        for (User userTmp : users.keySet()) {
            if (userTmp.getAccount().getCard().getIDocument().equals(numberCard)) {
                if (!userTmp.getAccount().isFeri()) {
                    System.out.println("this account is not ferri");
                    return;
                }
                setHowMuchMoney(users, user1, transfertype, userTmp.getAccount().getIDocument());
                return;
            }
        }
        System.out.println("this numbercard for transfer money does not exist");
    }

    public void finalNumCardTransfer(String numberCard, Map<User, String> users, User user1,
            TransferTypeOther transferTypeOther) {
        for (User userTmp : users.keySet()) {
            if (userTmp.getAccount().getCard().getIDocument().equals(numberCard)) {
                if (userTmp.getAccount().isFeri()) {
                    System.out.println("this account is ferri");
                    return;
                }
                setHowMuchMoney(users, user1, transferTypeOther, userTmp.getAccount().getIDocument());
                return;
            }
        }
        System.out.println("this numbercard for transfer money does not exist");
    }

    public void setHowMuchMoney(Map<User, String> users, User user1, TransferType transfertype, String iDocument) {
        while (true) {
            System.out.println("1.enter your money\n2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> enterMoney(users, user1, iDocument, transfertype);
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public void setHowMuchMoney(Map<User, String> users, User user1, TransferTypeOther transferTypeOther,
            String iDocument) {
        while (true) {
            System.out.println("1.enter your money\n2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> enterMoney(users, user1, iDocument, transferTypeOther);
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public void enterMoney(Map<User, String> users, User user1, String iDocument, TransferType transfertype) {
        String order2 = ScannerWrapper.nextLine();
        if (order2.matches("\\d+")) {
            double money = Integer.parseInt(order2);
            if (money <= user1.getAccount().getBalance()) {
                if (transfertype.equals(TransferType.CUSTOM)) {
                    incDecMoneyCustom(money, users, user1, iDocument);
                } else {
                    incDecMoneyContact(money, users, user1, iDocument);
                }
            } else {
                System.out.println("Your money is not enough");
            }
        } else {
            System.out.println("enter a number");
        }
    }

    public void enterMoney(Map<User, String> users, User userSource, String iDocument,
            TransferTypeOther transferTypeOther) {
        String order2 = ScannerWrapper.nextLine();
        if (order2.matches("\\d+")) {
            double money = Integer.parseInt(order2);
            if (moneyAfterIncome(transferTypeOther, money) <= userSource.getAccount()
                    .getBalance()) {
                incDecMoneyOther(money, new Object[] { users, userSource }, iDocument, transferTypeOther);
            } else {
                System.out.println("Your money is not enough");
            }
        } else {
            System.out.println("enter a number");
        }
    }

    public void incDecMoneyOther(double money, Object[] usersUserSource, String iDocument,
            TransferTypeOther transferTypeOther) {
        Map<User, String> users = (Map<User, String>) usersUserSource[0];
        User userSource = (User) usersUserSource[1];
        for (User userDest : users.keySet()) {
            if (userDest.getAccount().getIDocument().equals(iDocument)) {
                userSource.getAccount().showTransferdDetails(userDest, money);
                System.out.println("1.ACCEPT\n2.quit");
                String order1 = ScannerWrapper.nextLine();
                if ("1".equals(order1)) {
                    if (money <= getMaxAmountTransfer(transferTypeOther)) {
                        if (!transferTypeOther.equals(PAYA)) {
                            transferOther(new User[] { userSource, userDest }, money, iDocument, transferTypeOther);
                        } else {
                            new PayaTrans(userSource, money, iDocument, userDest).add();
                        }
                        userSource.getAccount().getLastAcountNumbers().add(iDocument);
                    } else {
                        System.out.println("the transfer limit has not been met");
                    }
                }
                return;
            }
        }
    }

    public void transferOther(User[] sourceDest, double money, String iDocument, TransferTypeOther transferTypeOther) {
        User userSource = sourceDest[0];
        User userDest = sourceDest[1];
        double userSourcemoney = userSource.getAccount().getBalance() - moneyAfterIncome(transferTypeOther, money);
        userSource.getAccount()
                .setBalance(userSourcemoney);
        userDest.getAccount().setBalance(userDest.getAccount().getBalance() + money);
        Transaction transaction = transactionTransfer(TransferType.valueOf(transferTypeOther.toString()), userSource,
                userDest, money);
        userSource.getAccount().getLastAcountNumbers().add(iDocument);
        userSource.getAccount().getTransactions().sort(Transaction::compareTo);
        System.out.println("succesfully transfered");
        System.out.println(transaction);
        userSource.getAccount().doCapitalFundsTrans(userSource, userDest, money);
    }

    public double getMaxAmountTransfer(TransferTypeOther transferTypeOther) {
        switch (transferTypeOther) {
            case CARDTOCARD:
                return 100000;
            case POL:
            case PAYA:
                return 5000000;
            default:
                break;
        }
        return 0;
    }

    public void incDecMoneyContact(double money, Map<User, String> users, User userSource, String iDocument) {
        for (User userDest : users.keySet()) {
            if (userDest.getAccount().getIDocument().equals(iDocument)
                    && userDest.getAuthentication() == AuthenticationType.ACCEPT) {
                userSource.getAccount().showTransferdDetails(userDest, money);
                System.out.println("1.ACCEPT\n2.quit");
                String order1 = ScannerWrapper.nextLine();
                if ("1".equals(order1)) {
                    if (money <= 8000000) {
                        userSource.getAccount().setBalance(userSource.getAccount().getBalance() - money);
                        userDest.getAccount().setBalance(userDest.getAccount().getBalance() + money);
                        Transaction transaction = transactionTransfer(TransferType.CONTACT, userSource, userDest,
                                money);
                        userSource.getAccount().getLastAcountNumbers().add(iDocument);
                        userSource.getAccount().getTransactions().sort(Transaction::compareTo);
                        System.out.println("succesfully transfered");
                        System.out.println(transaction);
                        userSource.getAccount().doCapitalFundsTrans(userSource, userDest, money);
                    } else {
                        System.out.println("the transfer limit has not been met");
                    }
                }
                return;
            }
        }
        System.out.println("not found");
    }

    public void incDecMoneyCustom(double money, Map<User, String> users, User userSource, String iDocument) {
        for (User userDest : users.keySet()) {
            if (userDest.getAccount().getIDocument().equals(iDocument)
                    && userDest.getAuthentication() == AuthenticationType.ACCEPT) {
                userSource.getAccount().showTransferdDetails(userDest, money);
                System.out.println("1.ACCEPT\n2.quit");
                String order1 = ScannerWrapper.nextLine();
                if ("1".equals(order1)) {
                    if (money <= 8000000) {
                        userSource.getAccount().setBalance(userSource.getAccount().getBalance() - money);
                        userDest.getAccount().setBalance(userDest.getAccount().getBalance() + money);
                        Transaction transaction = transactionTransfer(TransferType.CUSTOM, userSource, userDest, money);
                        userSource.getAccount().getLastAcountNumbers().add(iDocument);
                        userSource.getAccount().getTransactions().sort(Transaction::compareTo);
                        System.out.println("succesfully transfered");
                        System.out.println(transaction);
                        userSource.getAccount().doCapitalFundsTrans(userSource, userDest, money);
                    } else {
                        System.out.println("the transfer limit has not been met");
                    }
                }
                return;
            }
        }
        System.out.println("not found");
    }
}
