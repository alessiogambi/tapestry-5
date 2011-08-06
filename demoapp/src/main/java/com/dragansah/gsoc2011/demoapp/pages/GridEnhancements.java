package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.KeepRequestParameters;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

@KeepRequestParameters(components="g.*")
public class GridEnhancements
{
    @Inject
    private EmployeeService employeeService;

    public List<Employee> getEmployees100()
    {
        return employeeService.find100();
    }
}
