package org.apache.tapestry5.corelib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.ContextMenuClientEvent;
import org.apache.tapestry5.corelib.ContextMenuGridLevel;
import org.apache.tapestry5.corelib.ContextMenuHideType;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.internal.services.GridOutputContext;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.runtime.RenderQueue;
import org.apache.tapestry5.services.Environment;
import org.apache.tapestry5.services.PropertyOutputContext;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

/**
 * Base class for {@link ContextMenu} and {@link ContextMenuAjax}. Handles all the rendering scenarios given by
 * {@link ContextMenuBase#menuLevel}, {@link ContextMenuBase#clientEvent} and {@link ContextMenuBase#hideType}. The
 * actual rendering of the menu is left to the implementing classes using one abstract method
 * {@link ContextMenuBase#renderMenu}
 * 
 * @author Dragan Sahpaski
 * @see ContextMenu
 * @see ContextMenuAjax
 * @since 5.3
 * @tapestrydoc
 */
@Import(library = "contextmenu.js")
@Events(EventConstants.CONTEXTMENU)
public abstract class ContextMenuBase
{
    /**
     * A block providing the content for the context menu.
     */
    @Parameter(name = "contextmenu", defaultPrefix = BindingConstants.BLOCK)
    private Block contextMenuBlock;

    protected Block getContextMenuBlock()
    {
        return contextMenuBlock;
    }

    /**
     * If provided, this is the event context, which will be provided via the
     * {@link org.apache.tapestry5.contextmenu.EventConstants#CONTEXTMENU event}.
     */
    @Parameter
    private Object[] context;

    /**
     * The level at which the context menu is applied to a {@link Grid}. The default value is
     * {@link ContextMenuGridLevel#CELL}. This parameter is only used if the mixin is aplied by a {@link Grid}.
     */
    @Parameter(value = "CELL", defaultPrefix = BindingConstants.LITERAL)
    private ContextMenuGridLevel menuLevel;

    /**
     * The javascript client event that triggers the context menu
     */
    @Parameter(value = "CONTEXT", defaultPrefix = BindingConstants.LITERAL)
    private ContextMenuClientEvent clientEvent;

    /**
     * The hide type for the context menu
     */
    @Parameter(value = "MOUSEDOWN", defaultPrefix = BindingConstants.LITERAL)
    private ContextMenuHideType hideType;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Inject
    private Environment environment;

    @InjectContainer
    private Object container;

    @Inject
    private ComponentResources resources;

    Object afterRender(MarkupWriter writer)
    {
        return render();
    }

    private boolean isGrid;

    RenderCommand render()
    {
        final String containerId = containerElementId();

        isGrid = environment.peek(GridOutputContext.class) != null;

        /**
         * Render either a regular context menu or {@link GridContextMenuLevel.GRID} level context menu if the context
         * menu is applied to a {@link Grid}.
         */
        if (!isGrid || menuLevel == ContextMenuGridLevel.GRID)
        {
            final String contextMenuId = javaScriptSupport.allocateClientId(resources);
            if (environment.peek(GridOutputContext.class) != null) environment.pop(GridOutputContext.class);

            return renderMenu(containerId, contextMenuId, context);
        }

        /**
         * Render a {@link GridContextMenuLevel.CELL} level context menu on a {@link Grid}.
         */
        GridOutputContext gridOutputContext = environment.pop(GridOutputContext.class);
        if (menuLevel == ContextMenuGridLevel.CELL)
        {
            final List<RenderCommand> renderCommands = new ArrayList<RenderCommand>();
            int i = 0;
            for (Object row : gridOutputContext.rows())
            {
                for (PropertyOutputContext gridCellContext : gridOutputContext.properties(row))
                {
                    final String contextMenuId = javaScriptSupport.allocateClientId(resources);

                    renderCommands
                            .add(renderMenuForGridCell(containerId, contextMenuId, i++, context, gridCellContext));
                }
            }

            // render all
            return new RenderCommand()
            {
                public void render(MarkupWriter writer, RenderQueue queue)
                {
                    for (int i = renderCommands.size() - 1; i >= 0; i--)
                        queue.push(renderCommands.get(i));
                }
            };
        }

        /**
         * Render a {@link GridContextMenuLevel.ROW} level context menu on a {@link Grid}.
         */
        if (menuLevel == ContextMenuGridLevel.ROW)
        {
            int i = 0;
            final List<RenderCommand> renderCommands = new ArrayList<RenderCommand>();
            for (Object row : gridOutputContext.rows())
            {
                final String contextMenuId = javaScriptSupport.allocateClientId(resources);

                renderCommands.add(renderMenuForGridRow(containerId, contextMenuId, i++, context, row));
            }

            // render all
            return new RenderCommand()
            {
                public void render(MarkupWriter writer, RenderQueue queue)
                {
                    for (int i = renderCommands.size() - 1; i >= 0; i--)
                        queue.push(renderCommands.get(i));
                }
            };
        }

        throw new IllegalStateException("Should not be here");
    }

    /**
     * Returns an id of the context menu element.
     * <p/>
     * If the containing component is a {@link ClientElement} than return the clientId, thus forcing render of the
     * container's id. If, not than return null, which means that the menu element will be taken to be the previous
     * sibling of the contextMenu element (see contextmenu.js).
     * <p/>
     * This can result in undesirable effects if the containing component renders several elements with no root element
     * like the TextField for example (rendering a text field and a trailing icon), so in this case the context menu
     * would work on the icon and not on the text field.
     * 
     * @return the id of the menu element or null stating that the container is not an instance of {@link ClientElement}
     */
    private String containerElementId()
    {
        /**
         * So much we can do if don't now the id of the parent component Let's hope the component is just one HTML
         * element. Example for breaking this is {@link TextField} which renders a trailing icon, so the textField will
         * not get the contextMenu. But the {@link TextField} is a {@link ClientElement} so it is covered by the
         * previous case.
         */
        if (container instanceof ClientElement) return String.format("%s", ((ClientElement) container).getClientId());

        return null;
    }

