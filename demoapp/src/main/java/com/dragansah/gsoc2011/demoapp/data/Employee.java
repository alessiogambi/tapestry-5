package com.dragansah.gsoc2011.demoapp.data;

import org.apache.tapestry5.beaneditor.NonVisual;

public class Employee
{
    private Integer id;

    private String firstName;

    private String lastName;

    private Integer salary;

    private Employee(Integer id, String firstName, String lastName, Integer salaryEuros)
    {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salaryEuros;
    }

    public static Employee newEmployee(Integer id, String firstName, String lastName,
            Integer salaryEuros)
    {
        return new Employee(id, firstName, lastName, salaryEuros);
    }

    public static Employee newEmployee(Employee employee)
    {
        return new Employee(employee.id, employee.firstName, employee.lastName, employee.salary);
    }

    @NonVisual
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Integer getSalary()
    {
        return salary;
    }

    public void setSalary(Integer salary)
    {
        this.salary = salary;
    }

    public String getName()
    {
        return firstName + " " + lastName;
    }

    @Override
    public String toString()
    {
        return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary
                + "]";
    }
}
