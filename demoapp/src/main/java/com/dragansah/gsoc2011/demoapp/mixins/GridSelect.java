package com.dragansah.gsoc2011.demoapp.mixins;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.EmbeddedMixin;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell.GridOutputContext;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class GridSelect
{
    @SuppressWarnings("unused")
    @EmbeddedMixin("rows.gridcell")
    private ContextMenuGridCell contextMenuGridCell;

    @Environmental
    private GridOutputContext gridOutputContext;

    @Environmental
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    void afterRender(MarkupWriter writer)
    {
        // render a dummy zone
        String zoneId = javaScriptSupport.allocateClientId(resources);
        Element e = writer.element("div", "id", zoneId);
        e.addClassName("t-zone");
        writer.end();
        
        JSONObject spec = new JSONObject("element", zoneId);
        
        int i = 0;
        for (Object row : gridOutputContext.rows())
        {
            Link link = resources.createEventLink("gridSelect", row);
            javaScriptSupport
                    .addScript(
                            "$('%s').previousSibling.select('tbody tr')[%d].onclick = "
                                    + "function(){"
                                    + "if(this.hasClassName('t-selected')) { if(window.isSelected == undefined) {var isSelected = true;} else {isSelected = true;}};"
                                    + "$('%s').previousSibling.select('tbody tr').each(function(item){item.removeClassName('t-selected');});"
                                    + "if(!isSelected) {this.addClassName('t-selected');}; "
                                    + "new Tapestry.ZoneManager(%s).updateFromURL('%s');};", zoneId, i++, zoneId, spec,
                            link);
        }

    }
}
