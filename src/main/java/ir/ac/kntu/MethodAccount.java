package ir.ac.kntu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ir.ac.kntu.fantesic.ScannerWrapper;
import ir.ac.kntu.util.Calendar;

public class MethodAccount {
    public void accountManagement(User user1) throws IOException {
        while (true) {
            System.out.println("ACCOUNT MANAGEMENT" + "\n\n---------------------\n\n" + "1.charge account");
            System.out.println("2. show balance");
            System.out.println("3.show transaction list");
            System.out.println("4.show simcard Recharge balance");
            System.out.println("5.quit");
            String order = ScannerWrapper.nextLine();
            switch (order) {
                case "1" -> charge(user1);
                case "2" -> showBalance(user1);
                case "3" -> showTransactionList(user1);
                case "4" -> showSimCardBalance(user1);
                case "5" -> {
                    return;
                }
                default -> System.out.println(Colors.ANSI_RED + "you should choose 1-5");
            }
        }
    }

    private void showSimCardBalance(User user1) {
        System.out.println(user1.getSimCard());
    }

    public void showTransactionList(User user1) throws IOException {
        while (true) {
            String order1 = new String();
            System.out.println("show Transaction list menu" + "\n\n---------------------\n\n"
                    + "1.Without filter\n2. with filter\n3.see Tables of Transaction\n4.quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "2" ->
                    printLimitedList(showTransactionListFilter(user1));
                case "1" -> {
                    for (Contact contact : user1.getUserContacts()) {
                        contact.changeTransactions(user1, contact);
                    }
                    printLimitedList(user1.getAccount().getTransactions());
                }
                case "3" -> HtmlFile.makeTableHtml(user1.getAccount().getTransactions());
                case "4" -> {
                    return;
                }
                default ->
                    System.out.println(Colors.ANSI_RED + "you should choose 1-4");
            }

        }
    }

    public void printLimitedList(List<Transaction> list) {
        int start = 0, end = start + 10;
        while (true) {
            list.stream().skip(start).limit(end - start + 1)
                    .forEach(entry -> System.out.println(list.indexOf(entry) + ": " + entry));
            System.out.println("1.previous page\n2.next page\n3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    if (start >= 10) {
                        start -= 10;
                        end -= 10;
                    }
                    break;
                case "2":
                    start += 10;
                    end += 10;
                    break;
                case "3":
                    return;
                default:
                    System.out.println("you should choose 1-3");
                    break;
            }
        }
    }

    public List<Transaction> showTransactionListFilter(User user1) {
        String[] fields = new String[6];
        while (!scanFilterFields(fields)) {
            System.out.println("enter a number");
        }
        return finalShowTransactionFilter(user1, fields);
    }

    public List<Transaction> finalShowTransactionFilter(User user1, String[] fields) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        for (Transaction transaction : user1.getAccount().getTransactions()) {
            if (checkTransactionCondition(fields, transaction)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public boolean checkTransactionCondition(String[] fields, Transaction transaction) {
        Date date1 = new Date(fields[0], fields[1], fields[2]);
        Date date2 = new Date(fields[3], fields[4], fields[5]);
        return transaction.getDate().sub() >= date1.sub() && transaction.getDate().sub() <= date2.sub();
    }

    public boolean scanFilterFields(String[] fields) {
        System.out.println("Enter start year");
        fields[0] = ScannerWrapper.nextLine();
        System.out.println("Enter start month");
        fields[1] = ScannerWrapper.nextLine();
        System.out.println("Enter start day");
        fields[2] = ScannerWrapper.nextLine();
        System.out.println("Enter end year");
        fields[3] = ScannerWrapper.nextLine();
        System.out.println("Enter ean month");
        fields[4] = ScannerWrapper.nextLine();
        System.out.println("Enter end day");
        fields[5] = ScannerWrapper.nextLine();
        for (String string : fields) {
            if (!string.matches("\\d+")) {
                return false;
            }
        }
        return true;
    }

    public void showBalance(User user1) {
        System.out.println("you balance is " + user1.getAccount().getBalance());
    }

    public void charge(User userDest) {
        String input = new String();
        System.out.println("enter your money or quit");
        input = ScannerWrapper.nextLine();
        if ("quit".equals(input)) {
            return;
        } else if (input.matches("\\d+")) {
            double amount = Double.parseDouble(input);
            userDest.getAccount().setBalance(userDest.getAccount().getBalance() + amount);
            userDest.getAccount().getTransactions()
                    .add(new Transaction(TransactionType.CHARGE, amount, "charge", userDest.getAccount().getIDocument(),
                            Calendar.now(), userDest.getFirstName(), userDest.getLastName()));
            userDest.getAccount().doCapitalFundsTrans(userDest, userDest, amount);
            Comparator<Transaction> transComp = (trans1, trans2) -> trans1.compareTo(trans2);
            userDest.getAccount().getTransactions().sort(transComp);
        } else {
            System.out.println("input a number");
        }
    }

    public void addContact(User user1, Map<User, String> users) {
        String[] fields = new String[3];
        while (true) {
            System.out.println("do you want to continue\n write every thing else 2 to continue");
            System.out.println("2.quit");
            String order1 = ScannerWrapper.nextLine();
            if ("2".equals(order1)) {
                return;
            }
            System.out.println("Enter your contact firstname");
            fields[0] = ScannerWrapper.nextLine();
            System.out.println("Enter your contact lastname");
            fields[1] = ScannerWrapper.nextLine();
            System.out.println("Enter your contact phonenumber");
            fields[2] = ScannerWrapper.nextLine();
            boolean check1 = user1.checkCondition(fields, user1), check2 = user1.checkEmptyContact(fields);
            if (!(check1 && !check2)) {
                return;
            }
            Contact contact1 = new Contact(fields[0], fields[1], fields[2]);
            user1.getUserContacts().add(contact1);

        }
    }

}
