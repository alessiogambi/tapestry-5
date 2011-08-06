package com.dragansah.gsoc2011.demoapp.components;

import static com.dragansah.gsoc2011.demoapp.Constants.CONTEXTMENU_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_JAVA_PAGES;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_TML_PAGES;
import static com.dragansah.gsoc2011.demoapp.Constants.GRID_ENHANCEMENTS_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_JAVA_COMPONENTS;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_MIXINS;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_SERVICES;
import static com.dragansah.gsoc2011.demoapp.Constants.*;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.KeepRequestParameters;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.ContextMenuClientEvent;
import org.apache.tapestry5.corelib.ContextMenuGridLevel;
import org.apache.tapestry5.corelib.ContextMenuHideType;
import org.apache.tapestry5.corelib.mixins.ContextMenu;
import org.apache.tapestry5.corelib.mixins.ContextMenuAjax;
import org.apache.tapestry5.corelib.mixins.ContextMenuBase;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell.GridOutputContext;
import org.apache.tapestry5.corelib.mixins.GridColumnSort;
import org.apache.tapestry5.corelib.mixins.GridCurrentPage;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.awt.MenuItem;

/**
 * Layout component for pages of application contextmenu.
 */
@Import(stylesheet = "layout.css")
public class Layout
{
    /** The page title, for the <title> element and the <h1>element. */
    @SuppressWarnings("unused")
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Inject
    private ComponentResources resources;

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
    }

    public String[] getPageNames()
    {
        return new String[]
        { "Index", "BasicExamples", "AjaxExamples", "LoopExamples", "GridExamples", "ParametersExamples",
                "DropdownMenuExamples", "GridEnhancements" };
    }

    public String getJavaClassName()
    {
        return resources.getPageName() + ".java";
    }

    public String getTmlName()
    {
        return resources.getPageName() + ".tml";
    }

    public String getJavaClassLink()
    {
        return DEMOAPP_BRANCH + DEMOAPP_JAVA_PAGES + getJavaClassName();
    }

    public String getTmlLink()
    {
        return DEMOAPP_BRANCH + DEMOAPP_TML_PAGES + getTmlName();
    }

    public String getContextMenuLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenu.class.getSimpleName() + ".java";
    }

    public String getContextMenuBaseLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenuBase.class.getSimpleName() + ".java";
    }

    public String getContextMenuAjaxLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenuAjax.class.getSimpleName() + ".java";
    }

    public String getContextMenuJsLink()
    {
        return CONTEXTMENU_BRANCH
                + "tapestry-core/src/main/resources/org/apache/tapestry5/corelib/mixins/contextmenu.js";
    }

    public String getGridOutputContextLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_SERVICES + GridOutputContext.class.getSimpleName() + ".java";
    }

    public String getContextMenuClientEventLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_CORELIB + ContextMenuClientEvent.class.getSimpleName() + ".java";
    }

    public String getContextMenuHideTypeLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_CORELIB + ContextMenuHideType.class.getSimpleName() + ".java";
    }

    public String getContextMenuGridLevelLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_CORELIB + ContextMenuGridLevel.class.getSimpleName() + ".java";
    }

    public String getDropdownMenuLink()
    {
        return TAP_CORE_JAVA_COMPONENTS;// + DropdownMenu.class.getSimpleName() + ".java";
    }

    public String getMenuItemLink()
    {
        return TAP_CORE_JAVA_COMPONENTS + MenuItem.class.getSimpleName() + ".java";
    }

    public String getGridColumnSortLink()
    {
        return GRID_ENHANCEMENTS_BRANCH + TAP_CORE_MIXINS + GridColumnSort.class.getSimpleName() + ".java";
    }

    public String getGridCurrentPageLink()
    {
        return GRID_ENHANCEMENTS_BRANCH + TAP_CORE_MIXINS + GridCurrentPage.class.getSimpleName() + ".java";
    }

    public String getKeepRequestParametersLink()
    {
        return GRID_ENHANCEMENTS_BRANCH + TAP_CORE_ANNOTATIONS + KeepRequestParameters.class.getSimpleName() + ".java";
    }
}
