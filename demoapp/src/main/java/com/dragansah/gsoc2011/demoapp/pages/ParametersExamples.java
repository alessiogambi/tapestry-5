package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.contextmenu.ContextMenuClientEvent;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

public class ParametersExamples
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

    @SuppressWarnings("unused")
    @Property
    private String propertyName;

    @SuppressWarnings("unused")
    @Property
    private Employee objectValue;

    @SuppressWarnings("unused")
    @Property
    private String propertyValue;

    @Log
    void onContextMenu(Employee employee, String propertyName, String propetyValue)
            throws InterruptedException
    {
        Thread.sleep(300);
        this.propertyName = propertyName;
        this.objectValue = employee;
        this.propertyValue = propetyValue;
    }

    @SuppressWarnings("unused")
    @Property
    private ContextMenuClientEvent clientEvent;

    public ContextMenuClientEvent[] getClientEvents()
    {
        return new ContextMenuClientEvent[]
        { ContextMenuClientEvent.CONTEXT, ContextMenuClientEvent.MOUSEDOWN,
                ContextMenuClientEvent.MOUSEOVER, ContextMenuClientEvent.MOUSEMOVE };
    }

}
