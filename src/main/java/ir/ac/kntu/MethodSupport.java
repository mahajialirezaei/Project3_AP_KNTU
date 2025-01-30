package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.ac.kntu.fantesic.ScannerWrapper;

public class MethodSupport {
    public SupportRequest supportSwitch(String order1, Map<SupportRequest, User> supportRequests, User user1) {
        SupportRequest supportRequest = new SupportRequest();
        switch (order1) {
            case "1" -> supportRequest.setRequestSection(RequestSection.REPORT);
            case "2" -> supportRequest.setRequestSection(RequestSection.CONTACT);
            case "3" -> supportRequest.setRequestSection(RequestSection.TRANSFER);
            case "4" -> supportRequest.setRequestSection(RequestSection.SETTING);
            case "5" -> supportRequest.setRequestSection(RequestSection.FUNDS);
            case "6" -> supportRequest.setRequestSection(RequestSection.CHARGE);
            case "7" -> supportRequest.setRequestSection(RequestSection.CARD);
            case "9" -> reviewSupportRequsets(supportRequests, user1);
            default -> System.out.println("you should choose 1-9");
        }
        return supportRequest;
    }

    public void reviewSupportRequsets(Map<SupportRequest, User> requests, User user1) {

        printLimitedRequests(requests, user1);
        while (true) {
            System.out.println("do you want to see any supportReport");
            System.out.println("1.yes");
            System.out.println("2.exit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    seeSupportRequestInformation(requests, user1);
                    break;
                case "2":
                    return;
                default:
                    System.out.println(Colors.ANSI_RED + "you should choose 1-2");
                    break;
            }
        }
    }

