package ir.ac.kntu.style;

import ir.ac.kntu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CheckTest {
        private Manager highManager;
        private Manager manager;
        private Bank bank;

        private Support support;

        private User user;
        @Test
        public void testCheckCondition() {
                User user1 = new User("Firstname", "LastName", "09145211245",
                                "0254154141", "Aafef@@484ed", Role.USER);
                Map<User, String> users = new HashMap<>();
                String[] fields = { "Firstname", "LastName", "09014147412",
                                "0254114575", "Aafef@@484ed" };
                users.put(user1, "Aafef@@484ed");
                assertTrue(user1.checkCondition(fields, users));

        }

        @Test
        public void testNotChangedIDocumentAfterChangePhoneNumber() {
                User user1 = new User("Firstname", "LastName", "09014147412",
                                "0251414125", "Aafef@@484ed", Role.USER);
                user1.setAuthentication(AuthenticationType.ACCEPT);
                user1.setAccount("IR" + user1.getPhoneNumber().substring(1));
                user1.setPhoneNumber("09448215417");
                assertEquals(user1.getAccount().getIDocument(), "IR" + "9014147412");
        }

        @Test
        public void testUserToString() {
                User user1 = new User("Firstname", "LastName", "09014147412",
                                "0251414125", "Aafef@@484ed", Role.USER);
                assertEquals("User{Person{firstName='Firstname, lastName='LastName, iDocument='0251414125, phoneNumber='09014147412, userRole=USER, authentication=INPROGRESS, isBlocked=false}}",
                                user1.toString());
        }

        @Test
        public void testisSimilitary() {
                User user1 = new User("Firstname", "LastName", "09014147412",
                                "0251414125", "Aafef@@484ed", Role.USER);
                assertTrue(SimilarityAlgorithm.isSimilarity(user1.getFirstName(), "First"));

        }

        @Test
        public void testIsCardNumberCorrect() {
                User user1 = new User("Firstname", "LastName", "09014147412",
                                "0251414125", "Aafef@@484ed", Role.USER);
                user1.setAccount("IR" + user1.getPhoneNumber().substring(1));
                assertEquals(user1.getAccount().getCard().getIDocument(), "9014147412");
        }

        @Test
        public void testIsCardNumberCorrectAfterChangePhoneNumber() {
                User user1 = new User("Firstname", "LastName", "09014147412",
                                "0251414125", "Aafef@@484ed", Role.USER);
                user1.setAccount("IR" + user1.getPhoneNumber().substring(1));
                user1.setPhoneNumber("09211414214");
                assertEquals(user1.getAccount().getCard().getIDocument(), "9014147412");

        }

        @Test
        public void testShowTransferedDetails() {
                User user1 = new User("Firstname", "LastName", "09014147412",
                                "0251414125", "Aafef@@484ed", Role.USER);
                user1.setAccount("IR" + user1.getPhoneNumber().substring(1));
                User user = new User("Firstname", "LastName", "09021414527",
                                "0251414125", "Aafef@@484ed", Role.USER);
                user.setAccount("IR" + user.getPhoneNumber().substring(1));
                user1.getUserContacts()
                                .add(new Contact(user.getFirstName(), user.getLastName(), user.getPhoneNumber()));
                user.getUserContacts()
                                .add(new Contact(user1.getFirstName(), user1.getLastName(), user1.getPhoneNumber()));
                HashMap<User, String> users = new HashMap<User, String>();
                users.put(user1, user1.getPhoneNumber());
                users.put(user, user.getPhoneNumber());
                String phoneNumber = "09021414527";
                assertTrue(user1.getAccount().mutualRelContact(phoneNumber, users, user1));

        }

        @BeforeEach
        public void make() {
                highManager = new Manager("null", "null", "123456", "1111", 0, Role.MANAGER);
                manager = new Manager("null", "null", "1234", "1111", 1, Role.MANAGER);
                bank = new Bank();
                bank.getManagers().put(manager, manager.getIDocument());
                bank.getManagers().put(highManager, highManager.getIDocument());
                support = new Support("sample", "sampleFamily", "aadddss", "drgrgrf", Role.SUPPORT);
                bank.getSupports().put(support, support.getIDocument());
                user = new User("sample", "sampleFamily", "09457451235","0244444444", "drgrgrf", Role.USER);
                user.getAccount().setFeri(true);
                bank.getUsers().put(user, user.getIDocument());
        }

        @Test
        public void testHighManagerBlock() {
                highManager.blockThisPerson(bank, "1234", Role.MANAGER);
                assertTrue(manager.isBlocked());
        }

        @Test
        public void testLowManagerBlock() {
                manager.blockThisPerson(bank, "123456", Role.MANAGER);
                assertFalse(highManager.isBlocked());
        }

        @Test
        public void testHighManagerUnBlock() {
                highManager.unBlockThisPerson(bank, "1234", Role.MANAGER);
                assertFalse(manager.isBlocked());
        }

        @Test
        public void testLowManagerUnBlock() {
                highManager.setBlocked(true);
                manager.unBlockThisPerson(bank, "123456", Role.MANAGER);
                assertTrue(highManager.isBlocked());
        }

        @Test
        public void testBlockSupport() {
                highManager.setBlocked(false);
                highManager.blockThisPerson(bank, "aadddss", Role.SUPPORT);
                assertTrue(support.isBlocked());
        }

        @Test
        public void testBlockUser() {
                highManager.blockThisPerson(bank, "0244444444", Role.USER);
                assertTrue(user.isBlocked());
        }

        @Test
        public void testUnBlockUser() {
                user.setBlocked(true);
                highManager.unBlockThisPerson(bank, "0244444444", Role.USER);
                assertFalse(user.isBlocked());
        }
}
