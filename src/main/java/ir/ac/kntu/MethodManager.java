package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class MethodManager {
    public Map<Person, String> showFilteredPerson(Map<Person, String> persons, String[] filters) {
        Map<Person, String> fiPeMap = new HashMap<Person, String>();
        for (Person person : persons.keySet()) {
            if ((filters[0] == null || SimilarityAlgorithm.isSimilarity(person.getFirstName(), filters[0]))
                    && (filters[1] == null || SimilarityAlgorithm.isSimilarity(person.getLastName(), filters[1]))
                    && (filters[2] == null || SimilarityAlgorithm.isSimilarity(person.getIDocument(), filters[2]))
                    && (filters[3] == null || SimilarityAlgorithm.isSimilarity(person.getPhoneNumber(), filters[3]))
                    && (filters[4] == null
                            || SimilarityAlgorithm.isSimilarity(person.getUserRole().toString(), filters[4]))) {
                fiPeMap.put(person, person.getIDocument());
            }
        }
        return fiPeMap;
    }

    public void showUsersWithoutFilter(Bank bank) {
        Map<Person, String> persons = addToSimpleMap(bank);
        printLimitedWthoutFltr(persons);
    }

    public Map<Person, String> addToSimpleMap(Bank bank) {
        Map<Person, String> map = new HashMap<>();
        for (User user : bank.getUsers().keySet()) {
            if (user.getAccount().isFeri()) {
                map.put(user, bank.getUsers().get(user));
            }
        }
        for (Support support : bank.getSupports().keySet()) {
            map.put(support, bank.getSupports().get(support));
        }
        for (Manager manager : bank.getManagers().keySet()) {
            map.put(manager, bank.getManagers().get(manager));
        }
        return map;
    }

    public void mainSettings() {
        while (true) {
            System.out
                    .println("main setting\n" + "---------------------\n\n" + "1.set Bank Income\n2.set Bonus\n3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    setBankIncome();
                    break;
                case "2":
                    setBonusManager();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("you should choose 1-3");
                    break;
            }
        }

    }

    public void setBonusManager() {
        while (true) {
            System.out.println("Enter percent of bonus");
            String order1 = ScannerWrapper.nextLine();
            if (order1.matches("\\d+.*\\d*")) {
                Amounts.setBonus(Double.parseDouble(order1));
                return;
            } else {
                System.out.println("Enter a number");
            }
        }
    }

    public void setBankIncome() {
        while (true) {
            System.out.println("set Bank Income\n" + "---------------------\n\n"
                    + "1.set Card To Card Income\n\n2.set Pol Income\n\n3.set PAYA income\n4.set Charge phoneNumber Income\n5.quit\n");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    setCardtoCardIncome();
                    break;
                case "2":
                    setPolIncomeManager();
                    break;
                case "3":
                    setPayaIncomeManager();
                    break;
                case "4":
                    setchargeSimCardIncome();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("you should choose 1-4");
                    break;
            }
        }
    }

    private void setchargeSimCardIncome() {
        while (true) {
            System.out.println("Enter percent of Income");
            String order1 = ScannerWrapper.nextLine();
            if (order1.matches("\\d+.*\\d*")) {
                Amounts.setInChargePhoneNum(Double.parseDouble(order1));
                return;
            } else {
                System.out.println("Enter a number");
            }
        }
    }

    public void setPayaIncomeManager() {
        while (true) {
            System.out.println("Enter Amount of Income");
            String order1 = ScannerWrapper.nextLine();
            if (order1.matches("\\d+.*\\d*")) {
                Amounts.setPayaIncome(Double.parseDouble(order1));
                return;
            } else {
                System.out.println("Enter a number");
            }
        }
    }

    public void setPolIncomeManager() {
        while (true) {
            System.out.println("Enter percent of Income");
            String order1 = ScannerWrapper.nextLine();
            if (order1.matches("\\d+.*\\d*")) {
                Amounts.setPolIncome(Double.parseDouble(order1));
                return;
            } else {
                System.out.println("Enter a number");
            }
        }
    }

    public void setCardtoCardIncome() {
        while (true) {
            System.out.println("Enter Amount of Income");
            String order1 = ScannerWrapper.nextLine();
            if (order1.matches("\\d+.*\\d*")) {
                Amounts.setcToCardIncome(Double.parseDouble(order1));
                return;
            } else {
                System.out.println("Enter a number");
            }
        }
    }

    public void printLimitedWthoutFltr(Map<Person, String> map) {
        int start = 0, end = start + 10;
        List<Map.Entry<Person, String>> list = new ArrayList<>(map.entrySet());
        while (true) {
            list.stream().skip(start).limit(end - start + 1)
                    .forEach(entry -> System.out.println(list.indexOf(entry) + ": " + entry.getKey()));
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

            }
        }
    }

    public void showUsers(Bank bank) {
        while (true) {
            System.out.println(
                    "show Users Menu\n" + "---------------------\n\n" + "1.without filter\n2.with filter\n3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    new MethodManager().showUsersWithoutFilter(bank);
                    break;
                case "2":
                    showUsersWithFilter(bank);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("you should choose 1-3");
                    break;
            }
        }
    }

    private void showUsersWithFilter(Bank bank) {
        Map<Person, String> persons = new MethodManager().addToSimpleMap(bank);
        filterPersons(persons);
    }

    public Map<Person, String> userToPerson(Map<User, String> users) {
        List<Map.Entry<User, String>> list = new ArrayList<>(users.entrySet());
        return list.stream().filter(entry -> entry.getKey().getAccount().isFeri())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void filterPersons(Map<Person, String> persons) {
        String[] filters = new String[5];
        while (true) {
            System.out.println(
                    "you can choose field to filter it after choose press 7\n1.First Name\n2.last name\n3. IDocument\n4.phone Number\n5.user Role\n6. quit\n7. apply and see users");
            String order1 = ScannerWrapper.nextLine();
            if ("6".equals(order1)) {
                return;
            }
            if ("7".equals(order1)) {
                Map<Person, String> fiPeMap = new MethodManager().showFilteredPerson(persons, filters);
                new MethodManager().printLimitedWthoutFltr(fiPeMap);
            }
            filterPersonsSwitch(persons, filters, order1);
        }
    }

    private Map<Person, String> filterPersonsSwitch(Map<Person, String> persons, String[] filters, String order1) {
        switch (order1) {
            case "1" -> {
                System.out.println(Colors.ANSI_RED + "Enter Your First Name");
                filters[0] = ScannerWrapper.nextLine();
            }
            case "2" -> {
                System.out.println("Enter Your last name");
                filters[1] = ScannerWrapper.nextLine();
            }
            case "3" -> {
                System.out.println("Enter Your IDocument");
                filters[2] = ScannerWrapper.nextLine();
            }
            case "4" -> {
                System.out.println("Enter Your phone Number");
                filters[3] = ScannerWrapper.nextLine();
            }
            case "5" -> {
                System.out.println("Enter Your user Role");
                filters[4] = ScannerWrapper.nextLine();
            }
            default -> {
                System.out.println(Colors.colorString() + "You should choose 1-7");
            }
        }
        return persons;
    }

    public Map<Person, String> supportToPerson(Map<Support, String> supports) {
        Map<Person, String> persons = new HashMap<>();
        for (Support support : supports.keySet()) {
            persons.put(support, supports.get(support));
        }
        return persons;
    }

    public Map<Person, String> managerToPerson(Map<Manager, String> managers) {
        Map<Person, String> persons = new HashMap<>();
        for (Manager manager : managers.keySet()) {
            persons.put(manager, managers.get(manager));
        }
        return persons;
    }
}