    private void printLimitedRequests(Map<SupportRequest, User> requests, User user1) {
        Map<SupportRequest, User> userRequests = getUserRequests(requests, user1);
        int start = 0, end = start + 10;
        List<Map.Entry<SupportRequest, User>> list = new ArrayList<>(userRequests.entrySet());
        while (true) {
            list.stream().skip(start).limit(end - start + 1).forEach(entry -> System.out.println(list.indexOf(entry) + ": " + entry.getKey().getRequestText()));
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

    private Map<SupportRequest, User> getUserRequests(Map<SupportRequest, User> requests, User user1) {
        Map<SupportRequest, User> userRequests = new HashMap<>();
        for (SupportRequest supportRequest : requests.keySet()) {
            if (requests.get(supportRequest).equals(user1)) {
                userRequests.put(supportRequest, user1);
            }
        }
        return userRequests;
    }

    public void seeSupportRequestInformation(Map<SupportRequest, User> requests, User user1) {
        while (true) {
            System.out.println("Enter index of you request object or type quit");
            String order1 = ScannerWrapper.nextLine();
            if ("quit".equals(order1)) {
                return;
            }
            if (!order1.matches("\\d+")) {
                System.out.println("Enter a number");
                continue;
            }
            if (Integer.parseInt(order1) >= requests.size()) {
                System.out.println(Colors.ANSI_RED + "You should choose 0-" + (user1.getSupReqUser().size() - 1));
            } else {
                int index = Integer.parseInt(order1);
                int index1 = 0;
                for (SupportRequest supportRequest : user1.getSupReqUser().keySet()) {
                    if (index1 == index) {
                        System.out.println(supportRequest);
                    }
                    index1++;
                }
            }
        }
    }

    public void support(User user1, Map<SupportRequest, User> supportRequests) {
        boolean registered = false;
        while (true) {
            System.out.println("Support Menu\n\n" + Colors.ANSI_GREEN + "choose 1-5\n1.Report\n2.Contacts\n3.Transfer\n4.Setting\n5.Funds\n6.Charge\n7.Card\n8.quit\n9.Review supportRequests");
            String order1 = ScannerWrapper.nextLine();
            if ("8".equals(order1)) {
                return;
            }
            if (order1.matches("[1-7]")) {
                registered = true;
            }
            SupportRequest request = supportSwitch(order1, supportRequests, user1);
            if (registered) {
                request.setRequestCondition(RequestCondition.REGISTERED);
                request.setUserPhonenumber(user1.getPhoneNumber());
                System.out.println("Enter your request text");
                order1 = ScannerWrapper.nextLine();
                if (order1.length() == 0) {
                    System.out.println("text should not be empty");
                    continue;
                }
                request.setRequestText(order1);
                supportRequests.put(request, user1);
                user1.getSupReqUser().put(request, user1);
                registered = false;
            }
        }

    }

    public void showFlitUser(Map<SupportRequest, User> supportRequests, String userPhoneNumber, Support thisSupport) {
        thisSupport.finalShowFiltUser(supportRequests, userPhoneNumber);
        while (true) {
            System.out.println("1.enter your request index\n2.quit\n");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    if (order1.matches("\\d+")) {
                        int index1 = Integer.parseInt(order1);
                        thisSupport.getSupUserInd(index1, supportRequests, userPhoneNumber);
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

    public void supReqWthOutFltr(Map<SupportRequest, User> requests, Support thisSupport) {
        finalShowWthOutFltr(requests, thisSupport);
        while (true) {
            System.out.println("1. Enter index of your supportrequest\n2.Filter\n3.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    order1 = ScannerWrapper.nextLine();
                    if (order1.matches("\\d+")) {
                        int index1 = Integer.parseInt(order1);
                        getSupReqInd(index1, requests, thisSupport);
                    } else {
                        System.out.println("enter a number");
                    }
                    break;
                case "2":
                    thisSupport.supReqWthFltr(requests);
                case "3":
                    return;
                default:
                    System.out.println("you should choose 1-3");
                    break;
            }
        }
    }

    public void finalShowWthOutFltr(Map<SupportRequest, User> requests, Support thisSupport) {
        printLimitedWthoutFltr(requests, thisSupport);
    }

    public void printLimitedWthoutFltr(Map<SupportRequest, User> requests, Support thisSupport) {
        int start = 0, end = start + 10;
        Map<SupportRequest, User> supRequests = getSupRequest(requests, thisSupport);
        List<Map.Entry<SupportRequest, User>> list = new ArrayList<>(supRequests.entrySet());
        if (list.size() == 0) {
            System.out.println("you have no request");
            return;
        }
        loopShowLimited(start, end, list);
    }

    public void loopShowLimited(int start, int end, List<Map.Entry<SupportRequest, User>> list) {
        while (true) {
            list.stream().skip(start).limit(end - start + 1).forEach(entry -> System.out.println(list.indexOf(entry) + ": " + entry.getKey().getRequestText()));
            System.out.println("1.previous page\n2.next page\n3.quit");
            switch (ScannerWrapper.nextLine()) {
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

    private Map<SupportRequest, User> getSupRequest(Map<SupportRequest, User> requests, Support thisSupport) {
        Map<SupportRequest, User> supRequests = new HashMap<>();
        for (SupportRequest request : requests.keySet()) {
            if (thisSupport.getAccessArrayList().contains(request.getRequestSection())) {
                supRequests.put(request, requests.get(request));
            }
        }
        return supRequests;
    }

    public void getSupReqInd(int index1, Map<SupportRequest, User> requests, Support thisSupport) {
        int index2 = 0;
        for (SupportRequest request : requests.keySet()) {
            if (thisSupport.getAccessArrayList().contains(request.getRequestSection())) {
                if (index2 == index1) {
                    if ("ir.ac.kntu.Request".equals(request.getClass().getName())) {
                        finalConiform(requests.get(request), requests);
                        return;
                    } else {
                        System.out.println("Enter your answer or press enter");
                        String order1 = ScannerWrapper.nextLine();
                        setSupportRequestCondition(request);
                        request.setAnswer(order1);
                        return;
                    }
                }
                index2++;
            }
        }
    }

    public void setSupportRequestCondition(SupportRequest supportRequest) {
        while (true) {
            System.out.println(Colors.colorString() + "\n\n---------------------\n\n" + "1.REGESTRED\n2.IN PROGRESS\n3. CLOSED");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    supportRequest.setRequestCondition(RequestCondition.REGISTERED);
                    return;
                case "2":
                    supportRequest.setRequestCondition(RequestCondition.INPROGRESS);
                    return;
                case "3":
                    supportRequest.setRequestCondition(RequestCondition.CLOSED);
                    return;
                default:
                    System.out.println("you should choose 1-3");
                    break;
            }
        }
    }

    public void authentication(Map<SupportRequest, User> requests) {
        for (User user1 : requests.values()) {
            if ("ir.ac.kntu.Request".equals(Request.getrequest(user1, requests).getClass().getName())) {
                System.out.println(user1.getPhoneNumber() + Request.getrequest(user1, requests).toString());
            }
        }
        while (true) {
            System.out.println(Colors.colorString() + "do you want to see information of any user");
            System.out.println("1.yes");
            System.out.println("2.quit");
            String order1 = ScannerWrapper.nextLine();
            switch (order1) {
                case "1":
                    for (User user1 : requests.values()) {
                        if ("ir.ac.kntu.Request".equals(Request.getrequest(user1, requests).getClass().getName())) {
                            System.out.println(user1.getPhoneNumber() + Request.getrequest(user1, requests).toString());
                        }
                    }
                    coniformationUser(requests);
                    break;
                case "2":
                    return;
                default:
                    System.out.println("You should choose 1-2");
                    break;
            }
        }

    }

    public void coniformationUser(Map<SupportRequest, User> requests) {
        while (true) {
            System.out.println(Colors.colorString() + "Enter you object user phonenumber or enter 0 to quit");
            String order1 = new String();
            order1 = ScannerWrapper.nextLine();
            if (order1.matches("\\d{11}")) {
                for (User user1 : requests.values()) {
                    if (user1.getPhoneNumber().equals(order1) && Request.class.isInstance(Request.getrequest(user1, requests).getClass())) {
                        System.out.println(user1.toString());
                        finalConiform(user1, requests);
                        return;
                    }
                }
                System.out.println("not found");
            } else if ("0".equals(order1)) {
                return;
            } else {
                System.out.println("phone number structure is incorrect");
            }
        }

    }

    public void finalConiform(User user1, Map<SupportRequest, User> requests) {
        while (true) {
            System.out.println(Request.getrequest(user1, requests));
            System.out.println(Colors.colorString() + "1. coniform\n2.reject\n3.quit");
            String order = ScannerWrapper.nextLine();
            switch (order) {
                case "1":
                    coniformUser(user1, requests);
                    return;
                case "2":
                    rejectUser(user1, requests);
                    return;
                case "3":
                    return;
                default:
                    System.out.println(Colors.colorString() + "You should choose 1-3");
                    break;
            }
        }
    }

    private void rejectUser(User user1, Map<SupportRequest, User> requests) {
        Request.getrequest(user1, requests).setAuthentication(AuthenticationType.REJECT);
        user1.setAuthentication(AuthenticationType.REJECT);
        System.out.println("enter your answer");
        String order = ScannerWrapper.nextLine();
        Request.getrequest(user1, requests).setAnswer(order);
        Request.getrequest(user1, requests).setRequestCondition(RequestCondition.CLOSED);
        System.out.println("Done");
    }

    private void coniformUser(User user1, Map<SupportRequest, User> requests) {
        Request.getrequest(user1, requests).setAuthentication(AuthenticationType.ACCEPT);
        user1.setAuthentication(AuthenticationType.ACCEPT);
        user1.getAccount().setFeri(true);
        Request.getrequest(user1, requests).setAnswer("");
        user1.setAccount("IR" + user1.getPhoneNumber().substring(1));
        user1.getAccount().setAccountType(AccountType.FERRI);
        Request.getrequest(user1, requests).setRequestCondition(RequestCondition.CLOSED);
        System.out.println("Done");
    }
}
