package ir.ac.kntu;

import static ir.ac.kntu.RequestSection.*;

import java.util.Map;
import java.util.Objects;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class Manager extends Person {
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Manager(String line, Role role) {
        super(line, role);
        String[] parts = line.split(",");
        setLevel(Integer.parseInt(parts[7]));
    }

    public void logIn(Bank bank) {
        while (true) {
            String iDocument;
            String password;
            System.out.println(Colors.colorString() + "Enter Your id");
            iDocument = ScannerWrapper.nextLine();
            System.out.println(Colors.colorString() + "Enter Your password");
            password = ScannerWrapper.nextLine();
            for (Manager manager : bank.getManagers().keySet()) {
                if (manager.getIDocument().equals(iDocument) && manager.getPassword() == Objects.hash(password)
                        && !manager.isBlocked()) {
                    System.out.println(Colors.colorString() + "Wellcome\n\n" + "---------------------\n\n");
                    manager.logedIn(bank);
                    return;
                }
                if (manager.getIDocument().equals(iDocument) && manager.getPassword() == Objects.hash(password)
                        && manager.isBlocked()) {
                    System.out.println("you have been blocked");
                    return;
                }
            }
            System.out.println(
                    Colors.colorString() + "id or password is incorrect\ndo you want to continue\n1.Yes\n2.quit");
            String order1 = ScannerWrapper.nextLine();
            if ("2".equals(order1)) {
                return;
            } else if (!"1".equals(order1)) {
                System.out.println(Colors.colorString() + "You should choose 1-2");
            }
        }
    }

    public void logedIn(Bank bank) {
        while (true) {
            String order = "";
            System.out.println(Colors.colorString() + "Manager Menu\n" + "---------------------\n\n"
                    + "1.Main Settings\n2.All Type Users Management\n3.Automatic Transaction\n4.quit");
            order = ScannerWrapper.nextLine();
            switch (order) {
                case "1" -> new MethodManager().mainSettings();
                case "2" -> allUserManagement(bank);
                case "3" -> autoMaticTransaction(bank);
                case "4" -> {
                    return;
                }
                default -> System.out.println(Colors.ANSI_RED + "You should choice 1-4");
            }
        }
    }

    public Manager(String firstName, String lastName, String iDocument, String password, int level, Role userRole) {
        super(firstName, lastName, iDocument, password, userRole);
        setLevel(level);
    }

    public Manager() {
    }

    public void autoMaticTransaction(Bank bank) {
        while (true) {
            System.out.println("1.do paya transactions\n2.send bonus\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> new PayaTrans().doAllPayaTrans();
                case "2" -> new BonusCapitalFunds().doAllBonus(bank);
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }

    }

    public void allUserManagement(Bank bank) {
        while (true) {
            System.out.println("All User Management Menu\n" + "---------------------\n\n"
                    + "1.show Users\n2.create Support or manager\n3.Edit User\n4.Block User\n5.unblock User\n6.quit\n");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> new MethodManager().showUsers(bank);
                case "2" -> makeSupportManager(bank);
                case "3" -> editPerson(bank);
                case "4" -> blockPerson(bank);
                case "5" -> unBlockPerson(bank);
                case "6" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }
        }
    }

    public void unBlockPerson(Bank bank) {
        while (true) {
            System.out.println("1.unBlock User\n2.unBlock Support\n3.unBlock Manager\n4. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> unBlockUser(bank, Role.USER);
                case "2" -> unBlockUser(bank, Role.SUPPORT);
                case "3" -> unBlockUser(bank, Role.MANAGER);
                case "4" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }
        }
    }

    public void unBlockUser(Bank bank, Role role) {
        switch (role) {
            case USER -> new MethodManager().printLimitedWthoutFltr(new MethodManager().userToPerson(bank.getUsers()));
            case MANAGER ->
                new MethodManager().printLimitedWthoutFltr(new MethodManager().managerToPerson(bank.getManagers()));
            case SUPPORT ->
                new MethodManager().printLimitedWthoutFltr(new MethodManager().supportToPerson(bank.getSupports()));
            default -> System.out.println("you should choose 1-3");
        }
        System.out.println("Enter your Person Idocument to unblock it");
        String iDocument = ScannerWrapper.nextLine();
        unBlockThisPerson(bank, iDocument, role);
    }

    public void unBlockThisPerson(Bank bank, String iDocument, Role role) {
        for (Person person : new MethodManager().addToSimpleMap(bank).keySet()) {
            if (iDocument.equals(person.getIDocument()) && role.equals(person.getUserRole())) {
                if (person.getUserRole() == Role.MANAGER) {
                    Manager manager = (Manager) person;
                    if (this.getLevel() < manager.getLevel()) {
                        person.setBlocked(false);
                    } else {
                        System.out.println("you cant unblock this manager");
                    }
                    return;
                } else {
                    person.setBlocked(false);
                    return;
                }
            }
        }
    }

    public void blockPerson(Bank bank) {
        while (true) {
            System.out.println("1.block User\n2.Block Support\n3.Block Manager\n4. quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> blockUser(bank, Role.USER);
                case "2" -> blockUser(bank, Role.SUPPORT);
                case "3" -> blockUser(bank, Role.MANAGER);
                case "4" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }
        }
    }

    public void blockUser(Bank bank, Role role) {
        switch (role) {
            case USER -> new MethodManager().printLimitedWthoutFltr(new MethodManager().userToPerson(bank.getUsers()));
            case MANAGER ->
                new MethodManager().printLimitedWthoutFltr(new MethodManager().managerToPerson(bank.getManagers()));
            case SUPPORT ->
                new MethodManager().printLimitedWthoutFltr(new MethodManager().supportToPerson(bank.getSupports()));
            default -> System.out.println("you should choose 1-3");
        }
        System.out.println("Enter your Person Idocument to block it");
        String iDocument = ScannerWrapper.nextLine();
        blockThisPerson(bank, iDocument, role);
    }

    public void blockThisPerson(Bank bank, String iDocument, Role role) {
        for (Person person : new MethodManager().addToSimpleMap(bank).keySet()) {
            if (iDocument.equals(person.getIDocument()) && role.equals(person.getUserRole())) {
                if (person.getUserRole() == Role.MANAGER) {
                    Manager manager = (Manager) person;
                    if (this.getLevel() < manager.getLevel()) {
                        person.setBlocked(true);
                    } else {
                        System.out.println("you cant block this manager");
                    }
                    return;
                } else {
                    person.setBlocked(true);
                    return;
                }
            }
        }
    }

    public void makeSupportManager(Bank bank) {
        while (true) {
            System.out.println("make Support Manager Menu\n" + "---------------------\n\n"
                    + "1.make Support\n2.make manager\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> makeSupport(bank.getSupports());
                case "2" -> makeManager(bank.getManagers());
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }
    }

    public void makeManager(Map<Manager, String> managers) {
        String[] fields = new String[4];
        System.out.println("enter your firstName");
        fields[0] = ScannerWrapper.nextLine();
        System.out.println("enter your lastName");
        fields[1] = ScannerWrapper.nextLine();
        System.out.println("enter your iDocument");
        fields[2] = ScannerWrapper.nextLine();
        System.out.println("enter your password");
        fields[3] = ScannerWrapper.nextLine();
        Manager manager = new Manager(fields[0], fields[1], fields[2], fields[3], this.getLevel() + 1, Role.MANAGER);
        if (manager.checkRepetitousIDocument(new MethodManager().managerToPerson(managers), fields[2])) {
            managers.put(manager, fields[3]);
            System.out.println("done");
        }
    }

    public void makeSupport(Map<Support, String> supports) {
        String[] fields = new String[4];
        System.out.println("enter your firstName");
        fields[0] = ScannerWrapper.nextLine();
        System.out.println("enter your lastName");
        fields[1] = ScannerWrapper.nextLine();
        System.out.println("enter your iDocument");
        fields[2] = ScannerWrapper.nextLine();
        System.out.println("enter your password");
        fields[3] = ScannerWrapper.nextLine();
        Support support = new Support(fields[0], fields[1], fields[2], fields[3], Role.SUPPORT);
        if (support.checkRepetitousIDocument(new MethodManager().supportToPerson(supports), fields[2])) {
            supports.put(support, fields[3]);
            System.out.println("done");
        }

    }

    public void editPerson(Bank bank) {
        Map<Person, String> users = new MethodManager().userToPerson(bank.getUsers());
        Map<Person, String> supports = new MethodManager().supportToPerson(bank.getSupports());
        Map<Person, String> managers = new MethodManager().managerToPerson(bank.getManagers());
        while (true) {
            System.out.println("1.edit User\n2.edit Support\n3.edit Managers\n4.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> editUser(users);
                case "2" -> editSupport(supports);
                case "3" -> editUser(managers);
                case "4" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-4");
            }
        }
    }

    public void editSupport(Map<Person, String> supports) {
        while (true) {
            System.out.println(
                    "this is persons for you to see the we ask you to quit and enter your user iDocument\n1.see supports\n2.continue and choose user\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> new MethodManager().printLimitedWthoutFltr(supports);
                case "2" -> {
                    break;
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
            System.out.println("enter your iDocument");
            getThisSupport(supports, ScannerWrapper.nextLine());
        }
    }

    public void getThisSupport(Map<Person, String> supports, String iDocument) {
        for (Person person : supports.keySet()) {
            if (iDocument.equals(person.getIDocument())) {
                editThisSupport(supports, person);
                return;
            }
        }
        System.out.println("no user with this iDocument exists");
    }

    public void editThisSupport(Map<Person, String> supports, Person person) {
        Support support = (Support) person;
        while (true) {
            System.out.println(
                    "1.edit firstName\n2.edit UserName\n3.edit IDocument\n4. edit access to this support\n5.quit\nafter choose field enter your text to change this field");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> {
                    support.setFirstName(ScannerWrapper.nextLine());
                    System.out.println("done");
                }
                case "2" -> {
                    support.setLastName(ScannerWrapper.nextLine());
                    System.out.println("done");
                }

                case "3" -> {
                    String iDocument = ScannerWrapper.nextLine();
                    if (person.checkRepetitousIDocument(supports, iDocument)) {
                        support.setIDocument(ScannerWrapper.nextLine());
                        System.out.println("done");
                    }
                }
                case "4" -> editAccessSupport(support);
                case "5" -> {
                    return;
                }
                default -> System.out.println(Colors.ANSI_RED + "you should choose 1-4");
            }
        }

    }

    public void editAccessSupport(Support support) {
        while (true) {
            System.out.println(
                    "1.AUTHENTICATION\n2.REPORT\n3.FUNDS\n4.CONTACTS\n5.TRANSFER\n6.CHARGE\n7.CARD\n8.SETTING\n9.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> editAccessThis(support, AUTHENTICATION);
                case "2" -> editAccessThis(support, REPORT);
                case "3" -> editAccessThis(support, FUNDS);
                case "4" -> editAccessThis(support, CONTACT);
                case "5" -> editAccessThis(support, TRANSFER);
                case "6" -> editAccessThis(support, CHARGE);
                case "7" -> editAccessThis(support, CARD);
                case "8" -> editAccessThis(support, SETTING);
                case "9" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-11");
            }
        }

    }

    private void editAccessThis(Support support, RequestSection access) {
        while (true) {
            System.out.println("1.get access\n2.limit support\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> {
                    if (!support.getAccessArrayList().contains(access)) {
                        support.accessAdd(access);
                    }
                }
                case "2" -> {
                    if (support.getAccessArrayList().contains(access)) {
                        support.accessRem(access);
                    }
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
        }
    }

    private void editUser(Map<Person, String> users) {
        while (true) {
            System.out.println(
                    "this is persons for you to see the we ask you to quit and enter your user iDocument\n1.see users\n2.continue and choose user\n3.quit");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> new MethodManager().printLimitedWthoutFltr(users);
                case "2" -> {
                    break;
                }
                case "3" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-3");
            }
            System.out.println("enter your iDocument");
            getThisPerson(users, ScannerWrapper.nextLine());
        }
    }

    public void getThisPerson(Map<Person, String> persons, String iDocument) {
        for (Person person : persons.keySet()) {
            if (iDocument.equals(person.getIDocument())) {
                if (person.getUserRole().equals(Role.MANAGER)) {
                    Manager manager = (Manager) person;
                    if (this.getLevel() < manager.getLevel()) {
                        editThisPerson(persons, person);
                        return;
                    }
                    System.out.println("this manager is your boss you cant edit it");
                    return;
                } else {
                    editThisPerson(persons, person);
                    return;
                }
            }
        }
        System.out.println("no user with this iDocument exists");
    }

    public void editThisPerson(Map<Person, String> persons, Person person) {
        while (true) {
            System.out.println(
                    "1.edit firstName\n2.edit UserName\n3.edit IDocument\n4.quit\nafter choose field enter your text to change this field");
            switch (ScannerWrapper.nextLine()) {
                case "1" -> {
                    person.setFirstName(ScannerWrapper.nextLine());
                    System.out.println("done");
                }
                case "2" -> {
                    person.setLastName(ScannerWrapper.nextLine());
                    System.out.println("done");
                }

                case "3" -> {
                    String iDocument = ScannerWrapper.nextLine();
                    if (person.checkRepetitousIDocument(persons, iDocument)) {
                        person.setIDocument(iDocument);
                        System.out.println("done");
                    }
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println(Colors.ANSI_RED + "you should choose 1-4");
            }
        }
    }

    @Override
    public String toString() {
        return "Manager{" + super.toString() + '}';
    }

    @Override
    public String fileToString() {
        return this.getFirstName() + "," + this.getLastName() + "," +
                this.getIDocument() + "," + this.getPhoneNumber()
                + "," + this.getPassword() + "," + this.getAuthentication() + "," +
                this.isBlocked() + ","
                + this.getLevel() + "\n";
    }
}
