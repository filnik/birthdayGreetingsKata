package com.filnik;

public class Main {

    public static void main(String[] args) {
        BirthdayService birthdayService = new BirthdayService(
                employeeRepository, emailService);
        birthdayService.sendGreetings(today());
    }
}
