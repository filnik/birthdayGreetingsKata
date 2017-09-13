package com.filnik.service;

import com.filnik.repository.EmailAddress;
import com.filnik.repository.EmployeeRepository;

import java.time.LocalDateTime;

public class BirthdayService {
    public static final String MAIL_SUBJECT = "Happy birthday!";
    public static final String MAIL_CONTENT = "Happy birthday, dear %s!";
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public BirthdayService(EmployeeRepository employeeRepository, EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.employeeRepository.loadFromDatabase();
        this.emailService = emailService;
    }

    public void sendGreetings(LocalDateTime date) {
        EmailAddress[] emailAddresses = employeeRepository.load();
        for (EmailAddress emailAddress : emailAddresses){
            if (shouldISendMail(date, emailAddress)) {
                emailService.send(emailAddress.getEmail(), MAIL_SUBJECT,
                        String.format(MAIL_CONTENT, emailAddress.getName()), "");
            }
        }
    }

    private boolean shouldISendMail(LocalDateTime date, EmailAddress emailAddress) {
        return date.getMonth().equals(emailAddress.getDate().getMonth()) &&
                date.getDayOfYear() == emailAddress.getDate().getDayOfYear();
    }
}
