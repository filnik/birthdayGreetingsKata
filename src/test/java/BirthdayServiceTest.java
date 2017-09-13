import com.filnik.repository.EmailAddress;
import com.filnik.repository.EmployeeRepository;
import com.filnik.service.BirthdayService;
import com.filnik.service.EmailService;
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
    private EmailServiceSpy mailService;
    private EmployeeRepositorySpy employeeRepository;

    @Before
    public void setUp() throws Exception {
        employeeRepository = new EmployeeRepositorySpy();
        mailService = new EmailServiceSpy();
        birthdayService = new BirthdayService(employeeRepository, mailService);

        employeeRepository.store(new EmailAddress("Furlan",
                "Davide", LocalDateTime.now(), "mobile.pos.test2017@gmail.com"));
    }

    @Test
    public void sendGreetingsToTheRightEmployee() throws Exception {
        birthdayService.sendGreetings(LocalDateTime.now());
        assertTrue(mailService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
    }

    @Test
    public void sendGreetingsWithTheRightSubjectAndContent() throws Exception {
        employeeRepository.store(new EmailAddress("Furlan",
                "Giovanni", LocalDateTime.now(), "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(LocalDateTime.now());

        assertTrue(mailService.messages.get(0).getSubject().equals("Happy birthday!"));
        assertTrue(mailService.messages.get(0).getContentText().equals("Happy birthday, dear Davide!"));
        assertTrue(mailService.messages.get(1).getSubject().equals("Happy birthday!"));
        assertTrue(mailService.messages.get(1).getContentText().equals("Happy birthday, dear Giovanni!"));
    }

    @Test
    public void sendGreetingsToday() throws Exception {
        employeeRepository.store(new EmailAddress("Furlan",
                "Giovanni", LocalDateTime.of(2017, 01, 03, 01, 10),
                "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(LocalDateTime.now());

        assertTrue(mailService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
        assertTrue(mailService.messages.size() == 1);
    }

    @Test
    public void sendGreetingsACertainDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2017, 01, 03, 01, 10);
        employeeRepository.store(new EmailAddress("Furlan",
                "Giovanni", date,
                "mobile.pos.test2017@gmail.com"));

        birthdayService.sendGreetings(date);

        assertTrue(mailService.messages.get(0).getTo().get(0).getEmail().equals("mobile.pos.test2017@gmail.com"));
        assertTrue(mailService.messages.size() == 1);
    }

    private class EmployeeRepositorySpy extends EmployeeRepository{

        public EmployeeRepositorySpy() {
        }

        @Override
        public EmailAddress[] loadFromDatabase() {
            return new EmailAddress[]{};
        }
    }

    private class EmailServiceSpy extends EmailService {
        public ArrayList<JavaMailGmailMessage> messages = new ArrayList<>();

        public EmailServiceSpy() {

        }

        @Override
        public void send(String email, String subject, String content, String contentType) {
            JavaMailGmailMessage message = new JavaMailGmailMessage();
            message.addTo(new com.googlecode.gmail4j.EmailAddress(email));
            message.setSubject(subject);
            message.setContentText(content);
            messages.add(message);
        }

        @Override
        public void finalize() throws Throwable {
        }
    }
}
