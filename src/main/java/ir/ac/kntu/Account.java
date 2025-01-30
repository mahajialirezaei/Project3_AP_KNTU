package ir.ac.kntu;

import static ir.ac.kntu.TransferTypeOther.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class Account {
    private String iDocument;
    private AccountType accountType;
    private double balance;
    private List<Transaction> transactions = new ArrayList<>();
    private Card card;
    private List<String> lastAcountNumber = new ArrayList<>();

    private List<SavingsCapitalFunds> savingFunds = new ArrayList<>();

    private List<BonusCapitalFunds> bonusFunds = new ArrayList<>();

    private ResidualCapitalFunds residualFunds = new ResidualCapitalFunds();

    private boolean isFeri;

    public String getiDocument() {
        return iDocument;
    }

    public void setiDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public boolean isFeri() {
        return isFeri;
    }

    public void setFeri(boolean feri) {
        this.isFeri = feri;
    }

    public List<String> getLastAcountNumber() {
        return this.lastAcountNumber;
    }

    public List<SavingsCapitalFunds> getSavingFunds() {
        return this.savingFunds;
    }

    public void setSavingFunds(List<SavingsCapitalFunds> savingFunds) {
        this.savingFunds = savingFunds;
    }

    public List<BonusCapitalFunds> getBonusFunds() {
        return bonusFunds;
    }

    public void setBonusFunds(List<BonusCapitalFunds> bonusFunds) {
        this.bonusFunds = bonusFunds;
    }

    public ResidualCapitalFunds getResidualFunds() {
        return residualFunds;
    }

    public void setResidualFunds(ResidualCapitalFunds residualFunds) {
        this.residualFunds = residualFunds;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void printSavingCapitalFunds() {
        Stream.of(savingFunds).forEach(System.out::println);
    }

    public void printBonusCapitalFunds() {
        Stream.of(bonusFunds).forEach(System.out::println);
    }

    public void printLastAcountNumber(List<String> lasAcNumbers) {
        if (lasAcNumbers.size() == 0) {
            System.out.println("you have no last account number");
            return;
        }
        int start = 0, end = start + 10;
        while (true) {
            lasAcNumbers.stream().skip(start).limit(end - start + 1)
                    .forEach(lasAcNumber -> System.out.println(lasAcNumbers.indexOf(lasAcNumber) + ": " + lasAcNumber));
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

    public List<String> getLastAcountNumbers() {
        return this.lastAcountNumber;
    }

    public void setLastAcountNumber(List<String> lastAcountNumber) {
        this.lastAcountNumber = lastAcountNumber;
    }

    public Account(String iDocument, int balance, List<Transaction> transactions, Card card) {
        setBalance(balance);
        setCard(card);
        setIDocument(iDocument);
        setTransactions(transactions);
        setResidualFunds(residualFunds);
    }

    public Account() {

    }

    public String getIDocument() {
        return this.iDocument;
    }

    public void setIDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public String printTrans() {
        String natije = "";
        for (int i = 0; i < this.getTransactions().size(); i++) {
            natije += getTransactions().get(i) + "\n";
        }
        return natije;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setCard(String iDocument) {
        this.setCard(new Card(iDocument, "1111"));
    }

    @Override
    public String toString() {
        return "{" + " iDocument='" + getIDocument() + "'" + ", balance='" + getBalance() + "'" + ", transactions='"
                + getTransactions() + "'" + ", card='" + getCard() + "'" + "}";
    }

    public static void changeCardPassword(User user1) {
        while (true) {
            System.out.println("enter your current card password or type 1 to quit");
            String order1 = ScannerWrapper.nextLine();
            if ("1".equals(order1)) {
                return;
            }
            if (user1.getAccount().getCard().getPassword().equals(order1)) {
                System.out.println("Enter a 4 digit number");
                order1 = ScannerWrapper.nextLine();
                if (order1.matches("\\d{4}")) {
                    user1.getAccount().getCard().setPassword(order1);
                    System.out.println("succesfully changed");
                    return;
                } else {
                    System.out.println("password structure is not correct");
                }
            } else {
                System.out.println("password is not correct");
            }
        }
    }

    public static void changePersonalPassword(User user1) {
        while (true) {
            System.out.println("Enter Your currentpassword");
            String order1 = ScannerWrapper.nextLine();
            if (user1.getPassword() == Objects.hash(order1)) {
                System.out.println("Enter your new password");
                order1 = ScannerWrapper.nextLine();
                if (!(order1.matches(".*[A-Z]+.*") && order1.matches(".*[a-z]+.*") && order1.matches(".*[0-9]+.*")
                        && order1.matches(".*[!|@|#|$|%|^|&|*|(|)|_|+|-|?|\\|/]+.*"))) {
                    System.out.println("choose an strong password");
                } else {
                    user1.setPassword(order1);
                    System.out.println("succesfully changed");
                }
            } else {
                System.out.println("not correct");
            }
            System.out.println("do you want to continue");
            System.out.println("1.continue");
            System.out.println("2.quit");
            order1 = ScannerWrapper.nextLine();
            if ("2".equals(order1)) {
                return;
            }
        }
    }

    public void chooseDestAccountType(User user1, Map<User, String> users) {
        while (true) {
            System.out.println("choose Dest account type\n\n1.To a ferri card\n2. to an other account\n3. quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    moneyTransferFerri(user1, users);
                    break;
                case "2":
                    moneyTransferOther(user1, users);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("you should enter 1-3");
                    break;
            }
        }
    }

    public void moneyTransferFerri(User user1, Map<User, String> users) {
        while (true) {
            String order1 = "";
            System.out.println("1.custom\n2.last account number\n3. my contacts\n4.number card\n5. quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    new MethodTransfer().customTransfer(user1, users);
                    break;
                case "2":
                    new MethodTransfer().lastAccountNumberTransfer(user1, users);
                    break;
                case "3":
                    new MethodTransfer().contactsTransfer(user1, users);
                    break;
                case "4": {
                    if (checkCardPassword()) {
                        new MethodTransfer().customCardTransfer(user1, users);
                    }
                }
                    break;
                case "5":
                    return;
                default:
                    System.out.println("you should choose 1-5");
                    break;
            }

        }
    }

    public void moneyTransferOther(User user1, Map<User, String> users) {
        while (true) {
            String order1 = "";
            System.out.println("1. card to card\n2.account pol\n3. account paya\n4. quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> {
                    if (checkCardPassword()) {
                        cardToCardOther(user1, users);
                    }
                }
                case "2" -> polPayaTrans(user1, users, POL);

                case "3" -> polPayaTrans(user1, users, PAYA);

                case "4" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }

        }
    }

    private boolean checkCardPassword() {
        while (true) {
            System.out.println("1.enter your card password\n2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> {
                    if (ScannerWrapper.nextLine().equals(getCard().getPassword())) {
                        return true;
                    }
                    System.out.println("your password is not correct");
                    return false;
                }
                case "2" -> {
                    return false;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public void cardToCardOther(User user1, Map<User, String> users) {
        while (true) {
            System.out.println("1.Enter card number");
            System.out.println("2. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1":
                    Object[] usersUser1 = { users, user1 };
                    new MethodTransfer().finalNumCardTransfer(ScannerWrapper.nextLine(), users, user1, CARDTOCARD);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void polPayaTrans(User user1, Map<User, String> users, TransferTypeOther transferTypeOther) {
        while (true) {
            System.out.println("1.Enter account number");
            System.out.println("2. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> {
                    finalOtherTransfer(ScannerWrapper.nextLine(), users, user1, transferTypeOther);
                }
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public void finalOtherTransfer(String iDocument, Map<User, String> users, User user1,
            TransferTypeOther transferTypeOther) {
        for (User userTmp : users.keySet()) {
            if (userTmp.getAccount().getIDocument().equals(iDocument)) {
                if (userTmp.getAccount().isFeri()) {
                    System.out.println("this account is ferri");
                    return;
                }
                new MethodTransfer().setHowMuchMoney(users, user1, transferTypeOther,
                        userTmp.getAccount().getIDocument());
                return;
            }
        }
        System.out.println("this iDocument for transfer money does not exist");
    }

    public void doCapitalFundsTrans(User userSource, User userDest, double amount) {
        if (userDest.getAccount().getResidualFunds().isActive()) {
            userDest.getAccount().getResidualFunds().newTransSetResidual(userDest, amount);
            isRound(userDest);
            double res = userDest.getAccount().getBalance() % 1;
            if (res != 0) {
                userDest.getAccount().setBalance((int) userDest.getAccount().getBalance());
                userDest.getAccount().getResidualFunds()
                        .setSavingBalance(userDest.getAccount().getResidualFunds().getSavingBalance() + res);
            }
        }
        if (userSource.getAccount().getResidualFunds().isActive() && !userSource.equals(userDest)) {
            userSource.getAccount().getResidualFunds().newTransSetResidual(userSource, amount);
            isRound(userSource);
            double res = userSource.getAccount().getBalance() % 1;
            if (res != 0) {
                userSource.getAccount().setBalance((int) userSource.getAccount().getBalance());
                userSource.getAccount().getResidualFunds()
                        .setSavingBalance(userSource.getAccount().getResidualFunds().getSavingBalance() + res);
            }
        }
    }

    private void isRound(User userDest) {
        int ragham = String.valueOf((int) userDest.getAccount().getResidualFunds().getSavingBalance()).length();
        ragham = ragham - 1;
        if (userDest.getAccount().getResidualFunds().getSavingBalance() % Math.pow(10, ragham) == 0) {
            userDest.getAccount().setBalance(
                    userDest.getAccount().getBalance() + userDest.getAccount().getResidualFunds().getSavingBalance());
            userDest.getAccount().getResidualFunds().setSavingBalance(0);
        }
    }

    public void showTransferdDetails(User user, double money) {
        System.out.println("user firstname" + user.getFirstName() + "\n" + "user lastname" + user.getLastName() + "\n"
                + "money:" + money);
    }

    public boolean mutualRelContact(String order1, Map<User, String> users, User user1) {
        boolean check1 = false, check2 = false, check3 = false;
        Contact contact1 = getContactByPhonenumber(user1, order1);
        for (User user : users.keySet()) {
            if (contact1 != null && user.getAccount().getIDocument().equals(contact1.getIDocument())) {
                for (Contact contact : user.getUserContacts()) {
                    if (contact.getIDocument().equals(user1.getAccount().getIDocument())) {
                        check1 = true;
                        if (user.getContactsFeature()) {
                            check3 = user.getContactsFeature();
                        }
                        break;
                    }
                }
            }
        }
        for (Contact contact : user1.getUserContacts()) {
            if (contact.getPhoneNumber().equals(order1)) {
                check2 = true;
                break;
            }
        }
        if (!check3 || !check2 || !check1) {
            System.out.println(
                    "this contact contact feature is not available or this phone number is not in your contacts or you are not in this contact");
        }
        return check2 && check1 && check3;
    }

    public Contact getContactByPhonenumber(User user1, String order1) {
        for (Contact contact : user1.getUserContacts()) {
            if (contact.getPhoneNumber().equals(order1)) {
                return contact;
            }
        }
        return null;
    }

    public String fileTransaction() {
        return getTransactions().stream().map(Transaction::fileToString).reduce("", (a1, a2) -> a1 + a2);
    }

    public String savingBoxFile() {
        return this.getSavingFunds().stream().filter(SavingsCapitalFunds::isActive)
                .map(SavingsCapitalFunds::fileToString).reduce("", (a, b) -> a + b);
    }

    public String bonusBoxFile() {
        return this.getBonusFunds().stream().filter(BonusCapitalFunds::isActive).map(BonusCapitalFunds::fileToString)
                .reduce("", (a, b) -> a + b);
    }

    public String residualBoxFile(User user) {
        return this.getResidualFunds().fileToString(user);
    }
}
