package com.filnik.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class EmployeeRepository implements Repository<EmailAddress[]> {
    private String path = "/Users/filippo/Documents/study/hexagonal-architecture-kata/src/main/resources/sources.txt";
    private ArrayList<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();

    public EmailAddress[] load() {
        if (emailAddresses.size() == 0) {
            String fileContent = readFromFile();
            String[] lines = fileContent.split("\n");
            for (String line : lines) {
                if (line.equals("last_name")) continue;
                String[] elements = line.split(", ");
                emailAddresses.add(new EmailAddress(elements[0], elements[1], LocalDateTime.now(), elements[3]));
            }
        }

        return emailAddresses.toArray(new EmailAddress[0]);
    }

    public void store(EmailAddress... emailAddress) {
        Collections.addAll(emailAddresses, emailAddress);
    }

    public void delete() {

    }

    private String readFromFile(){
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            return "";
        }
    }
}
