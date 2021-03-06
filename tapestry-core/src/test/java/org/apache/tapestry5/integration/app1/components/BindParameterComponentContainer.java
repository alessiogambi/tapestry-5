// Copyright 2009 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.integration.app1.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.MixinAfter;

/**
 * Used to help prove that BindParameter on a component field throws an intelligible exception, even if the containing
 * resources actually do contain a declared parameter of the appropriate name.
 */
public class BindParameterComponentContainer
{

    @Parameter("literal:testvalue")
    private String value;


}
