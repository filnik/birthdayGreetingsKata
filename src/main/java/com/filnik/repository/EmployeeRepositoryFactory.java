package com.filnik.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class EmployeeRepositoryFactory {
    private String path = "/Users/filippo/Documents/study/hexagonal-architecture-kata/src/main/resources/sources.txt";

    public EmployeeRepository createFromDatabase(){
        String[] lines = readFromFile();
        EmployeeRepository employeeRepository = createEmptyRepository();
        for (String line : lines) {
            if (line.contains("last_name")) continue;
            final Employee employee = parseLine(line);
            employeeRepository.store(employee);
        }
        return employeeRepository;
    }

    private Employee parseLine(String line) {
        String[] elements = line.split(", ");
        String[] dateValues = elements[2].split("/");
        final int year = Integer.parseInt(dateValues[0]);
        final int month = Integer.parseInt(dateValues[1]);
        final int dayOfMonth = Integer.parseInt(dateValues[2]);
        LocalDateTime date = LocalDateTime.of(year, month,
                dayOfMonth, 1, 1);
        final Employee employee = new Employee(elements[0], elements[1], date, elements[3]);
        return employee;
    }

    public EmployeeRepository createEmptyRepository(){
        return new EmployeeRepository();
    }

    private String[] readFromFile(){
        try {
            return new String(Files.readAllBytes(Paths.get(path))).split("\n");
        } catch (IOException e) {
            return new String[]{""};
        }
    }
}
