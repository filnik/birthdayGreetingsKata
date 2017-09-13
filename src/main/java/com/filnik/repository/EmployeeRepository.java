package com.filnik.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EmployeeRepository implements Repository<EmailAddress[]> {
    private String path = "/Users/filippo/Documents/study/hexagonal-architecture-kata/src/main/resources/sources.txt";
    private ArrayList<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

    public EmailAddress[] loadFromDatabase() {
        String fileContent = readFromFile();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] elements = line.split(", ");
            if (elements[0].equals("last_name")) continue;
            emailAddresses.add(new EmailAddress(elements[0], elements[1], LocalDateTime.now(), elements[3]));
        }
        return load();
    }

    public EmailAddress[] load() {
        return emailAddresses.toArray(new EmailAddress[0]);
    }

    public void store(EmailAddress... emailAddress) {
        Collections.addAll(emailAddresses, emailAddress);
    }

    public void delete(EmailAddress... address) {
        emailAddresses.removeAll(Arrays.asList(address));
    }

    private String readFromFile(){
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            return "";
        }
    }
}
