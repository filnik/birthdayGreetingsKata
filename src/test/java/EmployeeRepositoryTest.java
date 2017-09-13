import com.filnik.repository.EmailAddress;
import com.filnik.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class EmployeeRepositoryTest {

    @Test
    public void employeeRepositoryHasEmailAddresses() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        EmailAddress[] emailAddress = repository.loadFromDatabase();
        assertTrue(emailAddress.length == 2);
    }

    @Test
    public void employeeRepositoryStoresEmailAddressesCorrectly() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        EmailAddress emailAddress = new EmailAddress("lastname", "firstname",
                LocalDateTime.now(), "filnik90@gmail.com");
        repository.store(emailAddress);
        EmailAddress[] emailAddresses = repository.load();
        assertTrue(emailAddresses.length == 1);
    }
    @Test
    public void employeeRepositoryLoadsEmailAddressesCorrectly() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        LocalDateTime date = LocalDateTime.of(1982, 10, 8, 11, 10);
        EmailAddress[] emailAddresses = repository.loadFromDatabase();
        assertTrue(emailAddresses[0].getEmail().equals("filnik90@gmail.com"));
        assertTrue(emailAddresses[0].getName().equals("John"));
        assertTrue(emailAddresses[0].getLastname().equals("Doe"));
        assertTrue(emailAddresses[0].getDate().getDayOfYear() == date.getDayOfYear());
    }

    @Test
    public void employeeRepositoryDeletesEmailAddressesCorrectly() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        EmailAddress emailAddress = new EmailAddress("lastname", "firstname",
                LocalDateTime.now(), "filnik90@gmail.com");
        repository.store(emailAddress);
        repository.delete(emailAddress);
        EmailAddress[] emailAddresses = repository.load();
        assertTrue(emailAddresses.length == 0);
    }
}
