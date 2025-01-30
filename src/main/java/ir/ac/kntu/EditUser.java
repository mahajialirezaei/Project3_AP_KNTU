package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class EditUser {

    public void filterUsers(Map<User, String> users) {
        String[] filters = new String[3];
        while (true) {
            System.out.println(
                    "you can choose field to filter it after choose press 5\n1.First Name\n2.last name\n3. phone number\n4. quit\n5. apply and see users");
            String order1 = ScannerWrapper.nextLine();
            if ("4".equals(order1)) {
                return;
            }
            filterUsersSwitch(users, filters, order1);
        }
    }

    public void filterUsersSwitch(Map<User, String> users, String[] filters, String order1) {
        switch (order1) {
            case "1":
                System.out.println(Colors.ANSI_RED + "Enter Your First Name");
                filters[0] = ScannerWrapper.nextLine();
                break;
            case "2":
                System.out.println("Enter Your last name");
                filters[1] = ScannerWrapper.nextLine();
                break;
            case "3":
                System.out.println("Enter Your phone number");
                filters[2] = ScannerWrapper.nextLine();
                break;
            case "4":
                return;
            case "5":
                showFilteredUser(users, filters);
            default:
                System.out.println(Colors.colorString() + "You should choose 1-5");
                break;
        }
    }

    public void selectFilteredUser(String[] filters, Map<User, String> users) {
        while (true) {
            System.out.println("Enter index or type quit\n\n");
            String order1 = ScannerWrapper.nextLine();
            if ("quit".equals(order1)) {
                return;
            }
            if (!order1.matches("\\d+")) {
                System.out.println("enter a number");
                continue;
            }
            int index = 0;
            for (User user : users.keySet()) {
                if ((filters[0] == null || SimilarityAlgorithm.isSimilarity(user.getFirstName(), filters[0]))
                        && (filters[1] == null || SimilarityAlgorithm.isSimilarity(user.getLastName(), filters[1]))
                        && (filters[2] == null
                                || SimilarityAlgorithm.isSimilarity(user.getPhoneNumber(), filters[2]))) {
                    if (index == Integer.parseInt(order1)) {
                        showUser(user);
                    }
                    index++;
                }
            }
        }
    }

    public void selectAllUser(Map<User, String> users) {
        while (true) {
            System.out.println("Enter index or type quit\n\n");
            String order1 = ScannerWrapper.nextLine();
            if ("quit".equals(order1)) {
                return;
            }
            if (!order1.matches("\\d+")) {
                System.out.println("enter a number");
                continue;
            }
            int index = 0;
            for (User user : users.keySet()) {
                if (index == Integer.parseInt(order1)) {
                    showUser(user);
                }
                index++;
            }
        }
    }

    public void showFilteredUser(Map<User, String> users, String[] filters) {
        while (true) {
            printLimitedUsers(users, filters);
            System.out.println("1. Enter index of your user to show it");
            System.out.println("2. quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    selectFilteredUser(filters, users);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    private void printLimitedUsers(Map<User, String> users, String[] filters) {
        int start = 0, end = start + 10;
        Map<User, String> filteredUsers = getFltrUsers(users, filters);
        List<Map.Entry<User, String>> list = new ArrayList<>(filteredUsers.entrySet());
        if (list.size() == 0) {
            System.out.println("you have no user");
        }
        showList(start, end, list);
    }

    private void showList(int start, int end, List<Map.Entry<User, String>> list) {
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
                    break;
            }
        }
    }

    private Map<User, String> getFltrUsers(Map<User, String> users, String[] filters) {
        Map<User, String> filteredUsers = new HashMap<>();
        for (User user1 : users.keySet()) {
            if ((filters[0] == null || SimilarityAlgorithm.isSimilarity(user1.getFirstName(), filters[0]))
                    && (filters[1] == null || SimilarityAlgorithm.isSimilarity(user1.getLastName(), filters[1]))
                    && (filters[2] == null || SimilarityAlgorithm.isSimilarity(user1.getPhoneNumber(), filters[2]))
                    && user1.getAccount().isFeri()) {
                filteredUsers.put(user1, users.get(user1));
            }
        }
        return filteredUsers;
    }

    public void showUserList(Map<User, String> users) {
        while (true) {
            printLimitedUsers(users);
            System.out.println("1. Enter index of your user to show it");
            System.out.println("2. quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    selectAllUser(users);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }

        }
    }

    private void printLimitedUsers(Map<User, String> users) {
        int start = 0, end = start + 10;
        Map<User, String> ferriUsers = getFerriUsers(users);
        List<Map.Entry<User, String>> list = new ArrayList<>(ferriUsers.entrySet());
        if (list.size() == 0) {
            System.out.println("you have no user");
        }
        showList(start, end, list);
    }

    private Map<User, String> getFerriUsers(Map<User, String> users) {
        List<Map.Entry<User, String>> list = new ArrayList<>(users.entrySet());
        return list.stream().filter(entry -> entry.getKey().getAccount().isFeri())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void showUser(User user) {
        while (true) {
            if (user.getAuthentication() != AuthenticationType.ACCEPT) {
                System.out.println(Colors.ANSI_GREEN
                        + "\n\nnote : this user account has not been authentication you will see just basic informations\n\n");
            }
            System.out.println(
                    "you can se this information\n1.Name\n2.Phone number\n3. Account Idocument\n4.User transactions\n5. quit\n");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    System.out.println(
                            "first name" + " : " + user.getFirstName() + "\nlast name" + " : " + user.getLastName());
                    break;
                case "2":
                    System.out.println("phone number" + " : " + user.getPhoneNumber());
                    break;
                case "3":
                    System.out.println("Account idocument" + " : " + user.getAccount().getIDocument());
                    break;
                case "4":
                    System.out.println("Account transactions" + " : " + user.getAccount().printTrans());
                    break;
                case "5":
                    return;
                default:
                    System.out.println("You should choose 1-5");
                    break;
            }
        }
    }

}
