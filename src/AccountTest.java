import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    class TestAccount extends Account {
        public TestAccount(String accNum, double bal, User user) {
            super(accNum, bal, user);
            this.card = new DebitCards("Mastercard");
        }
    }
    @Test
    void depositIncreasesBalance() {
        User u = new User();
        TestAccount acc = new TestAccount("123", 0, u);
        acc.deposit(100);
        assertEquals(100, acc.balance);
    }

    @Test
    void depositReactivatesAccount() {
        User u = new User();
        TestAccount acc = new TestAccount("123", -50, u);
        acc.overdrafting_count = 2;
        acc.deposit(100);
        assertEquals(50, acc.balance);
        assertEquals(0, acc.overdrafting_count);
    }
}
