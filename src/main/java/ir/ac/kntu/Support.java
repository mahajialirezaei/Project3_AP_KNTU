package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class Support extends Person {
    private List<RequestSection> accessArrayList = new ArrayList<RequestSection>();

    public Support(String line, Role role) {
        super(line, role);
    }

    public List<RequestSection> getAccessArrayList() {
        return accessArrayList;
    }

    public void setAccessArrayList(List<RequestSection> accessArrayList) {
        this.accessArrayList = accessArrayList;
    }

    public void accessAdd(RequestSection access) {
        accessArrayList.add(access);
    }

    public void accessRem(RequestSection access) {
        accessArrayList.remove(access);
    }

    public Support() {
        super();
    }

    public Support(String firstName, String lastName, String iDocument, String password, Role userRole) {
        super(firstName, lastName, iDocument, password, userRole);
    }

    public void logIn(Bank bank) {
        while (true) {
            String iDocument;
            String password;
            System.out.println(Colors.colorString() + "Enter Your id");
            iDocument = ScannerWrapper.nextLine();
            System.out.println(Colors.colorString() + "Enter Your password");
            password = ScannerWrapper.nextLine();
            for (Support support1 : bank.getSupports().keySet()) {
                if (support1.getIDocument().equals(iDocument) && support1.getPassword() == Objects.hash(password)
                        && !support1.isBlocked()) {
                    System.out.println(Colors.colorString() + "Wellcome\n\n" + "---------------------\n\n");
                    support1.logedIn(bank);
                    return;
                }
                if (support1.isBlocked() && support1.getIDocument().equals(iDocument)
                        && support1.getPassword() == Objects.hash(password)) {
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
            System.out.println(Colors.colorString() + "Support Menu\n" + "---------------------\n\n"
                    + "1.Requests\n2.Users\n3.quit");
            order = ScannerWrapper.nextLine();
            switch (order) {
                case "1" -> requests(bank.getRequests());
                case "2" -> users(bank.getUsers());
                case "3" -> {
                    return;
                }
                default -> System.out.println(Colors.ANSI_RED + "You should choice 1-3");
            }
        }
    }

    public void users(Map<User, String> users) {
        while (true) {
            System.out.println(
                    "users list menu" + "\n\n---------------------\n\n" + "1.Show User List\n2. Filter\n 3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    new EditUser().showUserList(users);
                    break;
                case "2":
                    new EditUser().filterUsers(users);
                    break;
                case "3":
                    return;
                default:
                    System.out.println(Colors.ANSI_RED + "you should choose 1-3");
                    break;
            }
        }
    }

    public void requests(Map<SupportRequest, User> requests) {
        while (true) {
            System.out.println("1.without filter\n2.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    new MethodSupport().supReqWthOutFltr(requests, this);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void getSupReqConInd(int index1, Map<SupportRequest, User> requests, RequestCondition requestCondition) {
        int index2 = 0;
        for (SupportRequest request : requests.keySet()) {
            if (index2 == index1 && request.getRequestCondition().equals(requestCondition)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                if ("ir.ac.kntu.Request".equals(request.getClass().getName())) {
                    new MethodSupport().finalConiform(requests.get(request), requests);
                    return;
                }
                System.out.println(request);
                System.out.println(Colors.colorString() + "Enter your answer or press enter");
                String order1 = ScannerWrapper.nextLine();
                request.setAnswer(order1);
                new MethodSupport().setSupportRequestCondition(request);
                return;
            }
            if (request.getRequestCondition().equals(requestCondition)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                index2++;
            }
        }
    }

    public void finalShowFiltCon(Map<SupportRequest, User> requests, RequestCondition requestCondition) {
        printLimitedFiltReqCon(requests, requestCondition);
    }

    private void printLimitedFiltReqCon(Map<SupportRequest, User> requests, RequestCondition requestCondition) {
        Map<SupportRequest, User> supRequest = getSupRequest(requests, requestCondition);
        List<Map.Entry<SupportRequest, User>> list = new ArrayList<>(supRequest.entrySet());
        if (list.size() == 0) {
            System.out.println("you have no request");
        }
        int start = 0, end = start + 10;
        showList(start, end, list);
    }

    private Map<SupportRequest, User> getSupRequest(Map<SupportRequest, User> requests,
            RequestCondition requestCondition) {
        Map<SupportRequest, User> supRequest = new HashMap<SupportRequest, User>();
        for (SupportRequest request : requests.keySet()) {
            if (request.getRequestCondition() == requestCondition
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                supRequest.put(request, requests.get(request));
            }
        }
        return supRequest;
    }

    public void finalShowFlirReqSec(Map<SupportRequest, User> requests, RequestSection requestSection) {
        printLimitedFiltReqSec(requests, requestSection);
    }

    private void printLimitedFiltReqSec(Map<SupportRequest, User> requests, RequestSection requestSection) {
        Map<SupportRequest, User> supRequest = getSupRequest(requests, requestSection);
        List<Map.Entry<SupportRequest, User>> list = new ArrayList<>(supRequest.entrySet());
        if (list.size() == 0) {
            System.out.println("you have no request");
        }
        int start = 0, end = start + 10;
        showList(start, end, list);
    }

    private Map<SupportRequest, User> getSupRequest(Map<SupportRequest, User> requests, RequestSection requestSection) {
        Map<SupportRequest, User> supRequest = new HashMap<>();
        for (SupportRequest request : requests.keySet()) {
            if (request.getRequestSection() == requestSection
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                supRequest.put(request, requests.get(request));
            }
        }
        return supRequest;
    }

    public void showFlitReqSec(Map<SupportRequest, User> requests, RequestSection requestSection) {
        if (!getAccessArrayList().contains(requestSection)) {
            System.out.println("You have no access to this part");
            return;
        }
        finalShowFlirReqSec(requests, requestSection);
        while (true) {
            System.out.println("1.enter your request index\n2.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    int index1 = 0;
                    if (order1.matches("\\d+")) {
                        index1 = Integer.parseInt(order1);
                        getSupReqSecInd(index1, requests, requestSection);
                    } else {
                        System.out.println("enter a number");
                    }
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void getSupReqSecInd(int index1, Map<SupportRequest, User> requests, RequestSection requestSection) {
        int index2 = 0;
        for (SupportRequest request : requests.keySet()) {
            if (index2 == index1 && request.getRequestSection().equals(requestSection)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                if ("ir.ac.kntu.Request".equals(request.getClass().getName())) {
                    new MethodSupport().finalConiform(requests.get(request), requests);
                    return;
                }
                System.out.println(request);
                System.out.println("Enter your answer or press enter");
                String order1 = ScannerWrapper.nextLine();
                request.setAnswer(order1);
                new MethodSupport().setSupportRequestCondition(request);
                return;
            }
            if (request.getRequestSection().equals(requestSection)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                index2++;
            }
        }
    }

    public void filterByReqSec(Map<SupportRequest, User> requests) {
        while (true) {
            System.out.println(
                    "1.REPORT\n2.CONTACT\n3.TRANSFER\n4.SETTING\n5.AUTHENTICATION\n6.CARD\n7.CHARGE\n8.CAPITALFUNDS\n9.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1" -> showFlitReqSec(requests, RequestSection.REPORT);
                case "2" -> showFlitReqSec(requests, RequestSection.CONTACT);
                case "3" -> showFlitReqSec(requests, RequestSection.TRANSFER);
                case "4" -> showFlitReqSec(requests, RequestSection.SETTING);
                case "5" -> showFlitReqSec(requests, RequestSection.AUTHENTICATION);
                case "6" -> showFlitReqSec(requests, RequestSection.CARD);
                case "7" -> showFlitReqSec(requests, RequestSection.CHARGE);
                case "8" -> showFlitReqSec(requests, RequestSection.FUNDS);
                case "9" -> {
                    return;
                }
                default -> System.out.println("you should choose 1-9");
            }
        }
    }

    public void showFlitReqCon(Map<SupportRequest, User> supportRequests, RequestCondition requestCondition) {
        finalShowFiltCon(supportRequests, requestCondition);
        while (true) {
            System.out.println("1.enter your request index\n2.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    int index1 = 0;
                    if (order1.matches("\\d+")) {
                        index1 = Integer.parseInt(order1);
                        getSupReqConInd(index1, supportRequests, requestCondition);
                    } else {
                        System.out.println("enter a number");
                    }
                    break;
                case "2":
                    return;
                default:
                    System.out.println("you should choose 1-2");
                    break;
            }
        }
    }

    public void filterByReqCon(Map<SupportRequest, User> supportRequests) {
        while (true) {
            System.out
                    .println("Condition Menu\n\n" + Colors.ANSI_BLUE + "1.REGISTERED\n2.INPROGRESS\n3.CLOSED\n4.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    showFlitReqCon(supportRequests, RequestCondition.REGISTERED);
                    break;
                case "2":
                    showFlitReqCon(supportRequests, RequestCondition.INPROGRESS);
                    break;
                case "3":
                    showFlitReqCon(supportRequests, RequestCondition.CLOSED);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("you should choose 1-4");
            }
        }
    }

    public void getSupUserInd(int index1, Map<SupportRequest, User> requests, String userPhoneNumber) {
        int index2 = 0;
        for (SupportRequest request : requests.keySet()) {
            if (index2 == index1 && request.getUserPhonenumber().equals(userPhoneNumber)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                if ("ir.ac.kntu.Request".equals(request.getClass().getName())) {
                    new MethodSupport().finalConiform(requests.get(request), requests);
                    return;
                }
                System.out.println(request);
                System.out.println("Enter your answer or press enter");
                String order1 = ScannerWrapper.nextLine();
                request.setAnswer(order1);
                new MethodSupport().setSupportRequestCondition(request);
                return;
            }
            if (request.getUserPhonenumber().equals(userPhoneNumber)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                index2++;
            }
        }
    }

    public void finalShowFiltUser(Map<SupportRequest, User> requests, String userPhoneNumber) {
        printLimitedFiltReqUser(requests, userPhoneNumber);
    }

    private void printLimitedFiltReqUser(Map<SupportRequest, User> requests, String userPhoneNumber) {
        Map<SupportRequest, User> supRequest = getSupRequest(requests, userPhoneNumber);
        List<Map.Entry<SupportRequest, User>> list = new ArrayList<>(supRequest.entrySet());
        if (list.size() == 0) {
            System.out.println("you have no request");
        }
        int start = 0, end = start + 10;
        showList(start, end, list);
    }

    private void showList(int start, int end, List<Map.Entry<SupportRequest, User>> list) {
        while (true) {
            list.stream().skip(start).limit(end - start + 1)
                    .forEach(entry -> System.out.println(list.indexOf(entry) + ": " + entry.getKey().getRequestText()
                            + entry.getValue() + entry.getKey().getRequestCondition()));
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

    private Map<SupportRequest, User> getSupRequest(Map<SupportRequest, User> requests, String userPhoneNumber) {
        Map<SupportRequest, User> supRequest = new HashMap<SupportRequest, User>();
        for (SupportRequest request : requests.keySet()) {
            if (request.getUserPhonenumber().equals(userPhoneNumber)
                    && this.getAccessArrayList().contains(request.getRequestSection())) {
                supRequest.put(request, requests.get(request));
            }
        }
        return supRequest;
    }

    public void supReqWthFltr(Map<SupportRequest, User> supportRequests) {
        while (true) {
            System.out.println(Colors.LAVENDER + "Support Filter Menu\n\n"
                    + "1.Filter by RequestSection\n2.Filter by Requestcondition\n3.Filter by user\n4.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    filterByReqSec(supportRequests);
                    break;
                case "2":
                    filterByReqCon(supportRequests);
                    break;
                case "3":
                    filterByUser(supportRequests);
                    break;
                case "4":
                    return;
                default:
                    break;
            }
        }
    }

    public void filterByUser(Map<SupportRequest, User> supportRequests) {
        while (true) {
            System.out.println("1.Enter your user phone number\n2. quit\n");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    if (order1.matches("\\d{11}")) {
                        new MethodSupport().showFlitUser(supportRequests, order1, this);
                    } else {
                        System.out.println("phone number structure is not correct");
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

    @Override
    public String toString() {
        return "Support{" + super.toString() + "}";
    }

    public String fileAccess() {
        String string = "";
        for (RequestSection access : accessArrayList) {
            string += access + " ";
        }
        if (string.length() > 0) {
            string += "\n";
        }
        return string;
    }

    public void setAccessArrayList(String line) {
        String[] fields = line.split(" ");
        for (String field : fields) {
            accessAdd(RequestSection.valueOf(field));
        }
    }
}
