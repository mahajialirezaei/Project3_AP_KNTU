package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.ac.kntu.fantesic.AutoMatic;
import ir.ac.kntu.fantesic.RunningManBye;
import ir.ac.kntu.fantesic.RunningManHello;
import ir.ac.kntu.fantesic.ScannerWrapper;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            RunningManHello.main(args);
            Bank bank = new Bank();
            readFiles(bank);
            // example(bank);
            AutoMatic autoMatic = new AutoMatic(bank);
            autoMatic.start();
            // String order = new String();
            menu(bank);
            writeFiles(bank);
            RunningManBye.main(args);
            autoMatic.setFinished(true);
            autoMatic.interrupt();
        } catch (Exception e) {

        }

    }

    public static void writeFiles(Bank bank) {
        try (BufferedWriter writerFile = new BufferedWriter(
                new FileWriter("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Users.csv"));) {
            writeToFileUser(bank, writerFile);
            writerFile.close();
        } catch (Exception e) {
            System.out.println("Did not write to file");
        }
        try (BufferedWriter writerFile = new BufferedWriter(
                new FileWriter("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Supports.csv"));) {
            writeToFileSupport(bank, writerFile);
            writerFile.close();
        } catch (Exception e) {
            System.out.println("Did not write to file");
        }
        try (BufferedWriter writerFile = new BufferedWriter(
                new FileWriter("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Managers.csv"));) {
            writeToFileManager(bank, writerFile);
            writerFile.close();
        } catch (Exception e) {
            System.out.println("Did not write to file");
        }
        try (BufferedWriter writerFile = new BufferedWriter(
                new FileWriter("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Requests.csv"));) {
            writeToFileRequests(bank, writerFile);
            writerFile.close();
        } catch (Exception e) {
            System.out.println("Did not write to file");
        }
        try (BufferedWriter writerBox = new BufferedWriter(
                new FileWriter("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\SaveBox.csv"));) {
            writeSavingBoxToFile(bank, writerBox);
            writerBox.close();
        } catch (Exception e) {
            System.out.println("Did not write to file");
        }
    }

    public static void readFiles(Bank bank) throws IOException {
        try (BufferedReader readerFile = new BufferedReader(
                new FileReader("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Users.csv"));) {
            readUsers(readerFile, bank);
        } catch (Exception e) {
            System.out.println("Did not read from file");
        }
        try (BufferedReader readerFile = new BufferedReader(
                new FileReader("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Supports.csv"));) {
            readSupports(readerFile, bank);
        } catch (Exception e) {
            System.out.println("Did not read from file");
        }
        try (BufferedReader readerFile = new BufferedReader(
                new FileReader("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Managers.csv"));) {
            readManagers(readerFile, bank);
        } catch (Exception e) {
            System.out.println("Did not read from file");
        }
        try (BufferedReader readerFile = new BufferedReader(
                new FileReader("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\Requests.csv"));) {
            readRequests(readerFile, bank);
        } catch (Exception e) {
            System.out.println("Did not read from file");
        }
        try (BufferedReader readerBox = new BufferedReader(
                new FileReader("C:\\Users\\farhadalirezaei\\OneDrive\\Desktop\\bankInfo\\SaveBox.csv"))) {
            readSaveBox(bank, readerBox);
        } catch (Exception e) {
            System.out.println("Did not read from file");
        }
    }

    public static void writeSavingBoxToFile(Bank bank, BufferedWriter writer) throws IOException {
        writer.write("Saving:\n");
        for (User user : bank.getUsers().keySet()) {
            writer.write(user.getAccount().savingBoxFile());
        }
        writer.write("Bonus:\n");
        for (User user : bank.getUsers().keySet()) {
            writer.write(user.getAccount().bonusBoxFile());
        }
        writer.write("Residual:\n");
        for (User user : bank.getUsers().keySet()) {
            if (user.getAccount().getResidualFunds().isActive()) {
                writer.write(user.getAccount().residualBoxFile(user));
            }
        }
        writer.write("End File");
        writer.close();
    }

    public static void readSaveBox(Bank bank, BufferedReader reader) throws IOException {
        String line = "";
        while ((line = reader.readLine()) != null) {
            if ("Saving:".equals(line)) {
                line = readSavingBox(reader, bank);
                line = "Bonus:";
            }
            if ("Bonus:".equals(line)) {
                line = readBonusBox(reader, bank);
                line = "Residual:";
            }
            if ("Residual:".equals(line)) {
                readResidualBox(reader, bank);
                break;
            }
        }
        reader.close();
    }

    public static String readResidualBox(BufferedReader reader, Bank bank) throws IOException {
        while (true) {
            String line = reader.readLine();
            if ("End File".equals(line)) {
                return line;
            }
            ResidualCapitalFunds fund = new ResidualCapitalFunds(line);
            User user = getThisUserPhoneNumber(bank, "0" + fund.getIDocument().substring(2, 12));
            user.getAccount().setResidualFunds(fund);
        }
    }

    public static String readBonusBox(BufferedReader reader, Bank bank) throws IOException {
        String line = "";
        while (true) {
            line = reader.readLine();
            if ("Residual:".equals(line)) {
                return line;
            }
            BonusCapitalFunds fund = new BonusCapitalFunds(line);
            fund.setActive(true);
            User user = getThisUserPhoneNumber(bank, "0" + fund.getIDocument().substring(2, 12));
            user.getAccount().getBonusFunds().add(fund);
        }
    }

    public static String readSavingBox(BufferedReader reader, Bank bank) throws IOException {
        while (true) {
            String line = reader.readLine();
            if ("Bonus:".equals(line)) {
                return line;
            }
            SavingsCapitalFunds fund = new SavingsCapitalFunds(line);
            User user = getThisUserPhoneNumber(bank, "0" + fund.getIDocument().substring(2, 12));
            user.getAccount().getSavingFunds().add(fund);
        }
    }

    public static void readRequests(BufferedReader reader, Bank bank) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            SupportRequest request = new SupportRequest(line);
            User user = getThisUserPhoneNumber(bank, request.getUserPhonenumber());
            bank.getRequests().put(request, user);
            user.getSupReqUser().put(request, user);
        }
    }

    public static User getThisUserPhoneNumber(Bank bank, String userPhonenumber) {
        for (User user : bank.getUsers().keySet()) {
            if (userPhonenumber.equals(user.getPhoneNumber())) {
                return user;
            }
        }
        return new User("sample", "sampleFamily", userPhonenumber, userPhonenumber.substring(1), userPhonenumber,
                Role.USER);
    }

    public static void readManagers(BufferedReader reader, Bank bank) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            Manager manager = new Manager(line, Role.MANAGER);
            bank.getManagers().put(manager, String.valueOf(manager.getPassword()));
        }
    }

    public static void readSupports(BufferedReader reader, Bank bank) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            Support support = new Support(line, Role.SUPPORT);
            bank.getSupports().put(support, String.valueOf(support.getPassword()));
            line = reader.readLine();
            line = reader.readLine();
            if ("this support end".equals(line)) {
                break;
            }
            support.setAccessArrayList(line);
            line = reader.readLine();
        }
    }

    public static void readUsers(BufferedReader reader, Bank bank) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            User user = new User(line, Role.USER);
            user.setAccount(new Account());
            String cardpassword = readUserChBalCardPassEtc(reader, bank, user, line);
            line = reader.readLine();
            readUsersTrans(reader, bank, line, user);
            readUserContacts(reader, bank, line, user);
            readUserFerriSituation(reader, bank, line, user);
            user.getAccount().setResidualFunds(new ResidualCapitalFunds(0, user.getAccount().getIDocument()));
            if (user.getAccount().isFeri()) {
                user.getAccount().setIDocument("IR" + user.getPhoneNumber().substring(1));
                user.getAccount().setCard(new Card(user.getPhoneNumber().substring(1), cardpassword));
            } else {
                user.getAccount().setIDocument("other" + user.getPhoneNumber().substring(1));
                user.getAccount().setCard(new Card(user.getPhoneNumber().substring(1), cardpassword));
            }
        }

    }

    public static String readUserChBalCardPassEtc(BufferedReader reader, Bank bank, User user, String line)
            throws IOException {
        bank.getUsers().put(user, String.valueOf(user.getPassword()));
        reader.readLine();
        line = reader.readLine();
        user.getAccount().setBalance(Double.parseDouble(line.split(",")[0]));
        String cardpassword = line.split(",")[1];
        user.setSimCard(new SimCard(user.getPhoneNumber(), Double.parseDouble(line.split(",")[2])));
        return cardpassword;
    }

    public static void readUserFerriSituation(BufferedReader reader, Bank bank, String line, User user)
            throws IOException {
        while (true) {
            line = reader.readLine();
            if ("this user end".equals(line)) {
                break;
            }
            user.getAccount().setFeri(Boolean.valueOf(line));
        }
    }

    public static void readUserContacts(BufferedReader reader, Bank bank, String line, User user) throws IOException {
        while (true) {
            line = reader.readLine();
            if ("FerriSituation".equals(line)) {
                break;
            }
            user.getUserContacts().add(new Contact(line));
        }
    }

    public static void readUsersTrans(BufferedReader reader, Bank bank, String line, User user) throws IOException {
        while (true) {
            line = reader.readLine();
            if ("UserAccountContacts".equals(line)) {
                break;
            }
            user.getAccount().getTransactions().add(new Transaction(line));
        }
    }

    public static void writeToFileRequests(Bank bank, BufferedWriter writer) throws IOException {
        bank.getRequests().keySet().stream().forEach(request -> {
            try {
                writer.write(request.fileToString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
            }
        });
    }

    public static void writeToFileManager(Bank bank, BufferedWriter writer) throws IOException {
        for (Manager manager : bank.getManagers().keySet()) {
            writer.write(manager.fileToString());
        }
    }

    public static void writeToFileSupport(Bank bank, BufferedWriter writer) throws IOException {
        for (Support support : bank.getSupports().keySet()) {
            writer.write(support.fileToString());
            writer.write("SupportAccess\n");
            writer.write(support.fileAccess());
            writer.write("this support end\n");
        }
    }

    public static void writeToFileUser(Bank bank, BufferedWriter writer) throws IOException {
        try {
            for (User user : bank.getUsers().keySet()) {
                writer.write(user.fileToString());
                writer.write("UserAccountBalance, User Card Pass, User Simcard Balance\n");
                writer.write(user.getAccount().getBalance() + "," + user.getAccount().getCard().getPassword() + ","
                        + user.getSimCard().getChargeBalance() + "\n");
                writer.write("UserAccountTransaction\n");
                writer.write(user.getAccount().fileTransaction());
                writer.write("UserAccountContacts\n");
                writer.write(user.fileContacts());
                writer.write("FerriSituation\n");
                writer.write(String.valueOf(user.getAccount().isFeri()) + "\n");
                writer.write("this user end\n");
            }
        } catch (Exception e) {//
            System.out.println("bye");
        }
    }

    public static void example(Bank bank) {
        Support support = new Support("dsdsd", "samplefamily", "48488484", "48488484", Role.SUPPORT);
        bank.getSupports().put(support, "fefdfrf%###R74");
        support.accessAdd(RequestSection.CONTACT);
        support.accessAdd(RequestSection.AUTHENTICATION);
        bank.getManagers().put(new Manager("1111", "1111", "1111", "Aa@1", 1, Role.MANAGER),
                "Aa@1");
        User otherUser = new User("s8", "s8", "09188888888", "0218888888", "other", Role.USER);
        List<Transaction> otherTrans = new ArrayList<Transaction>();
        otherUser.setAccount(new Account("other9188888888", 100, otherTrans, new Card("9188888888", "1111")));
        bank.getUsers().put(otherUser, "other");
    }

    public static void menu(Bank bank) {
        try {
            while (true) {
                System.out.println(Colors.ANSI_RED + "---------------------\n\n" + "Main Menu\n\n" + Colors.ANSI_BLUE
                        + "Choose Your Access" + Colors.ANSI_GREEN + "\n1.User\n2.Support\n3.Manager\n4.quit\n");
                String order = ScannerWrapper.nextLine();
                switch (order) {
                    case "1":
                        user(bank);
                        break;
                    case "2":
                        support(bank);
                        break;
                    case "3":
                        manager(bank);
                        break;
                    case "4":
                        return;
                    default:
                        System.out.println(Colors.colorString() + "You should choose 1-3\n");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(Colors.colorString() + "Enter a valid input");
            menu(bank);
        } finally {
            ScannerWrapper.closeScanner();
        }
    }

    private static void manager(Bank bank) {
        String order2 = new String();
        while (true) {
            System.out.println(
                    "Manager Menu\n\n" + "---------------------\n\n" + Colors.colorString() + "1.Log in\n2.quit\n");
            order2 = ScannerWrapper.nextLine();

            switch (order2) {
                case "1":
                    new Manager().logIn(bank);
                    break;
                case "2":
                    return;
                default:
                    System.out.println(Colors.colorString() + "You should choose 1-2");
                    break;
            }
        }
    }

    public static void support(Bank bank) {
        String order2 = new String();
        while (true) {
            System.out.println(
                    "Support Menu\n\n" + "---------------------\n\n" + Colors.colorString() + "1.Log in\n2.quit\n");
            order2 = ScannerWrapper.nextLine();

            switch (order2) {
                case "1":
                    new Support().logIn(bank);
                    break;
                case "2":
                    return;
                default:
                    System.out.println(Colors.colorString() + "You should choose 1-2");
                    break;
            }
        }
    }

    public static void user(Bank bank) throws IOException {
        String order2 = new String();
        while (true) {
            System.out.println(Colors.ANSI_BLUE + "User Menu\n\n" + Colors.colorString() + "1.Sign Up\n2.Log in\n3.quit"
                    + "\n\n---------------------\n\n");
            order2 = ScannerWrapper.nextLine();
            switch (order2) {
                case "1":
                    new User().signUp(bank);
                    break;
                case "2":
                    new User().logIn(bank);
                    break;
                case "3":
                    return;
                default:
                    System.out.println(Colors.ANSI_RED + "You should choose 1-3");
                    break;
            }
        }
    }
}
