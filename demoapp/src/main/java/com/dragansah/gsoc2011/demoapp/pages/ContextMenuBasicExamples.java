package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

public class ContextMenuBasicExamples
{
    public String getTest1() throws InterruptedException
    {
        Thread.sleep(1000);
        return "test1";
    }

    public String getTest2() throws InterruptedException
    {
        Thread.sleep(1000);
        return "test2";
    }

    @Log
    void onContextMenu()
    {

    }

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
