package org.apache.tapestry5.internal.services;

import org.apache.tapestry5.services.PropertyOutputContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GridOutputContext
{
    private Map<Object, List<PropertyOutputContext>> rowValueToCellContext;

    public GridOutputContext()
    {
        rowValueToCellContext = new LinkedHashMap<Object, List<PropertyOutputContext>>();
    }

    public void add(PropertyOutputContext propertyOutputContext)
    {
        Object row = propertyOutputContext.getObjectValue();

        if (!rowValueToCellContext.containsKey(row))
            rowValueToCellContext.put(propertyOutputContext.getObjectValue(), new ArrayList<PropertyOutputContext>());

        rowValueToCellContext.get(row).add(propertyOutputContext);
    }

    public Iterable<Object> rows()
    {
        return rowValueToCellContext.keySet();
    }

    public Iterable<PropertyOutputContext> properties(Object row)
    {
        return rowValueToCellContext.get(row);
    }
}