    static class GridCellOutputContext implements PropertyOutputContext
    {
        private Object objectValue;

        private Object propertyValue;

        private String propertyId;

        private String propertyName;

        public GridCellOutputContext(Object objectValue, String propertyId, String propertyName, Object propertyValue)
        {
            this.objectValue = objectValue;
            this.propertyId = propertyId;
            this.propertyName = propertyName;
            this.propertyValue = propertyValue;
        }

        public Object getObjectValue()
        {
            return objectValue;
        }

        public Object getPropertyValue()
        {
            return propertyValue;
        }

        public Messages getMessages()
        {
            throw new NotImplementedException("This method is not implemented at this time");
        }

        public String getPropertyId()
        {
            return propertyId;
        }

        public String getPropertyName()
        {
            return propertyName;
        }
    }

    static class GridRowOutputContext extends GridCellOutputContext
    {
        public GridRowOutputContext(Object objectValue)
        {
            super(objectValue, null, null, null);
        }

        @Override
        public Object getPropertyValue()
        {
            throw new NotImplementedException("This method is not implemented at this time");
        }

        @Override
        public String getPropertyId()
        {
            throw new NotImplementedException("This method is not implemented at this time");
        }

        @Override
        public String getPropertyName()
        {
            throw new NotImplementedException("This method is not implemented at this time");
        }
    }

    /**
     * Triggers a {@link EventConstants.CONTEXTMENU} event
     * 
     * @param context
     */
    protected void triggerEvent(Object[] context)
    {
        resources.triggerEvent(EventConstants.CONTEXTMENU, context, null);
    }

    /**
     * Triggers a {@link EventConstants.CONTEXTMENU} event
     * 
     * @param context
     */
    @Log
    protected void triggerGridCellEvent(Object objectValue, String propertyId, String propertyName,
            Object propertyValue, Object[] context)
    {
        while (environment.peek(PropertyOutputContext.class) != null)
            environment.pop(PropertyOutputContext.class);
        environment.push(PropertyOutputContext.class, new GridCellOutputContext(objectValue, propertyId, propertyName,
                propertyValue));

        resources.triggerEvent(EventConstants.CONTEXTMENU, context, null);
    }

    /**
     * Triggers a {@link EventConstants.CONTEXTMENU} event
     * 
     * @param context
     */
    protected void triggerGridRowEvent(Object objectValue, Object[] context)
    {
        while (environment.peek(PropertyOutputContext.class) != null)
            environment.pop(PropertyOutputContext.class);
        environment.push(PropertyOutputContext.class, new GridRowOutputContext(objectValue));

        resources.triggerEvent(EventConstants.CONTEXTMENU, context, null);
    }

    /**
     * Should be used by inheriting classes
     */
    protected ContextMenuClientEvent getClientEvent()
    {
        return clientEvent;
    }

    /**
     * Should be used by inheriting classes
     */
    protected ContextMenuHideType getHideType()
    {
        return hideType;
    }

    /**
     * Returns true if the context menu is rendered using ajax and not right away
     */
    protected abstract boolean isAjax();

    /**
     * Renders the actual context menu. Should be overridden by inheriting classes.
     * 
     * @param elementId
     *            the id of the element triggering the context menu or null if the id is not known
     * @param contextMenuId
     *            id of the context menu element
     * @param gridCellIndex
     *            the index of the grid cell (td) that is the menu element
     * @param gridRowIndex
     *            the index of the grid row (tr) that is the menu element
     * @param context
     *            the menu context
     * @return a RenderCommand that renders the context menu
     */
    protected abstract RenderCommand renderMenu(JSONObject spec, String contextMenuId, Object[] context);

    private RenderCommand renderMenu(String elementId, String contextMenuId, Object[] context)
    {
        if (!isAjax()) triggerEvent(context);

        return renderMenu(getSpec(elementId, contextMenuId, null, null), contextMenuId, context);
    }

    private RenderCommand renderMenuForGridRow(String elementId, String contextMenuId, Integer gridRowIndex,
            Object[] context, Object objectValue)
    {
        if (!isAjax()) triggerGridRowEvent(objectValue, context);

        return renderMenu(getSpec(elementId, contextMenuId, null, gridRowIndex), contextMenuId, context);
    }

    private RenderCommand renderMenuForGridCell(final String elementId, final String contextMenuId,
            final Integer gridCellIndex, final Object[] context, final PropertyOutputContext cellContext)
    {
        if (isAjax())
            return renderMenu(getSpec(elementId, contextMenuId, gridCellIndex, null), contextMenuId, context);

        return new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                triggerGridCellEvent(
                        cellContext.getObjectValue(), cellContext.getPropertyId(), cellContext.getPropertyName(),
                        cellContext.getPropertyValue(), context);

                queue.push(renderMenu(getSpec(elementId, contextMenuId, gridCellIndex, null), contextMenuId, context));
            }
        };
    }

    private JSONObject getSpec(String elementId, String contextMenuId, Integer gridCellIndex, Integer gridRowIndex)
    {
        return new JSONObject("elementId", elementId,

        "contextMenuId", contextMenuId,

        "gridCellIndex", gridCellIndex == null ? null : String.valueOf(gridCellIndex),

        "gridRowIndex", gridRowIndex == null ? null : String.valueOf(gridRowIndex),

        "clientEvent", String.valueOf(getClientEvent().ordinal()),

        "hideType", String.valueOf(getHideType().ordinal()));
    }
}
