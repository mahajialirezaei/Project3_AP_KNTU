package ir.ac.kntu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class User extends Person {
    private Account account = new Account();
    private List<Contact> userContacts = new ArrayList<>();
    private Map<SupportRequest, User> supReqUser = new HashMap<>();
    private boolean contactsFeature = true;

    private SimCard simCard;

    public User(String line, Role role) {
        super(line, role);
    }

    public SimCard getSimCard() {
        return simCard;
    }

    public void setSimCard(SimCard simCard) {
        this.simCard = simCard;
    }

    public boolean getContactsFeature() {
        return this.contactsFeature;
    }

    public Map<SupportRequest, User> getSupReqUser() {
        return this.supReqUser;
    }

    public void setSupReqUser(Map<SupportRequest, User> supReqUser) {
        this.supReqUser = supReqUser;
    }

    public void setContactsFeature(boolean contactsFeature) {
        this.contactsFeature = contactsFeature;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAccount(String iDocument) {
        this.account.setIDocument(iDocument);
        this.account.setCard(new Card(iDocument.substring(2), "1111"));
    }

    public List<Contact> getUserContacts() {
        return this.userContacts;
    }

    public void setUserContacts(List<Contact> userContacts) {
        this.userContacts = userContacts;
    }

    public User() {
        super();
    }

    public User(String firstName, String lastName, String phoneNumber, String iDocument, String password,
                Role userRole) {
        super(firstName, lastName, iDocument, password, userRole);
        setPhoneNumber(phoneNumber);
        setIDocument(iDocument);
    }

    public boolean signUp(Bank bank) {
        while (true) {
            String[] fields = new String[5];
            System.out.println(Colors.LAVENDER + "Enter Your firstname");
            fields[0] = ScannerWrapper.nextLine();
            System.out.println("Enter Your lastname");
            fields[1] = ScannerWrapper.nextLine();
            System.out.println("Enter Your phonenumber");
            fields[2] = ScannerWrapper.nextLine();
            System.out.println("Enter Your id");
            fields[3] = ScannerWrapper.nextLine();
            System.out.println("Enter Your password");
            fields[4] = ScannerWrapper.nextLine();
            boolean check1 = checkEmptyUser(fields), check2 = checkCondition(fields, bank.getUsers()),
                    check3 = User.checkName(fields);
            if (!(check2 && !check1 && check3)) {
                return false;
            }
            trueSignUp(bank, fields);
            return true;
        }
    }

    private void trueSignUp(Bank bank, String[] fields) {
        User tmp = new User(fields[0], fields[1], fields[2], fields[3], fields[4], Role.USER);
        bank.getUsers().put(tmp, fields[4]);
        new SimCard().connectSimCardToNewUser(tmp);
        Request tmpRequest = new Request(AuthenticationType.INPROGRESS, "", RequestSection.AUTHENTICATION,
                RequestCondition.INPROGRESS, tmp.getPhoneNumber());
        bank.getRequests().put(tmpRequest, tmp);
        tmp.getSupReqUser().put(tmpRequest, tmp);
        tmp.setAccount(new Account());
        tmp.getAccount().setFeri(true);
        tmp.getAccount().setCard(new Card());
        System.out.println(
                Colors.AQUA + "Successfully Signed up\n" + "your card password after authentication will be 1111");
    }

    public boolean checkEmptyPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 0) {
            System.out.println("phoneNumber field hasn't itialized");
            return true;
        }
        return false;
    }

    public boolean checkEmptyLastName(String lastName) {
        if (lastName.length() == 0) {
            System.out.println("lastname field hasn't itialized");
            return true;
        }
        return false;
    }

    public boolean checkEmptyFirstName(String firstName) {
        if (firstName.length() == 0) {
            System.out.println("firstname field hasn't itialized");
            return true;
        }
        return false;
    }

    public boolean checkEmptyIDocument(String iDocument) {
        if (iDocument.length() == 0) {
            System.out.println("iDocument field hasn't itialized");
            return true;
        }
        return false;
    }

    public boolean checkEmptyPassword(String password) {
        if (password.length() == 0) {
            System.out.println("password field hasn't itialized");
            return true;
        }
        return false;
    }

    public boolean checkCondition(String[] fields, User user1) {
        if (fields[2].length() > 0) {
            if (!fields[2].matches("\\d{11}")) {
                System.out.println("Invalid phonenumber");
                return false;
            }
        }
        if (fields[2].length() > 0) {
            for (Contact contact1 : user1.getUserContacts()) {
                if (contact1.getPhoneNumber().equals(fields[2])) {
                    System.out.println("Repititous phonenumber");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkEmptyContact(String[] fields) {
        return checkEmptyFirstName(fields[0]) || checkEmptyLastName(fields[1]) || checkEmptyPhoneNumber(fields[2]);
    }

    public boolean checkEmptyUser(String[] fields) {
        return checkEmptyFirstName(fields[0]) || checkEmptyLastName(fields[1]) || checkEmptyPhoneNumber(fields[2])
                || checkEmptyIDocument(fields[3]) || checkEmptyPassword(fields[4]);

    }

    public boolean checkEmptyField(String string) {
        if (string.length() == 0) {
            System.out.println("field hasn't itialized");
            return true;
        }
        return false;
    }

    public void edit(Bank bank) {
        while (true) {
            String order1 = new String();
            System.out.println(Request.getrequest(this, bank.getRequests()).getAnswer());
            System.out.println("You could edit your information to set a new request");
            System.out.println("1.edit");
            System.out.println("2.quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    Request.getrequest(this, bank.getRequests()).setAuthentication(AuthenticationType.INPROGRESS);
                    editInformation(this, bank.getUsers());
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void editInformation(User user1, Map<User, String> users) {
        while (true) {
            String[] fields = new String[5];
            System.out.println(
                    "choose 1-6\n1.edit firstname\n2.edit lastname\n3.edit phonenumber\n4.edit Id\n5.edit password\n6.quit");
            String order1 = ScannerWrapper.nextLine();
            if ("6".equals(order1)) {
                return;
            }
            boolean check1 = editInformationSwitch(fields, order1), check2 = user1.checkCondition(fields, users);
            setUserFields(user1, order1, check1 && check2, fields);
        }
    }

    public void setUserFields(User user1, String order1, boolean check2, String[] fields) {
        if (check2) {
            switch (order1) {
                case "1":
                    user1.setFirstName(fields[0]);
                    break;
                case "2":
                    user1.setLastName(fields[1]);
                    break;
                case "3":
                    user1.setPhoneNumber(fields[2]);
                    break;
                case "4":
                    user1.setIDocument(fields[3]);
                    break;
                case "5":
                    user1.setPassword(fields[4]);
                    break;
                default:
                    break;
            }
            System.out.println("user has been saved");
        }
    }

    public boolean editInformationSwitch(String[] fields, String order1) {
        int index = 0;
        if (order1.matches("[1-5]")) {
            index = Integer.parseInt(order1) - 1;
            fields[index] = ScannerWrapper.nextLine();
        } else {
            System.out.println("You should choose 1-6");
            return false;
        }
        return !checkEmptyField(fields[index]);
    }

    public boolean checkCondition(String[] fields, Map<User, String> users) {
        if (!(fields[2] == null)) {
            if (!fields[2].matches("\\d{11}")) {
                System.out.println(Colors.colorString() + "Invalid phonenumber");
                return false;
            }
        }
        if (!(fields[3] == null)) {
            System.out.println(fields[3]);
            if (!(fields[3].matches("\\d{10}"))) {
                System.out.println("Invalid id");
                return false;
            }
        }
        if (!(fields[4] == null)) {
            if (!(fields[4].matches(".*[A-Z]+.*") && fields[4].matches(".*[a-z]+.*") && fields[4].matches(".*[0-9]+.*")
                    && fields[4].matches(".*[!|@|#|$|%|^|&|*|(|)|_|+|-|?|\\|/]+.*"))) {
                System.out.println(Colors.colorString() + "choose an strong password");
                return false;
            }
        }
        for (User user1 : users.keySet()) {
            if (user1.getIDocument().equals(fields[3])) {
                System.out.println("Repetitious id");
                return false;
            }
            if (user1.getPhoneNumber().equals(fields[2])) {
                System.out.println("Repititous phonenumber");
                return false;
            }
        }
        return true;
    }

    public void logIn(Bank bank) throws IOException {
        while (true) {
            System.out.println(Colors.colorString() + "Enter Your phoneNumber");
            String phoneNumber = ScannerWrapper.nextLine();
            System.out.println("Enter Your password");
            String password = ScannerWrapper.nextLine();
            for (User user1 : bank.getUsers().keySet()) {
                if (user1.getPhoneNumber().equals(phoneNumber) && user1.getPassword() == Objects.hash(password)
                        && !user1.isBlocked() && user1.getAccount().isFeri()) {
                    System.out.println("Wellcome");
                    logedIn(user1, bank);
                    return;
                }
                if (user1.getPhoneNumber().equals(phoneNumber) && user1.getPassword() == Objects.hash(password)
                        && user1.isBlocked()) {
                    System.out.println("you have been blocked");
                    return;
                }
            }
            System.out.println(Colors.colorString()
                    + "phonenumber or password is incorrect\ndo you want to continue\n1.Yes\n2.quit");
            String order1 = ScannerWrapper.nextLine();
            if ("2".equals(order1)) {
                return;
            } else if (!"1".equals(order1)) {
                System.out.println("You should choose 1-2");
            }
        }
    }

    public void logedIn(User user1, Bank bank) throws IOException {
        if (user1.getAuthentication() == AuthenticationType.ACCEPT) {
            while (true) {
                String order = "";
                System.out.println(Colors.colorString()
                        + "1.Account Management\n2.Contacts\n3.Money Transfer\n4.Support\n5.Settings\n6.Capital Funds\n7.BUY SIM Card Recharge\n8. quit");
                order = ScannerWrapper.nextLine();
                if ("8".equals(order)) {
                    return;
                }
                user1.setAfterLogedIn(order, bank);
            }
        } else if (user1.getAuthentication() == AuthenticationType.REJECT) {
            user1.edit(bank);
        } else if (user1.getAuthentication() == AuthenticationType.INPROGRESS) {
            user1.setBeforeLogedIn(bank);
        }
    }

    private void setBeforeLogedIn(Bank bank) {
        while (true) {
            String order = "";
            System.out.println(Colors.colorString() + "Support has not been checked your request");
            System.out.println(Colors.colorString() + "your password card after authentication will be 1111");
            System.out.println(Colors.colorString() + "1.edit information\n2.Support\n3. quit");
            order = ScannerWrapper.nextLine();
            switch (order) {
                case "1" -> this.edit(bank);
                case "2" -> new MethodSupport().support(this, bank.getRequests());
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }
    }

    public void setAfterLogedIn(String order, Bank bank) throws IOException {
        switch (order) {
            case "1" -> new MethodAccount().accountManagement(this);
            case "2" -> new Contact().contacts(this, bank.getUsers());
            case "3" -> this.getAccount().chooseDestAccountType(this, bank.getUsers());
            case "4" -> new MethodSupport().support(this, bank.getRequests());
            case "5" -> this.settings();
            case "6" -> new SavingsCapitalFunds().capitalFunds(this);
            case "7" -> this.getSimCard().buySimCardRecharge(bank.getUsers(), this);
            default -> System.out.println(Colors.colorString() + "You should choice 1-8");
        }
    }

    public void settings() {
        while (true) {
            System.out.println(Colors.colorString()
                    + "1.change personal password\n2.change card password\n3.change contacts feature\n4.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> Account.changePersonalPassword(this);
                case "2" -> Account.changeCardPassword(this);
                case "3" -> new Contact().changeContactsFeature(this);
                case "4" -> {
                    return;
                }
                default -> System.out.println(Colors.colorString() + "You should choose 1-4");
            }
        }
    }

    @Override
    public String toString() {
        return "User{" + super.toString() + "}";
    }

    public static boolean checkName(String[] fields) {
        if (!fields[0].matches("[a-zA-Z ]+")) {
            System.out.println(Colors.ANSI_RED + "Invalid firstname");
            return false;
        }
        if (!fields[1].matches("[a-zA-Z ]+")) {
            System.out.println(Colors.ANSI_RED + "Invalid lastname");
            return false;
        }
        return true;
    }

    public void showContactsCharge(Map<User, String> users) {
        new Contact().printLimitedWthOutFltr(this.getUserContacts());
        while (true) {
            System.out.println("1. enter contact phone number\n2. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> this.enterContactPhoneNumber(users);
                case "2" -> {
                    return;
                }
                default -> System.out.println("enter a number between 1-2");
            }
        }
    }

    public void enterContactPhoneNumber(Map<User, String> users) {
        while (true) {
            System.out.println("enter your phone number");
            String phoneNumber = ScannerWrapper.nextLine();
            if (!phoneNumber.matches("\\d{11}")) {
                continue;
            }
            for (Contact contact : this.getUserContacts()) {
                if (contact.getPhoneNumber().equals(phoneNumber)) {
                    for (User userDest : users.keySet()) {
                        if (userDest.getAccount().getIDocument().equals(contact.getIDocument())) {
                            new SimCard().chargeThisPhoneNumber(userDest.getPhoneNumber(), users, this);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void enterPhoneNumber(Map<User, String> users) {
        while (true) {
            System.out.println("1.enter your phone number\n2.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> {
                    String phoneNumber = ScannerWrapper.nextLine();
                    if (!phoneNumber.matches("\\d{11}")) {
                        System.out.println("enter a valid phone number");
                        continue;
                    }
                    new SimCard().chargeThisPhoneNumber(phoneNumber, users, this);
                }
                case "2" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-2");
            }
        }
    }

    public double enterAmountCharge() {
        while (true) {
            System.out.println("enter your amount");
            String amount = ScannerWrapper.nextLine();
            if (!amount.matches("\\d+.*\\d*")) {
                System.out.println("enter a number");
                continue;
            }
            if ((Amounts.getInChargePhoneNum() + 1) * (Double.parseDouble(amount)) > this.getAccount().getBalance()) {
                System.out.println("Your balance is not enough");
                return 0.0;
            }
            return Double.parseDouble(amount);
        }
    }

    public String fileContacts() {
        return getUserContacts().stream().map(Contact::fileToString).reduce("", (a1, a2) -> a1 + a2);
    }
}
