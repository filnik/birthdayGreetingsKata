package com.filnik.repository;

import java.time.LocalDateTime;

//last_name, first_name, date_of_birth, email
public class Employee {
    private final String lastName;
    private final String firstName;
    private final LocalDateTime dateOfBirth;
    private final String email;

    public Employee(String lastName, String firstName, LocalDateTime dateOfBirth, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee)) return false;
        Employee other = (Employee) obj;
        return other.lastName != null && other.lastName.equals(lastName)
                && other.firstName != null && other.firstName.equals(firstName);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return firstName;
    }

    public LocalDateTime getDate() {
        return dateOfBirth;
    }

    public String getLastname() {
        return lastName;
    }
}
