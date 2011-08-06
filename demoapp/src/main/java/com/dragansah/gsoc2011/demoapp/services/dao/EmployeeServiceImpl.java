package com.dragansah.gsoc2011.demoapp.services.dao;

import static com.dragansah.gsoc2011.demoapp.data.Employee.newEmployee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dragansah.gsoc2011.demoapp.data.Employee;

public class EmployeeServiceImpl implements EmployeeService
{
    List<Employee> employees;

    List<Employee> employees100;

    Map<Integer, Employee> idToEmployee;

    public EmployeeServiceImpl()
    {
        employees = Arrays
                .asList(newEmployee(1, "Jack", "Donaghy", 7000), newEmployee(2, "Liz", "Lemon", 3000), newEmployee(3, "Tracy", "Jordan", 5000));

        idToEmployee = new LinkedHashMap<Integer, Employee>();
        for (Employee e : employees)
            idToEmployee.put(e.getId(), e);

        employees100 = new ArrayList<Employee>();
        for (int i = 0; i < 100; i++)
        {
            Employee employee = Employee.newEmployee(employees.get(i % employees.size()));
            employee.setFirstName(employee.getFirstName() + " " + (i + 1));
            employee.setLastName(employee.getLastName() + " " + (i + 1));
            employees100.add(employee);
        }
    }

    public List<Employee> findAll()
    {
        return employees;
    }

    public Employee findById(Integer employeeeId)
    {
        return idToEmployee.get(employeeeId);
    }

    public List<Employee> find100()
    {
        return employees100;
    }
}
