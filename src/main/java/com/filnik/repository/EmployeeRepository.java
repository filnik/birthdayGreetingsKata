package com.filnik.repository;

public class EmployeeRepository implements Repository<EmailAddress[]> {
    public EmailAddress[] load() {
        return new EmailAddress[]{new EmailAddress()};
    }

    public void store(EmailAddress... emailAddress) {

    }

    public void delete() {

    }
}
