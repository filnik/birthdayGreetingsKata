import com.filnik.repository.Employee;
import com.filnik.service.EmailService;
import com.googlecode.gmail4j.GmailClient;
import com.googlecode.gmail4j.GmailMessage;
import com.googlecode.gmail4j.javamail.ImapGmailClient;
import com.googlecode.gmail4j.javamail.ImapGmailConnection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class EmailServiceTest {
    private static final String EMAIL = "mobile.pos.test2017@gmail.com";
    private static final String PASSWORD = "mp0$_t3$t345lom870";
    private static final String MAIL_SUBJECT = "Happy birthday!";
    public static final String FIRST_NAME = "firstName";
    private ImapGmailClient client;
    private List<GmailMessage> messages;

    @Test
    public void employeeRepositoryHasEmailAddresses() throws Exception {
        client = new ImapGmailClient();
        client.setConnection(new ImapGmailConnection(EMAIL, PASSWORD.toCharArray()));

        EmailService emailService = new EmailService();
        Employee employee = new Employee("lastName", FIRST_NAME, LocalDateTime.now(), EMAIL);
        emailService.send(employee);

        messages = client.getMessagesBy(GmailClient.EmailSearchStrategy.SUBJECT, MAIL_SUBJECT);
        assertTrue(messages.size() > 0);
        assertTrue(messages.get(0).getContentText().contains(FIRST_NAME));
    }

    @After
    public void tearDown() throws Exception {
        client.moveToTrash(messages.toArray(new GmailMessage[messages.size()]));
    }
}
