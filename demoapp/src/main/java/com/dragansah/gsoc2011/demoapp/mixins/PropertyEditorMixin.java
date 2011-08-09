package com.dragansah.gsoc2011.demoapp.mixins;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.services.PropertyEditContext;

public class PropertyEditorMixin
{
    @Environmental
    private PropertyEditContext propertyEditContext;

    void afterRender(MarkupWriter writer)
    {
        writer.element("span").text("context:" + propertyEditContext.getPropertyId());
        writer.end();
    }
}
