package unit;

import com.filnik.repository.Employee;
import com.filnik.repository.EmployeeRepository;
import com.filnik.repository.EmployeeRepositoryFactory;
import com.filnik.service.BirthdayService;
import com.filnik.service.CommunicationService;
import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BirthdayServiceTest {

    private BirthdayService birthdayService;
    private EmailServiceSpy communicationService;
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() throws Exception {
        employeeRepository = new EmployeeRepositoryFactory().createEmptyRepository();
        communicationService = new EmailServiceSpy();
        birthdayService = new BirthdayService(employeeRepository, communicationService);

        employeeRepository.store(new Employee("Furlan",
                "Davide", LocalDateTime.now(), "mobile.pos.test2017@gmail.com"));
    }

    @Test
    public void sendGreetingsToTheRightEmployee() throws Exception {
        birthdayService.sendGreetings(LocalDateTime.now());
        assertTrue(communicationService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
    }

    @Test
    public void sendGreetingsWithTheRightSubjectAndContent() throws Exception {
        employeeRepository.store(new Employee("Furlan",
                "Giovanni", LocalDateTime.now(), "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(LocalDateTime.now());

        assertTrue(communicationService.messages.get(0).getContentText().contains("Davide"));
        assertTrue(communicationService.messages.get(1).getContentText().contains("Giovanni"));
    }

    @Test
    public void sendGreetingsToday() throws Exception {
        employeeRepository.store(new Employee("Furlan",
                "Giovanni", LocalDateTime.of(2017, 01, 03, 01, 10),
                "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(LocalDateTime.now());

        assertTrue(communicationService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
        assertTrue(communicationService.messages.size() == 1);
    }

    @Test
    public void sendGreetingsACertainDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2017, 01, 03, 01, 10);
        employeeRepository.store(new Employee("Furlan",
                "Giovanni", date,
                "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(date);

        assertTrue(communicationService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
        assertTrue(communicationService.messages.size() == 1);
    }

    @Test
    public void sendGreetingsInLeapYear() throws Exception {
        LocalDateTime birthday = LocalDateTime.of(2004, 02, 29, 01, 10);
        LocalDateTime today = LocalDateTime.of(2017, 02, 28, 01, 10);
        employeeRepository.store(new Employee("Furlan",
                "Giovanni", birthday,
                "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(today);

        assertTrue(communicationService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
        assertTrue(communicationService.messages.size() == 1);
    }

    @Test
    public void avoidDoubleGreetingsInLeapYear() throws Exception {
        LocalDateTime birthday = LocalDateTime.of(2004, 02, 29, 01, 10);
        LocalDateTime today = LocalDateTime.of(2016, 02, 28, 01, 10);
        employeeRepository.store(new Employee("Furlan",
                "Giovanni", birthday,
                "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(today);

        assertTrue(communicationService.messages.size() == 0);
    }

    private class EmailServiceSpy implements CommunicationService {
        public ArrayList<JavaMailGmailMessage> messages = new ArrayList<>();

        public EmailServiceSpy() {

        }

        @Override
        public void send(Employee employee) {
            JavaMailGmailMessage message = new JavaMailGmailMessage();
            message.addTo(new com.googlecode.gmail4j.EmailAddress(employee.getEmail()));
            message.setContentText(employee.getName());
            messages.add(message);
        }

        @Override
        public void finalize() throws Throwable {
        }
    }
}
