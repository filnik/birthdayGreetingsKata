package com.filnik.service;

import com.filnik.repository.EmployeeRepository;

import java.time.LocalDateTime;

public class BirthdayService {
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public BirthdayService(EmployeeRepository employeeRepository, EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    public void sendGreetings(LocalDateTime today) {

    }
}
