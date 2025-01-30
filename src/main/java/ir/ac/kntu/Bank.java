package ir.ac.kntu;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<User, String> users = new HashMap<>();
    private Map<Support, String> supports = new HashMap<>();
    private Map<SupportRequest, User> requests = new HashMap<>();

    private Map<Manager, String> managers = new HashMap<>();

    public Map<User, String> getUsers() {
        return users;
    }

    public void setUsers(Map<User, String> users) {
        this.users = users;
    }

    public Map<Support, String> getSupports() {
        return supports;
    }

    public void setSupports(Map<Support, String> supports) {
        this.supports = supports;
    }

    public Map<SupportRequest, User> getRequests() {
        return requests;
    }

    public void setRequests(Map<SupportRequest, User> requests) {
        this.requests = requests;
    }

    public Map<Manager, String> getManagers() {
        return managers;
    }

    public void setManagers(Map<Manager, String> managers) {
        this.managers = managers;
    }

    public Bank(Map<User, String> users, Map<Support, String> supports, Map<SupportRequest, User> requests, Map<Manager, String> managers) {
        setUsers(users);
        setSupports(supports);
        setRequests(requests);
        setManagers(managers);
    }
    public Bank(){
        users = new HashMap<>();
        supports = new HashMap<>();
        requests = new HashMap<>();
        managers = new HashMap<>();
    }
}
