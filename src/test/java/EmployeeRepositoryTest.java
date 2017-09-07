import com.filnik.repository.EmailAddress;
import com.filnik.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class EmployeeRepositoryTest {

    @Test
    public void employeeRepositoryHasEmailAddresses() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        EmailAddress[] emailAddress = repository.load();
        assertTrue(emailAddress.length > 0);
    }
}
