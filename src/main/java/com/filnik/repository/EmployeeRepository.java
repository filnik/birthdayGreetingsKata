package com.filnik.repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeRepository implements Repository<Employee> {
    private HashMap<String, ArrayList<Employee>> employees = new HashMap<>();

    public ArrayList<Employee> load() {
        ArrayList<Employee> results = new ArrayList<Employee>();
        for (ArrayList<Employee> array : employees.values()){
            results.addAll(array);
        }
        return results;
    }

    public void store(Employee... employees) {
        for (Employee employee : employees) {
            insert(employee.getDate(), employee);
        }
    }

    public void delete(Employee... employees) {
        for (Employee employee : employees) {
            remove(employee);
        }
    }

    public Employee[] load(LocalDateTime date) {
        final boolean isFebruary = date.getMonth().equals(Month.FEBRUARY);
        final boolean todayIsDay28 = date.getDayOfMonth() == 28;
        final boolean todayIsLeapYear = date.getYear() % 4 == 0;

        ArrayList<Employee> results = employees.get(date.getDayOfMonth() + " - " + date.getMonth());
        if (results == null) results = new ArrayList<>();
        if (isFebruary && todayIsDay28 && !todayIsLeapYear){
            ArrayList<Employee> employees29 = employees.get("29 - " + date.getMonth());
            if (employees29 != null) {
                results.addAll(employees29);
            }
        }

        return results.toArray(new Employee[0]);
    }

    private void insert(LocalDateTime date, Employee employee){
        String key = date.getDayOfMonth() + " - " + date.getMonth();
        ArrayList<Employee> array = employees.get(key) == null ? new ArrayList<>() : employees.get(key);
        array.add(employee);
        employees.put(key, array);
    }

    private void remove(Employee employee){
        String key = employee.getDate().getDayOfMonth() + " - " + employee.getDate().getMonth();
        ArrayList<Employee> array = employees.get(key) == null ? new ArrayList<>() : employees.get(key);
        array.remove(employee);
        employees.put(key, array);
    }
}
