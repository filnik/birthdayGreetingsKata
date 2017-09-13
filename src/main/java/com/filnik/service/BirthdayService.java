package com.filnik.service;

import com.filnik.repository.EmailAddress;
import com.filnik.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.time.Month;

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
        boolean isSameDay = date.getDayOfYear() == emailAddress.getDate().getDayOfYear();
        final boolean isFebruary = date.getMonth().equals(Month.FEBRUARY)
                && emailAddress.getDate().getMonth().equals(Month.FEBRUARY);
        final boolean birthdayIs29February = emailAddress.getDate().getDayOfMonth() == 29;
        final boolean todayIsDay28 = date.getDayOfMonth() == 28;
        final boolean todayIsLeapYear = date.getYear() % 4 == 0;

        return isSameDay || ( isFebruary && birthdayIs29February && todayIsDay28 && !todayIsLeapYear);
    }
}
