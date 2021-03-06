// Copyright 2006, 2007, 2009 The Apache Software Foundation
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

package org.apache.tapestry5.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import static org.apache.tapestry5.ioc.annotations.AnnotationUseContext.*;
import org.apache.tapestry5.ioc.annotations.UseWith;

/**
 * Marker annotation for component methods associated with the terminal phase for the component rendering state machine.
 * Methods may optionally take a {@link org.apache.tapestry5.MarkupWriter} annotation. Generally, methods marked with
 * this annotation are used to perform post-render cleanup. In addition, a method may return false to return to the
 * {@link org.apache.tapestry5.annotations.SetupRender} phase. Returning void or true (the default), is the normal
 * course.
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
@Documented
@UseWith({COMPONENT,MIXIN,PAGE})
public @interface CleanupRender
{

}
