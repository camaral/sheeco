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

import midas.sheeco.annotation.SpreadsheetAttribute;
import midas.sheeco.annotation.SpreadsheetPayload;

/**
 * @author caio.amaral
 *
 */
@SpreadsheetPayload(name = "Fur")
public class Fur {
	@SpreadsheetAttribute(index = 0)
	private Integer hairLength;
	@SpreadsheetAttribute(index = 1)
	private String hairColor;

	public String getHairColor() {
		return hairColor;
	}

	public Integer getHairLength() {
		return hairLength;
	}
}
