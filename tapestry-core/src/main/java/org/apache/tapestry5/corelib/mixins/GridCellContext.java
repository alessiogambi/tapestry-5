// Copyright 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.corelib.mixins;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PropertyConduit;
import org.apache.tapestry5.corelib.base.ContextMenuBase;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.GridRows;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This mixin is meant to be used exclusively with {@link org.apache.tapestry5.corelib.components.GridCell}. It is
 * mostly used as an embedded mixin.
 * <p>
 * The mixin exposes GridCell context data in the {@link org.apache.tapestry5.annotations.AfterRender} render phase, in
 * the {@link Environment} as {@link GridOutputContext} which is basically a collection of {@link GridCellOutputContext}.
 * 
 * @see {@link ContextMenuBase}
 * @since 5.3
 * @tapestrydoc
 */
public class GridCellContext
{
    public class GridCellOutputContext
    {
        private final Object objectValue;

        private final String propertyName;

        private final Object propertyValue;

        public GridCellOutputContext(Object objectValue, String propertyName, Object propertyValue)
        {
            super();
            this.objectValue = objectValue;
            this.propertyName = propertyName;
            this.propertyValue = propertyValue;
        }

        public Object getObjectValue()
        {
            return objectValue;
        }

        public String getPropertyName()
        {
            return propertyName;
        }

        public Object getPropertyValue()
        {
            return propertyValue;
        }

    }

    public class GridOutputContext
    {

        private Map<Object, List<GridCellOutputContext>> rowValueToCellContext;

        public GridOutputContext()
        {
            rowValueToCellContext = new LinkedHashMap<Object, List<GridCellOutputContext>>();
        }

        public void add(GridCellOutputContext gridCellOutputContext)
        {
            Object row = gridCellOutputContext.getObjectValue();

            if (!rowValueToCellContext.containsKey(row))
                rowValueToCellContext.put(
                        gridCellOutputContext.getObjectValue(), new ArrayList<GridCellOutputContext>());

            rowValueToCellContext.get(row).add(gridCellOutputContext);
        }

        public Iterable<Object> rows()
        {
            return rowValueToCellContext.keySet();
        }

        public Iterable<GridCellOutputContext> properties(Object row)
        {
            return rowValueToCellContext.get(row);
        }
    }

    @Inject
    private Environment environment;

    @Inject
    private ComponentResources resources;

    void afterRender()
    {
        GridOutputContext gridOutputContext = environment.peek(GridOutputContext.class);
        if (gridOutputContext == null)
        {
            gridOutputContext = new GridOutputContext();
            environment.push(GridOutputContext.class, gridOutputContext);
        }

        ComponentResources gridCellResources = resources.getContainer().getComponentResources();
        GridRows gridRows = (GridRows) gridCellResources.getContainer();
        ComponentResources gridRowsResources = gridCellResources.getContainer().getComponentResources();
        Grid grid = (Grid) gridRowsResources.getContainer();

        final Object objectValue = grid.getRow();
        final String propertyName = gridRows.getPropertyName();
        PropertyConduit propertyConduit = grid.getDataModel().get(gridRows.getPropertyName()).getConduit();
        final Object propertyValue = propertyConduit != null ? propertyConduit.get(objectValue) : null;

        gridOutputContext.add(new GridCellOutputContext(objectValue, propertyName, propertyValue));
    }

}
