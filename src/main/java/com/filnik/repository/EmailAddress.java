package com.filnik.repository;

import java.time.LocalDateTime;

//last_name, first_name, date_of_birth, email
public class EmailAddress {
    private final String lastName;
    private final String firstName;
    private final LocalDateTime dateOfBirth;
    private final String email;

    public EmailAddress(String lastName, String firstName, LocalDateTime dateOfBirth, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }
}
