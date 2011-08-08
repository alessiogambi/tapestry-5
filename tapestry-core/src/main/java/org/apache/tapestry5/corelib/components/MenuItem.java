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

package org.apache.tapestry5.corelib.components;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.runtime.RenderQueue;

/**
 * A MenuItem rendered as part of a {@link DropdownMenu} component. The MenuItems should be provided in the body of the
 * {@link DropdownMenu} component.
 * 
 * @see DropdownMenu
 */
public class MenuItem
{
    /**
     * The label that will be shown in the menu item.
     */
    @Parameter(name = "label", required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private Block labelBlock;

    /**
     * The icon that will be shown before the label in this MenuItem.
     */
    @Parameter(defaultPrefix = BindingConstants.ASSET)
    private Asset icon;

    /**
     * Whether to include a separator after this MenuItem.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean separator;

    /**
     * Whether to include a separator before this MenuItem.
     */
    @Parameter(defaultPrefix = BindingConstants.LITERAL, value = "false")
    private boolean separatorTop;

    /**
     * If provided, this MenuItem will be wrapped in an anchor element with the supplied link.
     */
    @Parameter
    private Link link;

    @Inject
    private ComponentResources resources;

    RenderCommand beginRender(MarkupWriter writer)
    {
        return new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                if (resources.hasBody())
                {
                    queue.push(element("ul", queue));
                }

                if (link != null) queue.push(end(queue)); // </a>

                queue.push((RenderCommand) labelBlock);

                queue.push(new RenderCommand()
                {
                    public void render(MarkupWriter writer, RenderQueue queue)
                    {
                        Element li = writer.element("li");
                        if (resources.hasBody()) li.addClassName("t-folder");
                        if (separator) li.addClassName("t-separator");
                        if (separatorTop) li.addClassName("t-separator-top");
                        if (link != null) writer.element("a", "href", link);

                        if (icon != null)
                        {
                            writer.element("img", "src", icon);
                            writer.end();
                        }
                    }
                });
            }
        };
    }

    void afterRender(MarkupWriter writer)
    {
        if (resources.hasBody())
        {
            writer.end(); // </ul>
        }

        writer.end(); // </li>
    }

    private RenderCommand element(final String element, RenderQueue queue)
    {
        return new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                writer.element(element);
            }
        };
    }

    private RenderCommand end(RenderQueue queue)
    {
        return new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                writer.end();
            }
        };
    }
}
