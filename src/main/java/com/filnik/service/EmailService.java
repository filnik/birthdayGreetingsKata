package com.filnik.service;

import com.filnik.repository.Employee;
import com.googlecode.gmail4j.EmailAddress;
import com.googlecode.gmail4j.javamail.ImapGmailClient;
import com.googlecode.gmail4j.javamail.ImapGmailConnection;
import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;

public class EmailService implements CommunicationService{
    private static final String EMAIL = "mobile.pos.test2017@gmail.com";
    private static final String PASSWORD = "mp0$_t3$t345lom870";

    private static final String MAIL_SUBJECT = "Happy birthday!";
    private static final String MAIL_CONTENT = "Happy birthday, dear %s!";

    private final ImapGmailClient client;

    public EmailService() {
        this(new ImapGmailClient());
        client.setConnection(new ImapGmailConnection(EMAIL, PASSWORD.toCharArray()));
    }

    public EmailService(ImapGmailClient client) {
        this.client = client;
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        client.disconnect();
    }

    @Override
    public void send(Employee employee) {
        final MailMessage message = new MailMessage();
        message.setSubject(MAIL_SUBJECT);
        message.setContentText(String.format(MAIL_CONTENT, employee.getName()));
        message.addTo(new EmailAddress(employee.getEmail()));
        message.setFrom(new EmailAddress("test@hexagonal.it"));
        client.send(message);
    }
}
