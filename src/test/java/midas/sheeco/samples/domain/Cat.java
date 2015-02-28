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
package midas.sheeco.samples.domain;

import java.util.Date;

import midas.sheeco.annotation.SpreadsheetAttribute;
import midas.sheeco.annotation.SpreadsheetElement;
import midas.sheeco.annotation.SpreadsheetPayload;

/**
 * @author caio.amaral
 *
 */
@SpreadsheetPayload(name = "Cat")
public class Cat {
	@SpreadsheetAttribute(index = 0)
	String name;
	@SpreadsheetAttribute(index = 2, name="Birth date")
	Date birthDate;
	@SpreadsheetAttribute(index = 1, name="Male?")
	Boolean male;

	@SpreadsheetElement(index = 3)
	Fur body;
	@SpreadsheetElement(index = 5)
	Fur tail;

}
