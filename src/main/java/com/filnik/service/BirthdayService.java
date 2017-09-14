package com.filnik.service;

import com.filnik.repository.Employee;
import com.filnik.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.time.Month;

public class BirthdayService {
    private final EmployeeRepository employeeRepository;
    private final CommunicationService communicationService;

    public BirthdayService(EmployeeRepository employeeRepository, CommunicationService communicationService) {
        this.employeeRepository = employeeRepository;
        this.employeeRepository.loadFromDatabase();
        this.communicationService = communicationService;
    }

    public void sendGreetings(LocalDateTime date) {
        Employee[] employees = employeeRepository.load(date);
        for (Employee employee : employees){
            communicationService.send(employee);
        }
    }
}
