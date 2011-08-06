package com.dragansah.gsoc2011.demoapp.services;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Service;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.services.ValueEncoderFactory;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeServiceImpl;

public class DemoAppModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(EmployeeService.class, EmployeeServiceImpl.class);
    }

    @SuppressWarnings("rawtypes")
    public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration,
            @Service("EmployeeService") final EmployeeService employeeService)
    {
        configuration.add(CoercionTuple
                .create(String.class, Employee.class, new Coercion<String, Employee>()
                {
                    public Employee coerce(String input)
                    {
                        return employeeService.findById(Integer.valueOf(input));
                    }
                }));
    }

    @SuppressWarnings("rawtypes")
    public static void contributeValueEncoderSource(
            MappedConfiguration<Class, ValueEncoderFactory> configuration,
            final EmployeeService employeeService)
    {
        configuration.add(Employee.class, new ValueEncoderFactory<Employee>()
        {
            public ValueEncoder<Employee> create(Class<Employee> type)
            {
                return new ValueEncoder<Employee>()
                {
                    public String toClient(Employee value)
                    {
                        return value.getId() + "";
                    }

                    public Employee toValue(String clientValue)
                    {
                        return employeeService.findById(Integer.valueOf(clientValue));
                    }
                };
            }
        });
    }
}
