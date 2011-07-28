// Copyright 2010 The Apache Software Foundation
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

package org.apache.tapestry5.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.PAGE;

import org.apache.tapestry5.ioc.annotations.UseWith;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * A marker annotation that indicates that all the request parameters will be added to the component event links, using
 * a {@link org.apache.tapestry5.EventConstants#DECORATE_COMPONENT_EVENT_LINK} event handler.
 * <p>
 * 
 * @see org.apache.tapestry5.EventConstants#DECORATE_COMPONENT_EVENT_LINK
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@UseWith(PAGE)
public @interface KeepRequestParameters
{
    boolean pageRenderLinks() default true;

    boolean componentEventLinks() default true;

    String[] components() default {};
}
