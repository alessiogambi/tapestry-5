package org.apache.tapestry5.corelib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.Renderable;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.ProgressiveDisplay;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.runtime.RenderQueue;
import org.apache.tapestry5.services.PropertyOutputContext;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class ContextMenuAjax extends ContextMenuBase
{
    /**
     * The initial content to display until the actual content arrives. Defaults to "Loading ..." and an Ajax activity
     * icon, controlled by the css class t-loading which is also used in {@link ProgressiveDisplay}.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "prop:initial")
    private Block initial;

    /**
     * Name of a function on the client-side Tapestry.ElementEffect object that is invoked after the elements's body
     * content has been updated. If not specified, then the basic "highlight" method is used, which performs a classic
     * "yellow fade" to indicate to the user that and update has taken place.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String update;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private ComponentResources resources;

    public Renderable getInitial()
    {
        return new Renderable()
        {
            public void render(MarkupWriter writer)
            {
                // The div containing the actual contextmenu
                writer.element("div").addClassName("t-contextmenu t-loading");
                writer.write(resources.getMessages().get("context-menu-loading"));
                writer.end();
            }
        };
    }

    @Override
    protected RenderCommand renderMenu(final JSONObject spec, final String contextMenuId, final Object[] context)
    {
        /**
         * Link used to trigger ajax update on the zone surrounding the context menu. The event is caught in this mixin.
         */
        Link link = resources.createEventLink("showMenu", context);

        final String zoneId = "zone-" + contextMenuId;
        // spec for the Tapestry.ZoneManager(spec);
        JSONObject zoneManagerSpec = new JSONObject("element", zoneId, "url", link.toURI());

        if (InternalUtils.isNonBlank(update))
        {
            zoneManagerSpec.put("update", update.toLowerCase());
        }

        spec.put("zoneManagerSpec", zoneManagerSpec);

        javaScriptSupport.addInitializerCall("contextMenu", spec);

        return new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                queue.push(new RenderCommand()
                {
                    public void render(MarkupWriter writer, RenderQueue queue)
                    {
                        writer.end(); // end zone

                        writer.end(); // end contextmenu
                    }
                });

                queue.push((RenderCommand) initial);

                queue.push(new RenderCommand()
                {
                    public void render(MarkupWriter writer, RenderQueue queue)
                    {
                        /**
                         * Rendering a zone inside the context menu. We don't use the zone as the context menu because
                         * if we are trying to hide the ContextMenu (example onMouseout), and if the zone is not yet
                         * updated we won't be able to update the zone's style.display because of the zone update, so
                         * the zone will stay visible when it's not supposed to.
                         */

                        // the context menu
                        writer.element("div", "id", contextMenuId, "style", "display: none; position: absolute;")
                                .addClassName(T_CONTEXTMENU);

                        // the zone inside the context menu
                        final String zoneDiv = resources.getElementName("div");
                        Element e = writer.element(zoneDiv, "id", zoneId);
                        resources.renderInformalParameters(writer);
                        e.addClassName("t-zone");
                    }
                });
            }
        };

    }

    /**
     * Ajax event that shows the context menu block, triggered on the chosen client event.
     * 
     * @param context
     *            the context
     * @return the context menu block;
     */
    Block onShowMenu(EventContext context)
    {
        triggerEvent(context);

        return getContextMenuBlock();
    }
}
