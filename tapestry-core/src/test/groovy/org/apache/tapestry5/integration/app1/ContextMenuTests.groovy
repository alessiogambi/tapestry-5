// Copyright 2010, 2011 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.integration.app1;

import org.apache.tapestry5.integration.TapestryCoreTestCase 

import org.openqa.selenium.internal.seleniumemulation.GetText;
import org.testng.annotations.Test 

class ContextMenuTests extends TapestryCoreTestCase
{
    @Test
    void basic_test_on_any() {
        
        openLinks "Context Menu Demo"
        
        contextMenuPresent "element1", "Context Menu Element", "element1_0", "Context Menu"
    }
    
    @Test
    void contextmenu_displays_loop_context() {
        
        openLinks "Context Menu Demo"
        
        contextMenuPresent "loop",   "Loop Element 0", "loop_0", "Loop Context Menu 1"
        contextMenuPresent "loop_1", "Loop Element 1", "loop_2", "Loop Context Menu 2"
        contextMenuPresent "loop_3", "Loop Element 2", "loop_4", "Loop Context Menu 3"
        contextMenuPresent "loop_5", "Loop Element 3", "loop_6", "Loop Context Menu 4"
        
    }

    private contextMenuPresent(menuElement, elementContent, contextMenu, contextMenuContent) {
        assertText menuElement, elementContent

        assertFalse isVisible(contextMenu)

        mouseDownRight menuElement

        // assertTrue isVisible("element1_0")
        System.out.println("text1_0:"+getText("element1_0")+" isVisible:"+isVisible("element1_0"));

        // assertText contextMenu, contextMenuContent
    }
}
