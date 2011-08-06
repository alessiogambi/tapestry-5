package com.dragansah.gsoc2011.demoapp.services.dao;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;

public interface EmployeeService
{
    List<Employee> findAll();

    List<Employee> find100();

    Employee findById(Integer employeeeId);
}
