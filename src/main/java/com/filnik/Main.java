package com.filnik;

import com.filnik.service.BirthdayService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        BirthdayService birthdayService = new BirthdayService();
        birthdayService.sendGreetings(today());
    }

    private static LocalDateTime today() {
        return LocalDateTime.now();
    }
}
