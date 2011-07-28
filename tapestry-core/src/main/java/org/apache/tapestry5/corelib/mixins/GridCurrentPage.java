package org.apache.tapestry5.corelib.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

public class GridCurrentPage
{
    private static final String PAGE_ATTR = "page";

    @Parameter(value = "true", allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private boolean includeId;

    @InjectContainer
    private Grid grid;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;

    @Inject
    private PageRenderLinkSource linkSource;

    /**
     * This logic should be executed before setupDataSource called in the Grid setupRender phase, because it updates the
     * currentPage of the Grid.
     */
    void setupRender()
    {
        String pageParam = request.getParameter(getPageParameterName());
        if (pageParam == null) return;

        try
        {
            grid.setCurrentPage(Integer.valueOf(String.valueOf(pageParam)));
        }
        catch (NumberFormatException exception)
        {
        }
    }

    private String getPageParameterName()
    {
        String gridId = resources.getContainer().getComponentResources().getId();

        String paramName;
        if (includeId)
            paramName = String.format("%s.%s", gridId, PAGE_ATTR);
        else
            paramName = String.format("%s", PAGE_ATTR);

        return paramName;
    }

    /**
     * Non Ajax event handler for the {@link org.apache.tapestry5.corelib.components.GridPager}.
     * <p>
     * This event handler also adds all parameters from the request to the new link. By default the request won't
     * contain the request parameters contained in the current viewed URL, so one solution would be that the link that
     * triggers this event is decorated and the parameters are added to the link in the page by using
     * {@link org.apache.tapestry5.EventConstants#DECORATE_COMPONENT_EVENT_LINK}.
     */
    Object onActionFromPager(int newPage)
    {
        Link link = linkSource.createPageRenderLink(resources.getPageName());

        link.addParameter(getPageParameterName(), Integer.toString(newPage));

        return link;
    }
}
