package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.mixins.ContextMenuAjax;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell.GridOutputContext;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.Constants;
import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

public class GridExamples
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
    void onContextMenuFromGrid1(Employee employee, String propertyName, String propetyValue)
            throws InterruptedException
    {
        Thread.sleep(300);
        this.propertyName = propertyName;
        this.objectValue = employee;
        this.propertyValue = propetyValue;
    }

    @Log
    void onContextMenuFromGrid11(Employee employee, String propertyName, String propertyValue)
            throws InterruptedException
    {
        Thread.sleep(300);
        this.propertyName = propertyName;
        this.objectValue = employee;
        this.propertyValue = propertyValue;
    }

    @Log
    void onContextMenuFromGrid2(Employee employee) throws InterruptedException
    {
        Thread.sleep(300);
        this.objectValue = employee;
    }

    @Log
    void onContextMenuFromGrid3() throws InterruptedException
    {
        Thread.sleep(300);
    }

    @Log
    void onContextMenuFromGrid4(Employee employee, String propertyName, String propertyValue)
    {
        this.propertyName = propertyName;
        this.objectValue = employee;
        this.propertyValue = propertyValue;
    }

    @Log
    void onContextMenuFromGrid5(Employee employee)
    {
        this.objectValue = employee;
    }

    @Log
    void onContextMenuFromGrid6()
    {
    }

    public String getContextMenuAjaxLink()
    {
        return Constants.TAP_CORE_MIXINS + ContextMenuAjax.class.getSimpleName() + ".java";
    }

    public String getGridCellLink()
    {
        return "http://tapestry.apache.org/current/tapestry-core/ref/org/apache/tapestry5/corelib/components/GridCell.html";
    }

    public String getAbstractPropertyOutputLink()
    {
        return "http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/corelib/base/AbstractPropertyOutput.html#renderPropertyValue(org.apache.tapestry5.MarkupWriter, java.lang.String)";
    }

    public String getEnvironmentLink()
    {
        return "http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/services/Environment.html";
    }

    public String getGridOutputContextLink()
    {
        return Constants.CONTEXTMENU_BRANCH + Constants.TAP_CORE_SERVICES + GridOutputContext.class.getSimpleName()
                + ".java";
    }
}
