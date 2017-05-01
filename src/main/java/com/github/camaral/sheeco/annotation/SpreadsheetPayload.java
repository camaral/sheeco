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

package com.github.camaral.sheeco.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A Java POJO to be serialized as a spreadsheet or deserialized from a
 * spreadsheet
 *
 * @author caio.amaral
 */
@Retention(RUNTIME)
@Target({ TYPE })
public @interface SpreadsheetPayload {
	/**
	 * The name of the entity represented by this payload. It will be used as
	 * the name of the spreadsheet sheet.
	 *
	 * @return The name of the entity
	 */
	String name();
	// TODO: make it optional; Use class name
}
