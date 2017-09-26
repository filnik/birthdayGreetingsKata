package com.filnik.service;

import com.filnik.repository.Employee;
import com.filnik.repository.EmployeeRepository;
import com.filnik.repository.EmployeeRepositoryFactory;

import java.time.LocalDateTime;

public class BirthdayService {
    private final EmployeeRepository employeeRepository;
    private final CommunicationService communicationService;

    public BirthdayService(EmployeeRepository employeeRepository, CommunicationService communicationService) {
        this.employeeRepository = employeeRepository;
        this.communicationService = communicationService;
    }

    public BirthdayService() {
        this(new EmployeeRepositoryFactory().createFromDatabase(), new EmailService());
    }

    public void sendGreetings(LocalDateTime date) {
        Employee[] employees = employeeRepository.load(date);
        for (Employee employee : employees){
            communicationService.send(employee);
        }
    }
}
