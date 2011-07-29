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

package org.apache.tapestry5.integration.app1

import org.apache.tapestry5.test.SeleniumTestCase
import org.testng.annotations.Test

class GridColumnSortMixinTest extends SeleniumTestCase
{
    @Test
    void sort_parameters_are_added_to_url() {
        
        openLinks "GridCurrentPage and GridColumnSort Mixin Demo"
        
        // in the beginning we are at page 1
        
        // click the Title header
         clickAndWait "//div[@class='t-data-grid'][1]//a[.='Title']"
         
         // check parameter in URL
         String urlParameters = getLocation().split("\\?")[1]
         
         assertTrue urlParameters.contains("sort=title")
         assertTrue getLocation().contains("sortorder=asc")
        
        // click the Title header again
         clickAndWait "//div[@class='t-data-grid'][1]//a[.='Title']"
        
         assertTrue urlParameters.contains("sort=title")
         assertTrue getLocation().contains("sortorder=desc")
         
         // reset grid1
         clickAndWait "link=reset Grid1"
         
         assertTrue getLocation().endsWith("gridcurrentpageandcolumnsortmixindemo")
    }
    
    @Test
    void provide_page_number_in_url_first_grid() {
        
        openLinks "GridCurrentPage and GridColumnSort Mixin Demo"
        
        // goto page 2 using URL
        clickAndWait "//a[@id='gotoPage2']"
        
        // grid1 should be on page 2
        assertText "//div[@class='t-data-grid'][1]/div[@class='t-data-grid-pager']/span[@class=\"current\"]", "2"
    }
    
    @Test
    void provide_page_number_in_url_both_grids() {
        
        openLinks "GridCurrentPage and GridColumnSort Mixin Demo"
        
        // goto page 2 using URL
        clickAndWait "//a[@id='gotoGrid1Page2Grid2Page3']"
        
        // grid1 should be on page 2
        assertText "//div[@class='t-data-grid'][1]/div[@class='t-data-grid-pager']/span[@class=\"current\"]", "2"
        
        // grid2 should be on page 3
        assertText "//div[@class='t-data-grid'][2]/div[@class='t-data-grid-pager']/span[@class=\"current\"]", "3"
        
        // reset grid1
        clickAndWait "link=reset Grid1"
        
        assertTrue getLocation().endsWith("gridcurrentpageandcolumnsortmixindemo")
    }
}
