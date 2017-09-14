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
        client = new ImapGmailClient();
        client.setConnection(new ImapGmailConnection(EMAIL, PASSWORD.toCharArray()));
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        client.disconnect();
    }

    @Override
    public void send(Employee employee) {
        final JavaMailGmailMessage message = new JavaMailGmailMessage();
        message.setSubject(MAIL_SUBJECT);
        message.setContentText(String.format(MAIL_CONTENT, employee.getName()));
        message.addTo(new EmailAddress(employee.getEmail()));
        client.send(message);
    }
}
