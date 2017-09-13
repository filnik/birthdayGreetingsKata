package com.filnik.service;

import com.googlecode.gmail4j.EmailAddress;
import com.googlecode.gmail4j.javamail.ImapGmailClient;
import com.googlecode.gmail4j.javamail.ImapGmailConnection;
import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;

public class EmailService {
    private static final String EMAIL = "mobile.pos.test2017@gmail.com";
    private static final String PASSWORD = "mp0$_t3$t345lom870";
    private final ImapGmailClient client;

    public EmailService() {
        client = new ImapGmailClient();
        client.setConnection(new ImapGmailConnection(EMAIL, PASSWORD.toCharArray()));
    }

    public void send(String email, String subject, String content, String contentType) throws Exception {
        final JavaMailGmailMessage message = new JavaMailGmailMessage();
        message.setSubject(subject);
        message.setContentText(content);
        message.addTo(new EmailAddress(email));
        client.send(message);
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        client.disconnect();
    }
}
