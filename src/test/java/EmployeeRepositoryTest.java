import com.filnik.repository.Employee;
import com.filnik.repository.EmployeeRepository;
import com.filnik.repository.EmployeeRepositoryFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class EmployeeRepositoryTest {

    @Test
    public void employeeRepositoryHasEmailAddresses() throws Exception {
        EmployeeRepository repository = new EmployeeRepositoryFactory().createFromDatabase();
        Employee[] employees = repository.load();
        assertTrue(employees.length == 2);
    }

    @Test
    public void employeeRepositoryStoresEmailAddressesCorrectly() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        Employee employee = new Employee("lastname", "firstname",
                LocalDateTime.now(), "filnik90@gmail.com");
        repository.store(employee);
        Employee[] employees = repository.load();
        assertTrue(employees.length == 1);
    }

    @Test
    public void employeeRepositoryLoadsCorrectly() throws Exception {
        LocalDateTime date = LocalDateTime.of(1982, 10, 8, 11, 10);
        final EmployeeRepository employeeRepository = new EmployeeRepositoryFactory().createFromDatabase();
        Employee[] employees = employeeRepository.load();
        assertTrue(employees[0].getEmail().equals("filnik90@gmail.com"));
        assertTrue(employees[0].getName().equals("John"));
        assertTrue(employees[0].getLastname().equals("Doe"));
        assertTrue(employees[0].getDate().getDayOfYear() == date.getDayOfYear());
    }

    @Test
    public void employeeRepositoryFiltersEmployeesCorrectly() throws Exception {
        LocalDateTime date = LocalDateTime.of(1982, 10, 8, 11, 10);
        final EmployeeRepository employeeRepository = new EmployeeRepositoryFactory().createFromDatabase();
        assertTrue(employeeRepository.load().length == 2);
        assertTrue(employeeRepository.load(date).length == 1);
    }

    @Test
    public void employeeRepositoryDeletesEmailAddressesCorrectly() throws Exception {
        EmployeeRepository repository = new EmployeeRepository();
        Employee employee = new Employee("lastname", "firstname",
                LocalDateTime.now(), "filnik90@gmail.com");
        repository.store(employee);
        repository.delete(employee);
        Employee[] employees = repository.load();
        assertTrue(employees.length == 0);
    }
}
