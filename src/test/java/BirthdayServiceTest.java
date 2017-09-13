import com.filnik.repository.EmailAddress;
import com.filnik.repository.EmployeeRepository;
import com.filnik.service.BirthdayService;
import com.filnik.service.EmailService;
import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.javamail.ImapGmailClient;
import com.googlecode.gmail4j.javamail.ImapGmailConnection;
import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BirthdayServiceTest {

    @Test
    public void sendGreetingsToTheRightEmployee() throws Exception {
        EmployeeRepository employeeRepository = new EmployeeRepositorySpy();
        EmailServiceSpy mailService = new EmailServiceSpy();
        BirthdayService birthdayService = new BirthdayService(employeeRepository, mailService);
        birthdayService.sendGreetings(LocalDateTime.now());

        assertTrue(mailService.MESSAGE.getSubject().equals("Happy birthday!"));
        assertTrue(mailService.MESSAGE.getContentText().equals("Happy birthday, dear John!"));
    }

    private class EmployeeRepositorySpy extends EmployeeRepository{
        @Override
        public EmailAddress[] loadFromDatabase() {
            return super.loadFromDatabase();
        }

        @Override
        public EmailAddress[] load() {
            return new EmailAddress[]{};
        }

        @Override
        public void store(EmailAddress... emailAddress) {

        }

        @Override
        public void delete(EmailAddress... address) {

        }
    }

    private class EmailServiceSpy extends EmailService {
        public JavaMailGmailMessage MESSAGE;

        public EmailServiceSpy() {

        }

        @Override
        public void send(String email, String subject, String content, String contentType) {
            MESSAGE = new JavaMailGmailMessage();
            MESSAGE.addTo(new com.googlecode.gmail4j.EmailAddress(email));
            MESSAGE.setSubject(subject);
            MESSAGE.setContentText(content);
        }

        @Override
        public void finalize() throws Throwable {
        }
    }
}
