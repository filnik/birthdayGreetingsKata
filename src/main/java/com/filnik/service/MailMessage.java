package com.filnik.service;

import com.googlecode.gmail4j.javamail.JavaMailGmailMessage;

public class MailMessage extends JavaMailGmailMessage {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MailMessage)){
            return false;
        }
        MailMessage otherMessage = (MailMessage) obj;
        boolean sameSubject = getSubject().equals(otherMessage.getSubject());
        boolean sameContent = getContentText().equals(otherMessage.getContentText());

        return sameSubject && sameContent;
    }
}
