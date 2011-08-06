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

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.MixinAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.PropertyOutputContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@MixinAfter
public class ContextMenuGridCell
{
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
    
    @Environmental
    private PropertyOutputContext propertyOutputContext;

    @Inject
    private Environment environment;
    
    void afterRender()
    {        
        GridOutputContext gridOutputContext = environment.peek(GridOutputContext.class);
        if (gridOutputContext == null)
        {
            gridOutputContext = new GridOutputContext();
            environment.push(GridOutputContext.class, gridOutputContext);
        }

        gridOutputContext.add(propertyOutputContext);
    }

}
