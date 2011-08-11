package com.dragansah.gsoc2011.demoapp.components;

import static com.dragansah.gsoc2011.demoapp.Constants.CONTEXTMENU_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_JAVA_PAGES;
import static com.dragansah.gsoc2011.demoapp.Constants.DEMOAPP_TML_PAGES;
import static com.dragansah.gsoc2011.demoapp.Constants.GRID_ENHANCEMENTS_BRANCH;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_ANNOTATIONS;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_BASE;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_BASE_PACKAGE;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_CONTEXTMENU;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_JAVA_COMPONENTS;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_MIXINS;
import static com.dragansah.gsoc2011.demoapp.Constants.TAP_CORE_SERVICES;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.EmbeddedMixin;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.KeepRequestParameters;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.contextmenu.ContextMenuClientEvent;
import org.apache.tapestry5.contextmenu.ContextMenuHideType;
import org.apache.tapestry5.corelib.components.DropdownMenu;
import org.apache.tapestry5.corelib.mixins.ContextMenu;
import org.apache.tapestry5.corelib.mixins.ContextMenuAjax;
import org.apache.tapestry5.corelib.mixins.ContextMenuBase;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell;
import org.apache.tapestry5.corelib.mixins.ContextMenuGridCell.GridOutputContext;
import org.apache.tapestry5.corelib.mixins.GridColumnSort;
import org.apache.tapestry5.corelib.mixins.GridCurrentPage;
import org.apache.tapestry5.grid.GridContextLevel;
import org.apache.tapestry5.internal.transform.EmbeddedMixinWorker;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

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
    private String page;

    @Inject
    private ComponentResources resources;

    private Map<String, String> pageToPageName;

    void setupRender()
    {
        pageToPageName = new LinkedHashMap<String, String>();
        pageToPageName.put("Index", "Home");
        pageToPageName.put("ContextMenuBasicExamples", "Basic examples");
        pageToPageName.put("GridExamples", "Grid examples");
        pageToPageName.put("ParametersExamples", "Parameters examples");
        pageToPageName.put("DropdownMenuExamples", "DropdownMenu examples");
        pageToPageName.put("GridEnhancements", "Grid Enhancements");
        pageToPageName.put("EmbeddedMixinExamples", "EmbeddedMixin examples");
    }

    public Collection<String> getPages()
    {
        return pageToPageName.keySet();
    }

    public String getPageName()
    {
        return pageToPageName.get(page);
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

    public String getEmbeddedMixinLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_ANNOTATIONS + EmbeddedMixin.class.getSimpleName() + ".java";
    }

    public String getEmbeddedMixinWorkerLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_BASE_PACKAGE + "internal/transform/"
                + EmbeddedMixinWorker.class.getSimpleName() + ".java";
    }

    public String getContextMenuLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenu.class.getSimpleName() + ".java";
    }

    public String getContextMenuBaseLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_BASE + ContextMenuBase.class.getSimpleName() + ".java";
    }

    public String getContextMenuAjaxLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenuAjax.class.getSimpleName() + ".java";
    }

    public String getContextMenuGridCellLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_MIXINS + ContextMenuGridCell.class.getSimpleName() + ".java";
    }

    public String getContextMenuJsLink()
    {
        return CONTEXTMENU_BRANCH + "tapestry-core/src/main/resources/org/apache/tapestry5/corelib/base/contextmenu.js";
    }

    public String getGridOutputContextLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_SERVICES + GridOutputContext.class.getSimpleName() + ".java";
    }

    public String getContextMenuClientEventLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_CONTEXTMENU + ContextMenuClientEvent.class.getSimpleName() + ".java";
    }

    public String getContextMenuHideTypeLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_CONTEXTMENU + ContextMenuHideType.class.getSimpleName() + ".java";
    }

    public String getGridContextLevelLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_BASE_PACKAGE + "grid/" + GridContextLevel.class.getSimpleName() + ".java";
    }

    public String getDropdownMenuLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_JAVA_COMPONENTS + DropdownMenu.class.getSimpleName() + ".java";
    }

    public String getMenuItemLink()
    {
        return CONTEXTMENU_BRANCH + TAP_CORE_JAVA_COMPONENTS + MenuItem.class.getSimpleName() + ".java";
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
