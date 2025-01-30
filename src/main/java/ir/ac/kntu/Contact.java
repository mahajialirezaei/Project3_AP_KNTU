package ir.ac.kntu;

import java.util.List;
import java.util.Map;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String iDocument;

    public Contact() {

    }

    public Contact(String line) {
        String[] fields = line.split(",");
        setFirstName(fields[0]);
        setLastName(fields[1]);
        setPhoneNumber(fields[2]);
        setIDocument(fields[2]);
    }

    public String getIDocument() {
        return this.iDocument;
    }

    public void setIDocument(String phonenumber) {
        this.iDocument = "IR" + phonenumber.substring(1);
    }

    public Contact(String firstName, String lastName, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setIDocument(phoneNumber);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void contacts(User user1, Map<User, String> users) {
        System.out.println(Colors.colorString() + "\n\n---------------------\n\n" + "1.add contact");
        System.out.println("2.show contacts");
        System.out.println("3.quit");
        switch (ScannerWrapper.nextLine()) {
            case "1":
                new MethodAccount().addContact(user1, users);
                break;
            case "2":
                showContacts(user1);
                break;
            case "3":
                return;
            default:
                System.out.println("you should choose 1-3\n\n");
                break;
        }
    }

    public void showContacts(User user1) {
        if (user1.getUserContacts().size() == 0) {
            System.out.println("you have no any contact\n\n");
        } else {
            printLimitedWthOutFltr(user1.getUserContacts());
        }
        while (true) {
            System.out.println("Do you want to show or edit any contact");
            System.out.println("1.yes");
            System.out.println("2.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    showOrEditContacts(user1);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void printLimitedWthOutFltr(List<Contact> userContacts) {
        int start = 0, end = start + 10;
        while (true) {
            userContacts.stream().skip(start).limit(end - start + 1)
                    .forEach(userContact -> System.out.println(userContacts.indexOf(userContact) + ": " + userContact));
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

    private void showOrEditContacts(User user1) {
        System.out.println("Enter index of your object's contact");
        while (true) {
            String order1 = ScannerWrapper.nextLine();
            if (!order1.matches("\\d+")) {
                System.out.println("enter a number");
                continue;
            } else {
                int index = Integer.parseInt(order1);
                if (index >= user1.getUserContacts().size()) {
                    System.out.println("enter a number between 0-" + (user1.getUserContacts().size() - 1));
                } else {
                    System.out.println(user1.getUserContacts().get(index).toString());
                    editContacts(user1, user1.getUserContacts().get(index));
                }
            }
            System.out.println("if you want to quit enter 1\n else write any text to continue");
            String order2 = ScannerWrapper.nextLine();
            if ("1".equals(order2)) {
                return;
            }
        }
    }

    public void editContacts(User user1, Contact contact1) {
        String[] fields = new String[3];
        while (true) {
            String order1 = new String();
            System.out.println(Colors.ANSI_RED + "edit contacts menu" + "\n\n---------------------\n\n"
                    + "choose 1-4\n1.edit firstname\n2.edit lastname\n3.edit phonenumber\n4.quit");
            order1 = ScannerWrapper.nextLine();
            if ("4".equals(order1)) {
                return;
            }
            boolean check1 = editContactsSwitch(order1, fields, contact1);
            if (check1) {
                setContactField(order1, contact1, fields);
                changeTransactions(user1, contact1);
                System.out.println("contact has been saved");
            }
        }
    }

    public void setContactField(String order1, Contact contact1, String[] fields) {
        switch (order1) {
            case "1":
                contact1.setFirstName(fields[0]);
                break;
            case "2":
                contact1.setLastName(fields[1]);
                break;
            case "3":
                contact1.setPhoneNumber(fields[2]);
                break;
            default:
                break;
        }
    }

    public void changeTransactions(User user1, Contact contact1) {
        for (Transaction transaction : user1.getAccount().getTransactions()) {
            if (transaction.getDestination().equals(contact1.getIDocument())
                    && transaction.getType() == TransactionType.TRANSFERCONTACT) {
                transaction.setFirstNameDest(contact1.getFirstName());
                transaction.setLastNameDest(contact1.getLastName());
            }
        }
    }

    private boolean editContactsSwitch(String order1, String[] fields, Contact contact1) {
        fields[0] = contact1.getFirstName();
        fields[1] = contact1.getLastName();
        fields[2] = contact1.getPhoneNumber();
        switch (order1) {
            case "1":
                System.out.println("Enter Your firstname");
                fields[0] = ScannerWrapper.nextLine();
                break;
            case "2":
                System.out.println("Enter Your lastname");
                fields[1] = ScannerWrapper.nextLine();
                break;
            case "3":
                System.out.println("Enter Your phonenumber");
                fields[2] = ScannerWrapper.nextLine();
                break;
            default:
                System.out.println("You should choose 1-4");
                break;
        }
        return !new User().checkEmptyContact(fields) && fields[2].matches("\\d{11}");

    }

    public void changeContactsFeature(User user1) {
        while (true) {
            String order1;
            System.out.println(Colors.ANSI_GREEN + "change contacts feature menu" + Colors.AQUA
                    + "\n\n---------------------\n\n" + "1.set contacts feature available");
            System.out.println("2. set contacts feature unavailable");
            System.out.println("3. quit");
            order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    user1.setContactsFeature(true);
                    System.out.println("succesfully changed to available");
                    return;
                case "2":
                    user1.setContactsFeature(false);
                    System.out.println("succesfully changed to unavailable");
                    return;
                case "3":
                    return;
                default:
                    System.out.println("you should choose 1-3");
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "{" + " firstName='" + getFirstName() + "'" + ", lastName='" + getLastName() + "'" + ", phoneNumber='"
                + getPhoneNumber() + "'" + "}";
    }

    public String privateInfo() {
        return "{" + " firstName='" + getFirstName() + "'" + ", lastName='" + getLastName() + "'" + "}";
    }

    public String fileToString() {
        return getFirstName() + "," + getLastName() + "," + getPhoneNumber() + "," + getIDocument() + "\n";
    }
}
