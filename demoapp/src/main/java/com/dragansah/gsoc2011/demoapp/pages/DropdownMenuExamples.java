package com.dragansah.gsoc2011.demoapp.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.util.ArrayList;
import java.util.List;

import com.dragansah.gsoc2011.demoapp.data.Employee;
import com.dragansah.gsoc2011.demoapp.services.dao.EmployeeService;

public class DropdownMenuExamples
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

    @InjectComponent
    private Grid grid1;

    @Property
    private String sortColumn;

    @SuppressWarnings("unchecked")
    @Cached
    public List<String> getSortColumns()
    {
        List<String> sortableColumns = new ArrayList<String>();
        sortableColumns.addAll(grid1.getDataModel().getPropertyNames());
        for (Object col : grid1.getDataModel().getPropertyNames())
            if (!grid1.getDataModel().get((String) col).isSortable())
            {
                sortableColumns.remove(col);
            }

        return sortableColumns;
    }

    @Inject
    private PageRenderLinkSource linkSource;

    public Link getIndexLink()
    {
        return linkSource.createPageRenderLink(Index.class);
    }

    @Inject
    private ComponentResources resources;

    public Link getSortColumnLink()
    {
        Link link = linkSource.createPageRenderLink(DropdownMenuExamples.class);
        link.addParameter("sort", sortColumn);
        if (grid1.getSortModel().getColumnSort(sortColumn) == ColumnSort.ASCENDING)
            link.addParameter("sortorder", "desc");
        else
            link.addParameter("sortorder", "asc");
        return link;
    }

    public Link getResetSortLink()
    {
        return resources.createEventLink("resetSort");
    }

    void onResetSort()
    {
        grid1.getSortModel().clear();
    }
}
