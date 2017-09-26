package unit;

import com.filnik.repository.Employee;
import com.filnik.service.EmailService;
import com.filnik.service.MailMessage;
import com.googlecode.gmail4j.EmailAddress;
import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.javamail.ImapGmailClient;
import com.googlecode.gmail4j.javamail.ImapGmailConnection;
import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {
    private static final String EMAIL = "mobile.pos.test2017@gmail.com";
    private static final String PASSWORD = "mp0$_t3$t345lom870";
    private static final String MAIL_SUBJECT = "Happy birthday!";
    private static final String MAIL_CONTENT = "Happy birthday, dear firstName!";
    private static final String FIRST_NAME = "firstName";
    @Mock ImapGmailClient client;

    @Test
    public void employeeRepositoryHasEmailAddresses() throws Exception {
        EmailService emailService = new EmailService(client);
        Employee employee = new Employee("lastName", FIRST_NAME, LocalDateTime.now(), EMAIL);

        final MailMessage message = new MailMessage();
        message.setSubject(MAIL_SUBJECT);
        message.setContentText(String.format(MAIL_CONTENT, employee.getName()));
        message.addTo(new EmailAddress(employee.getEmail()));
        message.setFrom(new EmailAddress("test@hexagonal.it"));

        emailService.send(employee);

        verify(client, times(1)).send(message);
    }
}
