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

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.EmbeddedMixin;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.runtime.RenderCommand;
import org.apache.tapestry5.runtime.RenderQueue;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

/**
 * A non ajax version of a context menu component implemented as mixin. When it renders it fires a
 * {@link EventConstants#CONTEXTMENU} notification.
 * <p/>
 * This mixin has special behavior when used with a {@link Grid} component. It can be configured to be used in 3 levels
 * configured with {@link ContextMenuBase#menuLevel}.
 * <p/>
 * TODO: Document the event context
 * 
 * @see ContextMenuAjax
 * @since 5.3
 * @tapestrydoc
 */
public class ContextMenu extends ContextMenuBase
{    
    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Override
    protected RenderCommand renderMenu(JSONObject spec, final String contextMenuId, final Object[] context)
    {
        javaScriptSupport.addInitializerCall("contextMenu", spec);

        return new RenderCommand()
        {
            public void render(MarkupWriter writer, RenderQueue queue)
            {
                queue.push(new RenderCommand()
                {
                    public void render(MarkupWriter writer, RenderQueue queue)
                    {
                        writer.end();
                    }
                });

                queue.push((RenderCommand) getContextMenuBlock());

                queue.push(new RenderCommand()
                {
                    public void render(MarkupWriter writer, RenderQueue queue)
                    {
                        triggerEvent(context);

                        writer.element("div", "id", contextMenuId, "style", "display: none; position: absolute;")
                                .addClassName(T_CONTEXTMENU);
                    }
                });
            }
        };
    }
}
