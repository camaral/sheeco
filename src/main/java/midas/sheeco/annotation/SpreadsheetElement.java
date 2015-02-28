/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package midas.sheeco.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * {@link SpreadsheetPayload} annotated Java Type attribute for
 * serialization/deserialization for spreadsheet
 * 
 * @author caio.amaral
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface SpreadsheetElement {

	/**
	 * The zero-based column number for the first attribute of the element
	 */
	int index();

}
