// Copyright 2010, 2011 The Apache Software Foundation
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

package org.apache.tapestry5.internal.transform;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.KeepRequestParameters;
import org.apache.tapestry5.internal.services.ComponentClassCache;
import org.apache.tapestry5.ioc.util.IdAllocator;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.FieldHandle;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticField;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.runtime.ComponentEvent;
import org.apache.tapestry5.services.ComponentEventHandler;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

/**
 * Hooks the activate event handler on the component (presumably, a page) to extract query parameters, and hooks the
 * link decoration events to extract values and add them to the {@link Link}.
 * 
 * @see ActivationRequestParameter
 * @since 5.2.0
 */
@SuppressWarnings("all")
public class KeepRequestParametersWorker implements ComponentClassTransformWorker2
{
    private final Request request;

    private final ComponentClassCache classCache;

    private final ValueEncoderSource valueEncoderSource;

    public KeepRequestParametersWorker(Request request, ComponentClassCache classCache,
            ValueEncoderSource valueEncoderSource)
    {
        this.request = request;
        this.classCache = classCache;
        this.valueEncoderSource = valueEncoderSource;
    }

    public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model)
    {
        final KeepRequestParameters annotation = plasticClass.getAnnotation(KeepRequestParameters.class);

        if (annotation == null) return;
        ComponentEventHandler handler = new ComponentEventHandler()
        {
            public void handleEvent(Component instance, ComponentEvent event)
            {
                Link link = event.getEventContext().get(Link.class, 0);
                ComponentEventRequestParameters parameters = event.getEventContext().get(
                        ComponentEventRequestParameters.class, 1);

                for (String component : annotation.components())
                {
                    if (parameters.getNestedComponentId().matches(component))
                        for (String parameterName : request.getParameterNames())
                            link.addParameter(parameterName, request.getParameter(parameterName));
                }
            }
        };
        ComponentEventHandler pageHandler = new ComponentEventHandler()
        {
            public void handleEvent(Component instance, ComponentEvent event)
            {
                Link link = event.getEventContext().get(Link.class, 0);

                for (String parameterName : request.getParameterNames())
                    link.addParameter(parameterName, request.getParameter(parameterName));
            }
        };

        support.addEventHandler(
                EventConstants.DECORATE_COMPONENT_EVENT_LINK, 0,
                String.format("KeepRequestParametersWorker decorate component event link event handler"), handler);

        support.addEventHandler(
                EventConstants.DECORATE_PAGE_RENDER_LINK, 0,
                String.format("KeepRequestParametersWorker decorate page render link event handler"), pageHandler);
    }

}
