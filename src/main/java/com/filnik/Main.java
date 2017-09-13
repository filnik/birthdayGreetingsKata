package com.filnik;

import com.filnik.repository.EmployeeRepository;
import com.filnik.service.BirthdayService;
import com.filnik.service.EmailService;

import java.time.LocalDateTime;

public class Main {
    /*
    - Missing 29 february handling
     */

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
        EmailService emailService = new EmailService();
        BirthdayService birthdayService = new BirthdayService(
                employeeRepository, emailService);
        birthdayService.sendGreetings(today());
    }

    private static LocalDateTime today() {
        return LocalDateTime.now();
    }
}
