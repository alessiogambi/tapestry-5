package com.dragansah.gsoc2011.demoapp.pages;

import static com.dragansah.gsoc2011.demoapp.Constants.CONTEXTMENU_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_MIXINS;
import static com.dragansah.gsoc2011.demoapp.Constants.GRID_ENHANCEMENTS_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_MIXINS;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.corelib.mixins.ContextMenuAjax;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell;
import org.apache.tapestry5.corelib.mixins.GridColumnSort;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.mixins.BeanEditFormMixin;
import com.dragansah.gsoc2011.demoapp.mixins.GridSelect;
import com.dragansah.gsoc2011.demoapp.mixins.PropertyEditorMixin;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

public class EmbeddedMixinExamples
{
    @Property
    private Employee employee;

    @Inject
    private EmployeeService employeeService;

    public List<Employee> getEmployees()
    {
        return employeeService.findAll();
    }

    @InjectComponent
    private Zone beanEditFormZone;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Log
    void onGridSelect(Employee employee)
    {
        this.employee = employee;
        ajaxResponseRenderer.addRender(beanEditFormZone);
    }

    public String getPropertyEditorMixinLink()
    {
        return DEMOAPP_BRANCH + DEMOAPP_MIXINS + PropertyEditorMixin.class.getSimpleName() + ".java";
    }

    public String getBeanEditFormMixinLink()
    {
        return DEMOAPP_BRANCH + DEMOAPP_MIXINS + BeanEditFormMixin.class.getSimpleName() + ".java";
    }

    public String getGridSelectLink()
    {
        return DEMOAPP_BRANCH + DEMOAPP_MIXINS + GridSelect.class.getSimpleName() + ".java";
    }
    
    public String getContextMenuGridCellLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenuGridCell.class.getSimpleName() + ".java";
    }
}
