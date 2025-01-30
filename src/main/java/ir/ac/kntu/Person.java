package ir.ac.kntu;

import java.util.Map;
import java.util.Objects;

public class Person {
    private String firstName = new String();
    private String lastName = new String();
    private String iDocument = new String();
    private int password;
    private String phoneNumber;
    private Role userRole;

    private AuthenticationType authentication = AuthenticationType.INPROGRESS;

    private boolean isBlocked = false;

    public Person(String line, Role role) {
        String[] parts = line.split(",");
        setFirstName(parts[0]);
        setLastName(parts[1]);
        setIDocument(parts[2]);
        setPhoneNumber(parts[3]);
        setPassword(Integer.parseInt(parts[4]));
        setAuthentication(AuthenticationType.valueOf(parts[5]));
        setBlocked(Boolean.parseBoolean(parts[6]));
        setUserRole(role);
    }

    public AuthenticationType getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(AuthenticationType authentication) {
        this.authentication = authentication;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
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

    public String getIDocument() {
        return this.iDocument;
    }

    public void setIDocument(String iDocument) {
        this.iDocument = iDocument;
    }

    public int getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = Objects.hash(password);
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Person() {

    }

    public Person(String firstName, String lastName, String iDocument, String password, Role userRole) {
        setFirstName(firstName);
        setLastName(lastName);
        setIDocument(iDocument);
        setPassword(password);
        setUserRole(userRole);
    }

    public boolean checkRepetitousIDocument(Map<Person, String> persons, String iDocument) {
        for (Person person : persons.keySet()) {
            if (iDocument.equals(person.getIDocument())) {
                System.out.println(Colors.ANSI_RED + "note : Repetious IDocument");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName +
                ", lastName='" + lastName +
                ", iDocument='" + iDocument +
                ", phoneNumber='" + phoneNumber +
                ", userRole=" + userRole +
                ", authentication=" + authentication +
                ", isBlocked=" + isBlocked +
                '}';
    }

    public String fileToString() {
        return this.getFirstName() + "," + this.getLastName() + "," + this.getIDocument() + "," + this.getPhoneNumber()
                + "," + this.getPassword() + "," + this.getAuthentication() + "," + this.isBlocked() + "\n";
    }
}
