package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

public class LoopExamples
{
    @SuppressWarnings("unused")
    @Property
    private Employee employee;

    @Inject
    private EmployeeService employeeService;

    public List<Employee> getEmployees()
    {
        return employeeService.findAll();
    }

    @Log
    void onContextMenu(Employee employee)
    {
        this.employee = employee;
    }

}
